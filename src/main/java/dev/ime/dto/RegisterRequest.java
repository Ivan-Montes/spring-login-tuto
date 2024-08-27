package dev.ime.dto;

public record RegisterRequest(
		String name,	
		String lastname,	
		String email,
		String password
		) {
	
}
