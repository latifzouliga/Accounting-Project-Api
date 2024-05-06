package com.cydeo.controller;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

	private final UserService userService;


	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	@ResponseBody
	public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(userService.getLoggedInUser());
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