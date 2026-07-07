package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class exampleController{

    @GetMapping("/")
    public String getInfo(){

        return " hello my name is Dhiraj";
    }
}