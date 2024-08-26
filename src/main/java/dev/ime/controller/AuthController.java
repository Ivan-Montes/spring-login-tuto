package dev.ime.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ime.dto.AuthResponse;
import dev.ime.dto.LoginRequest;
import dev.ime.dto.RegisterRequest;
import dev.ime.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
		
		Optional<AuthResponse> optAuth = authService.login(loginRequest);
		
		return createResponse(optAuth);
		
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
		
		Optional<AuthResponse> optAuth = authService.register(registerRequest);		
	
		return createResponse(optAuth);
		
	}

	private ResponseEntity<AuthResponse> createResponse(Optional<AuthResponse> optAuth) {
		
		return ResponseEntity.ok(optAuth
				.filter(auth -> auth != null && !auth.jwtToken().isBlank())
				.orElse(new AuthResponse("WTF Empty Token")));
		
	}	
	
}
