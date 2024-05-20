package com.zouliga.service.impl;

import com.zouliga.dto.RoleDto;
import com.zouliga.mapper.MapperUtil;
import com.zouliga.repository.RoleRepository;
import com.zouliga.service.RoleService;
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
