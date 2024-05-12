package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Company;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private SecurityService securityService;


    @InjectMocks
    private UserServiceImpl userService;
    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());

    /**
     * Test Find By Username: {@link UserServiceImpl#findByUsername(String)}
     */
    @DisplayName("Find user by username")
    @Test
    void findByUsername() {

        Company company = new Company();
        company.setId(1L);
        company.setTitle("any company");

        Role role1 = new Role();
        role1.setId(2L);
        role1.setDescription("Admin");

        User user = new User();
        user.setId(1L);
        user.setFirstname("mike");
        user.setLastname("can");
        user.setUsername("user1@email.com");
        user.setRole(role1);
        user.setCompany(company);
        user.setPassword("Abc1");

        UserDto expectedDto = UserDto.builder()
                .id(1L)
                .firstname("mike")
                .lastname("can")
                .username("user1@email.com")
                .password("Abc1")
                .role(RoleDto.builder().id(2L).description("Admin").build())
                .company(CompanyDto.builder().id(1L).title("any company").build())
                .build();

        when(securityService.getLoggedInUser()).thenReturn(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        UserDto actualDto = userService.findByUsername(user.getUsername());
        verify(userRepository).findByUsername(user.getUsername());
        assertEquals(expectedDto, actualDto);


    }

    // TODO:

    /**
     * Test List All Users By Company: {@link UserServiceImpl#listAllUsersByCompany(String, int, int)}
     */
    @DisplayName("List all Users By Company")
    @Test
    void listAllUsersByCompany() {
        String companyTitle = "any company";
        Pageable pageable = PageRequest.of(0, 3,
                Sort.by("firstname").and(Sort.by("lastname")));
        when(userRepository.findAllByCompany_Title(companyTitle, pageable)).thenReturn(userList());

        List<UserDto> returnedList = userService.listAllUsersByCompany(companyTitle, 0, 3);

        verify(userRepository).findAllByCompany_Title(companyTitle, pageable);
        assertEquals(userDtoList(), returnedList);
    }

    /**
     * Test List All users By Company: {@link UserServiceImpl#listAllUsersByCompany(String)}
     */
    @DisplayName("List all Users By Company")
    @Test
    void testListAllUsersByCompany() {
        String companyTitle = "any company";
        List<UserDto> expectedList = userDtoList();
        when(userRepository.findAllByCompany_Title(companyTitle)).thenReturn(userList());

        List<UserDto> returnedList = userService.listAllUsersByCompany(companyTitle);

        verify(userRepository).findAllByCompany_Title(companyTitle);
        assertEquals(expectedList, returnedList);
    }

    @DisplayName("List All Admins")
    @Test
    void listAllAdmins() {
        List<UserDto> expectedList = userDtoList();

        Pageable pageable = PageRequest.of(0, 10,
                Sort.by("firstname").and(Sort.by("lastname")));

        userList().get(0).getRole().setDescription("Admin");
        userList().get(1).getRole().setDescription("Admin");
        userList().get(2).getRole().setDescription("Admin");

        when(userRepository.findAllAdmins(pageable)).thenReturn(userList());

        List<UserDto> returnedUserList = userService.listAllAdmins(0, 10);

        verify(userRepository).findAllAdmins(pageable);

        assertTrue(returnedUserList.size() > 1);
        assertEquals(expectedList, returnedUserList);
    }

    @DisplayName("List All filtered Users: List All Admins by Root User")
    @Test
    void listAllFilteredUsers_by_Root() {

        Pageable pageable = PageRequest.of(0, 2,
                Sort.by("firstname").and(Sort.by("lastname")));

        UserDto userDto = UserDto.builder().build();
        Company company = new Company();
        company.setTitle("any company");

        User loggedInUser = new User();
        Role rootRole = new Role();
        rootRole.setDescription("Root User");
        loggedInUser.setRole(rootRole);
        loggedInUser.setCompany(company);

        when(securityService.getLoggedInUser()).thenReturn(loggedInUser);
        lenient().when(mapperUtil.convert(userDto, new User())).thenReturn(any(User.class));
        lenient().when(userRepository.findAllAdmins(pageable)).thenReturn(userList());

        userService.listAllFilteredUsers(0, 2);

        verify(userRepository, times(1)).findAllAdmins(pageable);
        //  Verify that listAllUsersByCompany was not called
        verify(userRepository, never()).findAllByCompany_Title(anyString());


    }

    @DisplayName("List All filtered Users: List All Users By Admin")
    @Test
    void listAllFilteredUsers_By_Admin() {

        Pageable pageable = PageRequest.of(0, 2,
                Sort.by("firstname").and(Sort.by("lastname")));

        UserDto userDto = UserDto.builder().build();

        Company company = new Company();
        company.setTitle("any company");

        User loggedInUser = new User();
        Role rootRole = new Role();
        rootRole.setDescription("Admin");
        loggedInUser.setRole(rootRole);
        loggedInUser.setCompany(company);

        when(securityService.getLoggedInUser()).thenReturn(loggedInUser);
        lenient().when(mapperUtil.convert(userDto, new User())).thenReturn(any(User.class));
        lenient().when(userRepository.findAllAdmins(pageable)).thenReturn(userList());

        List<UserDto> actualResult = userService.listAllFilteredUsers(0, 2);

        verify(userRepository, times(1)).findAllByCompany_Title(anyString());
        //  Verify that listAllUsersByCompany was not called
        verify(userRepository, never()).findAllAdmins(pageable);

    }

    @DisplayName("Update User: Accepts User Object As Parameter")
    @Test
    void update() {

        User user = new User();
        UserDto userDto = UserDto.builder().build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(mapperUtil.convert(userDto, new User())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.update(userDto);

        verify(userRepository).findByUsername(user.getUsername());
        verify(userRepository).save(user);

    }

    @DisplayName("Update User: Accepts Map<String, Object> As Parameter")
    @Test
    void testUpdate() {
        User user = new User();
        UserDto userDto = UserDto.builder().build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(mapperUtil.convert(userDto, new User())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.update(userDto);

        verify(userRepository).findByUsername(user.getUsername());
        verify(userRepository).save(user);
    }

    @DisplayName("Delete User")
    @Test
    void delete() {
        UserDto userDto = UserDto.builder().build();
        Company company = new Company();
        company.setTitle("any company");

        Role role1 = new Role();
        role1.setId(2L);
        role1.setDescription("Admin");

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstname("mike");
        user1.setLastname("can");
        user1.setUsername("user1@email.com");
        user1.setRole(role1);
        user1.setCompany(company);
        user1.setPassword("Abc1");

        User loggedInUser = new User();
        Role rootRole = new Role();
        rootRole.setDescription("Root User");
        loggedInUser.setRole(rootRole);
        loggedInUser.setCompany(company);

        when(securityService.getLoggedInUser()).thenReturn(loggedInUser);
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));
        when(userRepository.findAllByCompany_Title(company.getTitle())).thenReturn(userList());
        user1.setDeleted(true);

        userService.delete(user1.getUsername());

        verify(userRepository).findByUsername(user1.getUsername());
        verify(userRepository).findAllByCompany_Title(company.getTitle());

    }

    List<UserDto> userDtoList() {
        CompanyDto companyDto = CompanyDto.builder().id(1L).title("any company").build();
        UserDto user1 = UserDto.builder()
                .id(1L)
                .firstname("mike")
                .lastname("can")
                .username("user1@email.com")
                .password("Abc1")
                .role(RoleDto.builder().id(2L).description("Admin").build())
                .company(companyDto)
                .build();
        UserDto user2 = UserDto.builder()
                .id(2L)
                .firstname("john")
                .lastname("can")
                .username("user2@email.com")
                .password("Abc1")
                .role(RoleDto.builder().id(3L).description("Manager").build())
                .company(companyDto)
                .build();
        UserDto user3 = UserDto.builder()
                .id(3L)
                .firstname("james")
                .lastname("can")
                .username("user3@email.com")
                .password("Abc1")
                .role(RoleDto.builder().id(4L).description("Employee").build())
                .company(companyDto)
                .build();
        return List.of(user1, user2, user3);
    }

    List<User> userList() {

        Company company = new Company();
        company.setId(1L);
        company.setTitle("any company");

        Role role1 = new Role();
        role1.setId(2L);
        role1.setDescription("Admin");

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstname("mike");
        user1.setLastname("can");
        user1.setUsername("user1@email.com");
        user1.setRole(role1);
        user1.setCompany(company);
        user1.setPassword("Abc1");

        Role role2 = new Role();
        role2.setId(3L);
        role2.setDescription("Manager");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstname("john");
        user2.setLastname("can");
        user2.setUsername("user2@email.com");
        user2.setPassword("Abc1");
        user2.setRole(role2);
        user2.setCompany(company);

        Role role3 = new Role();
        role3.setId(4L);
        role3.setDescription("Employee");

        User user3 = new User();
        user3.setId(3L);
        user3.setFirstname("james");
        user3.setLastname("can");
        user3.setUsername("user3@email.com");
        user3.setPassword("Abc1");
        user3.setRole(role3);
        user3.setCompany(company);

        return List.of(user1, user2, user3);
    }

}