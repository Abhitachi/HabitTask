package com.habitstack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "https://chain-habits.preview.emergentagent.com")
@RequestMapping("/")
public class RootController {
    
    @GetMapping
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("message", "Habit Stack Builder API is running"));
    }
}