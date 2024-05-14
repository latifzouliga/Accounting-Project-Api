package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.exception.ServiceException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
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
    private final MapperUtil mapperUtil;

    @Override
    public List<ClientVendorDto> listAllClientVendors(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize)
                .withSort(Sort.Direction.ASC, "clientVendorName");
        return clientVendorRepository.findAll(pageRequest).stream()
                .map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .collect(Collectors.toList());
    }


    @Override
    public List<ClientVendorDto> listAllClientsClientVendorType(String clientVendorType, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("clientVendorName"));

        ClientVendorType clientVendor = switch (clientVendorType.toLowerCase()) {
            case "client" -> ClientVendorType.CLIENT;
            case "vendor" -> ClientVendorType.VENDOR;
            default -> throw new ServiceException("Invalid clientVendorType: " + clientVendorType);
        };
        return clientVendorRepository.findAllByClientVendorType(clientVendor, pageRequest)
                .stream()
                .map(cv -> mapperUtil.convert(cv, new ClientVendorDto()))
                .toList();
    }
}
