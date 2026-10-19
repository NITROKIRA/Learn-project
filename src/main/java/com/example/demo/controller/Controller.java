package com.example.demo.controller;


import com.example.demo.model.student;
import com.example.demo.service.service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class Controller {

    private final service serv;
    @PostMapping("/add")
    public String addStudent(@RequestBody student s)
    {
        serv.addStudent(s);
        return serv.print(s) + "data added to database";
    }

    @GetMapping("/hello")
    public String  hello(HttpServletRequest request)
    {
        System.out.println("hello called");
        return "Hello" ;
    }

    @GetMapping("/hi")
    public String  hi(HttpServletRequest request1)
    {
        System.out.println("hi called");
        return "Hi" ;
    }

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();   // name, email, picture...
    }

}