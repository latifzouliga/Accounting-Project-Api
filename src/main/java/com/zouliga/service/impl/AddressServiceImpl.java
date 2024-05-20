package com.zouliga.service.impl;

import com.zouliga.dto.AddressDto;
import com.zouliga.entity.Address;
import com.zouliga.entity.User;
import com.zouliga.mapper.MapperUtil;
import com.zouliga.repository.AddressRepository;
import com.zouliga.service.AddressService;
import com.zouliga.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;
    @Override
    public AddressDto save(AddressDto addressDto) {
        Address address = mapperUtil.convert(addressDto, new Address());

        addressRepository.save(address);
        return addressDto;
    }

    private User getLoggedInUser(){
        return securityService.getLoggedInUser();
    }
}
