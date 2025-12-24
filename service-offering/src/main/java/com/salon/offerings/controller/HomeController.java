package com.salon.offerings.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String HomeController(){
        return "Service Offering Microservice for salon booking system";
    }
}
