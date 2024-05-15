package com.cydeo.service;


import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;

import java.util.List;
import java.util.Map;

public interface ClientVendorService {

    List<ClientVendorDto> listAllClientVendors(int pageNo, int pageSize);

    List<ClientVendorDto> listAllByClientVendorType(String clientVendorType, int pageNo, int pageSize);

    ClientVendorDto create(ClientVendorDto clientVendorDto);

    ClientVendorDto update(Long clientVendorId, ClientVendorDto clientVendorDto);

    ClientVendorDto patch(Long id, Map<String, Object> fields);
}
