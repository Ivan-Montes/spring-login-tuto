package dev.ime.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.ime.dto.AuthResponse;
import dev.ime.dto.LoginRequest;
import dev.ime.dto.RegisterRequest;
import dev.ime.jwt.JwtUtils;
import dev.ime.model.Role;
import dev.ime.model.User;
import dev.ime.repository.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final JwtUtils jwtService;
    private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public AuthService(UserRepository userRepository, JwtUtils jwtService, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public Optional<AuthResponse> login(LoginRequest loginRequest) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.email(),
						loginRequest.password()));
		
		User userFound = userRepository.findByEmail(loginRequest.email())
				.orElseThrow(() -> new UsernameNotFoundException("Error"));
		
		return returnOptAuthResponse(userFound);
	}
	
	public Optional<AuthResponse> register(RegisterRequest registerRequest) {
		
		User user = User.builder()
				.name(registerRequest.name())
				.lastname(registerRequest.lastname())
				.email(registerRequest.email())
				.password(passwordEncoder.encode( registerRequest.password() ))
				.role(Role.USER)
				.build();
		
		User userSaved = userRepository.save(user);
		
		return returnOptAuthResponse(userSaved);
	}

	private Optional<AuthResponse> returnOptAuthResponse(User userSaved) {
		
		String jwtToken = jwtService.getToken(userSaved);		
		return Optional.ofNullable(new AuthResponse(jwtToken));
		
	}
	
}
