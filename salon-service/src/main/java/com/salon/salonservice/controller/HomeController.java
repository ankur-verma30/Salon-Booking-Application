package com.salon.salonservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String HomeController(){
        return "Salon microservice for salon booking system";
    }
}
