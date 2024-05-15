package com.cydeo.service.impl;

import com.cydeo.dto.RoleDto;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    @Override
    public RoleDto findById(Long id) {
        return mapperUtil.convert(roleRepository.findById(id).orElseThrow(),new RoleDto());
    }
}
