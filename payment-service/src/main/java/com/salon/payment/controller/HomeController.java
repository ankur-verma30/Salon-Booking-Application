package com.salon.payment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String HomeController(){
        return "Payment microservice for salon booking system";
    }
}
