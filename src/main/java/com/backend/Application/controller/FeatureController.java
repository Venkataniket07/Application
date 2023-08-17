package com.backend.Application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backendApplication/feature")
public class FeatureController {

    @GetMapping("greetings")
    public ResponseEntity<String> greetings() {
        return ResponseEntity.ok("Hello mother-fucker");
    }
}
