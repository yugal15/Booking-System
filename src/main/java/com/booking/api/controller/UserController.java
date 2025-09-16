package com.booking.api.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")

public class UserController {

    @PostMapping("/testUser")
    public ResponseEntity<String> seyHello() {
        return ResponseEntity.ok("Hello from User Controller!");
    }
}