package com.cydeo.service.impl;

import com.cydeo.dto.AddressDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Address;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import com.cydeo.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;
    @Override
    public AddressDto save(AddressDto addressDto) {
        Address address = mapperUtil.convert(addressDto, new Address());
        address.setInsertUserId(getLoggedInUser().getId());
        address.setLastUpdateUserId(getLoggedInUser().getId());

        addressRepository.save(address);
        return addressDto;
    }

    private User getLoggedInUser(){
        return securityService.getLoggedInUser();
    }
}
