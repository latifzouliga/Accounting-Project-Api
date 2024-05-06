package com.cydeo.controller;

import com.cydeo.dto.UserDto;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class KeycloakController {

	private final UserService userService;
	private final UserRepository userRepository;
	private final KeycloakService keycloakService;

	public KeycloakController(UserService userService, UserRepository userRepository, KeycloakService keycloakService) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.keycloakService = keycloakService;
	}

	@GetMapping("/userinfo")
	@ResponseBody
	public String getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		System.out.println(userService.getLoggedInUser());
		return "Logged-in User: " + username;
	}

	@GetMapping("/admin")
	public ResponseEntity<String> getAdminDetails() {
		return ResponseEntity.ok("Hello admin");
	}

	@GetMapping("/user")
	public ResponseEntity<String> getUserDetails() {
		return ResponseEntity.ok("Hello User");
	}

	@PostMapping("/admin")
	public ResponseEntity<String> create(@RequestBody UserDto userDto) {
		userService.save(userDto);
		return ResponseEntity.ok("Hello admin");
	}

}