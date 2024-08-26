package dev.ime.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PlaneController {
	

	@GetMapping
	public ResponseEntity<String> getStatus(){
		
		return ResponseEntity.ok("Still working...");
		
	}
	
	@GetMapping("/get-hello")
	public ResponseEntity<String> getHello(){
		
		return ResponseEntity.ok("hello world");
		
	}
	
}
