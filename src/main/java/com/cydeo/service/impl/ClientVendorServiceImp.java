package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Address;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.User;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.exception.ServiceException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientVendorServiceImp implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    @Override
    public List<ClientVendorDto> listAllClientVendors(int pageNo, int pageSize) {

        Sort sort = Sort.by("clientVendorType").and(Sort.by("clientVendorName"));
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        String companyTitle = securityService.getLoggedInUser().getCompany().getTitle();
        return clientVendorRepository.findAllByCompany_Title(companyTitle, pageRequest)
                .stream()
                .map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .collect(Collectors.toList());
    }


    @Override
    public List<ClientVendorDto> listAllByClientVendorType(String clientVendorType, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize)
                .withSort(Sort.Direction.ASC, "clientVendorName");

        ClientVendorType clientVendor = switch (clientVendorType.toLowerCase()) {
            case "client" -> ClientVendorType.CLIENT;
            case "vendor" -> ClientVendorType.VENDOR;
            default -> throw new ServiceException("Invalid clientVendorType: " + clientVendorType);
        };

        String companyTitle = securityService.getLoggedInUser().getCompany().getTitle();
        return clientVendorRepository.findAllByCompany_TitleAndClientVendorType(
                        companyTitle,
                        clientVendor,
                        pageRequest
                )
                .stream()
                .map(cv -> mapperUtil.convert(cv, new ClientVendorDto()))
                .toList();
    }

    @Override
    public ClientVendorDto create(ClientVendorDto clientVendorDto) {
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
        clientVendor.setCompany(getLoggedInUser().getCompany());
        clientVendorRepository.save(clientVendor);
        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }

    // TODO: implement update client/vendor method
    @Override
    public ClientVendorDto update(Long clientVendorId, ClientVendorDto clientVendorDto) {

        ClientVendor clientVendor = clientVendorRepository.findById(clientVendorId)
                .orElseThrow(() -> new ServiceException(String.valueOf(clientVendorId)));

        clientVendorDto.setId(clientVendor.getId());
        clientVendorDto.getAddress().setId(clientVendor.getAddress().getId());
        clientVendorDto.setCompany(mapperUtil.convert(clientVendor.getCompany(), new CompanyDto()));

        ClientVendor updatedClientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
        clientVendorRepository.save(updatedClientVendor);
        return clientVendorDto;
    }

    @Override
    public ClientVendorDto patch(Long id, Map<String, Object> fields) {

        ClientVendor clientVendor = clientVendorRepository
                .findById(id).orElseThrow(() -> new ServiceException(String.valueOf(id)));
        Address address = clientVendor.getAddress();

        fields.forEach((key, value) -> {
            switch (key) {
                case "clientVendorName" -> clientVendor.setClientVendorName((String) value);
                case "phone" -> clientVendor.setPhone((String) value);
                case "website" -> clientVendor.setWebsite((String) value);
                case "clientVendorType" -> clientVendor.setClientVendorType((ClientVendorType) value);
                case "addressLine1" -> address.setAddressLine1((String) value);
                case "addressLine2" -> address.setAddressLine2((String) value);
                case "city" -> address.setCity((String) value);
                case "state" -> address.setState((String) value);
                case "country" -> address.setCountry((String) value);
                case "zipCode" -> address.setZipCode((String) value);
                default -> throw new ServiceException("Invalid input " + value);
            }
        });

        return mapperUtil.convert(clientVendorRepository.save(clientVendor), new ClientVendorDto());
    }

    private User getLoggedInUser() {
        return securityService.getLoggedInUser();
    }
}
