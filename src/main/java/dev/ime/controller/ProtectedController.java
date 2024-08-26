package dev.ime.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedController {
	
	@GetMapping
	public ResponseEntity<String> getStatus(){
		
		return ResponseEntity.ok("Still protected working...");
		
	}
	
	@GetMapping("/get-hello")
	public ResponseEntity<String> getHello(){
		
		return ResponseEntity.ok("hello protected world");
		
	}
	
}
