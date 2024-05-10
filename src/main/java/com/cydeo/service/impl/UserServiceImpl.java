package com.cydeo.service.impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.UserService;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapper;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakService keycloakService;


    public UserServiceImpl(UserRepository userRepository,
                           MapperUtil mapper,
                           PasswordEncoder passwordEncoder,
                           KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.keycloakService = keycloakService;
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return mapper.convert(user, new UserDto());
    }

    @Override
    public User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public UserDto save(UserDto userDto) {
        String password = userDto.getPassword();
        User user = mapper.convert(userDto, new User());
        if (password != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(password));
            user.setInsertDateTime(LocalDateTime.now());
            user.setLastUpdateDateTime(LocalDateTime.now());
            user.setLastUpdateUserId(1L);
            user.setInsertUserId(1L);
            userRepository.save(user);
            keycloakService.userCreate(userDto);
            return userDto;
        } else {
            throw new RuntimeException("Password can not be null");
        }

    }

    @Override
    public List<UserDto> listAllUsersByCompany(String companyTitle, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by("firstname").and(Sort.by("lastname"))
        );
        return userRepository.findAllByCompany_Title(companyTitle, pageable)
                .stream()
                .map(user -> mapper.convert(user, new UserDto()))
                .toList();
    }

    @Override
    public List<UserDto> listAllUsersByCompany(String companyTitle) {
        return userRepository.findAllByCompany_Title(companyTitle)
                .stream()
                .map(user -> {
                    UserDto userDto = mapper.convert(user, new UserDto());
                    userDto.setOnlyAdmin(isOnlyAdmin(userDto));
                    return userDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> listAllAdmins(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by("firstname").and(Sort.by("lastname"))
        );
        return userRepository.findAllAdmins(pageable)
                .stream()
                .map(user -> {
                    UserDto userDto = mapper.convert(user, new UserDto());
                    userDto.setOnlyAdmin(isOnlyAdmin(userDto));
                    return userDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> listAllFilteredUsers(int pageNo, int pageSize) {
        String loggedInUserRole = getLoggedInUser().getRole().getDescription();
        if (loggedInUserRole.equalsIgnoreCase("Root User")) {
            return listAllAdmins(pageNo, pageSize);
        }
        return listAllUsersByCompany(getCompanyTitle());
    }

    private boolean isOnlyAdmin(UserDto userDto) {
        String role = userDto.getRole().getDescription();
        String admin = "Admin";
        if (role.equalsIgnoreCase(admin)) {
            List<User> userList = userRepository.findAllByCompany_TitleAndRole_Description(userDto.getCompany().getTitle(), admin);
            return userList.size() == 1;
        }
        return false;
    }

    private String getCompanyTitle() {
        return getLoggedInUser().getCompany().getTitle();
    }

}











