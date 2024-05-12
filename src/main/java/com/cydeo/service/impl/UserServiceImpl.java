package com.cydeo.service.impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapper;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakService keycloakService;
    private final SecurityService securityService;


    public UserServiceImpl(UserRepository userRepository,
                           MapperUtil mapper,
                           PasswordEncoder passwordEncoder,
                           KeycloakService keycloakService, SecurityService securityService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.keycloakService = keycloakService;
        this.securityService = securityService;
    }

    private User getLoggedInUser() {
        return securityService.getLoggedInUser();
    }

    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        if (getLoggedInUser().getRole().getDescription().equalsIgnoreCase("Root User")) {
            if (user.getRole().getDescription().equalsIgnoreCase("Admin")) {
                return mapper.convert(user, new UserDto());
            }
            throw new UserNotFoundException("No admin found with username: " + username);
        }
        return mapper.convert(user, new UserDto());
    }


    @Transactional
    @Override
    public UserDto save(UserDto userDto) {
        String password = userDto.getPassword();
        User user = mapper.convert(userDto, new User());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(password));
            user.setInsertDateTime(LocalDateTime.now());
            user.setLastUpdateDateTime(LocalDateTime.now());
            user.setLastUpdateUserId(getLoggedInUser().getId());
            user.setInsertUserId(getLoggedInUser().getId());
        userRepository.save(user);
        keycloakService.userCreate(userDto);
        return userDto;


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

    @Transactional
    @Override
    public UserDto update(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userDto.setId(user.getId());
        User updatedUser = mapper.convert(userDto, new User());
        updatedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        updatedUser.setInsertDateTime(LocalDateTime.now());
        updatedUser.setLastUpdateDateTime(LocalDateTime.now());
        updatedUser.setLastUpdateUserId(getLoggedInUser().getId());
        updatedUser.setInsertUserId(getLoggedInUser().getId());
        userRepository.save(updatedUser);
        return userDto;
    }

    @Override
    public UserDto update(String username, Map<String, Object> fieldToBeUpdated) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        for (Map.Entry<String, Object> entry : fieldToBeUpdated.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();

            switch (field) {
                case "firstname" -> user.setFirstname((String) value);
                case "lastname" -> user.setLastname((String) value);
                case "password" -> user.setPassword((String) value);
                case "phone" -> user.setPhone((String) value);
                case "enabled" -> user.setEnabled((boolean) value);
            }
        }
        userRepository.save(user);
        return mapper.convert(user, new UserDto());
    }

    @Override
    public void delete(String username) {

        User loggedInUser = getLoggedInUser();
        List<UserDto> userList = listAllUsersByCompany(getCompanyTitle());
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean isAdmin = user.getRole().getDescription().equalsIgnoreCase("Admin");
        boolean isRootUser = user.getRole().getDescription().equalsIgnoreCase("Root User");

        if (username.equalsIgnoreCase(loggedInUser.getUsername())) {
            throw new RuntimeException("You are the Admin. You can not delete yourself");
        }

        if (!isAdmin) {
            user.setIsDeleted(true);
            user.setEnabled(false);
        }
        if (!isRootUser) {
            user.setIsDeleted(true);
            user.setEnabled(false);
        } else {
            userList.forEach(each -> {
                if (each.isOnlyAdmin()) {
                    throw new RuntimeException(username + " is only admin");
                }
                user.setIsDeleted(true);
                user.setEnabled(false);
            });
        }
        userRepository.save(user);

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











