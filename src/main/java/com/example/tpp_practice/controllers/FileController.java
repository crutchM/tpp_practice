package com.example.tpp_practice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileController {
    @GetMapping(path = "/")
    public String mainPg(){
        return "index";
    }
}
