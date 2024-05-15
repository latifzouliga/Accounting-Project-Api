package com.cydeo.service;

import com.cydeo.dto.UserDto;

import java.util.List;
import java.util.Map;

public interface UserService {


    UserDto findByUsername(String username);
    UserDto create(UserDto userDto);
    List<UserDto> listAllUsersByCompany(String companyTitle, int pageNo, int size);
    List<UserDto> listAllUsersByCompany(String companyTitle);
    List<UserDto> listAllAdmins(int pageNo, int size);
    List<UserDto> listAllFilteredUsers(int pageNo, int size);
    UserDto update(UserDto userDto);
    UserDto update(String username, Map<String, Object> field);
    void delete(String username);
}
