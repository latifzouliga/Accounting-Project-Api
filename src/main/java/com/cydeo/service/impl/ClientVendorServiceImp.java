package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
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
        return clientVendorRepository.findAllByCompany_Title(companyTitle, pageRequest).stream()
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
        return clientVendorRepository.findAllByCompany_TitleAndClientVendorType(companyTitle, clientVendor, pageRequest)
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
        return null;
    }

    private User getLoggedInUser() {
        return securityService.getLoggedInUser();
    }
}
