package com.app.my_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class RootController {




    @GetMapping("/public/")
    public String rootApplication()
    {
        return "Hello with security";
    }
}
