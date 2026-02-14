package com.qingyunyouxiao.fsld.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.qingyunyouxiao.fsld.dtos.ContentDto;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GreetingController {

	@GetMapping("/greeting")
	public ResponseEntity<ContentDto> greeting() {

		return ResponseEntity.ok(new ContentDto("hello from Backend"));

	}
	
}
