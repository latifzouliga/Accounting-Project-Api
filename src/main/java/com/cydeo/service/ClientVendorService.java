package com.cydeo.service;


import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;

import java.util.List;

public interface ClientVendorService {

    List<ClientVendorDto> listAllClientVendors(int pageNo, int pageSize);

    List<ClientVendorDto> listAllClientsClientVendorType(String clientVendorType, int pageNo, int pageSize);
}
