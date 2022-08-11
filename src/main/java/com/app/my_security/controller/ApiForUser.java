package com.app.my_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class ApiForUser {

    @GetMapping("/")
    public String userSaysTest()
    {
        return "This is authorized user!";
    }
}
