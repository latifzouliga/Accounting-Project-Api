package com.zouliga.service;


import com.zouliga.dto.ClientVendorDto;

import java.util.List;
import java.util.Map;

public interface ClientVendorService {

    List<ClientVendorDto> listAllClientVendors(int pageNo, int pageSize);

    List<ClientVendorDto> listAllByClientVendorType(String clientVendorType, int pageNo, int pageSize);

    ClientVendorDto create(ClientVendorDto clientVendorDto);

    ClientVendorDto update(Long clientVendorId, ClientVendorDto clientVendorDto);

    ClientVendorDto patch(Long id, Map<String, Object> fields);

    void delete(Long id);
    ClientVendorDto findById(Long id);
}
