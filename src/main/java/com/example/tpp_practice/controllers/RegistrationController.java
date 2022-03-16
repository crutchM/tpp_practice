package com.example.tpp_practice.controllers;

import com.example.tpp_practice.logger.Event.Event;
import com.example.tpp_practice.logger.Event.EventType;
import com.example.tpp_practice.logger.EventLogger;
import com.example.tpp_practice.model.User;
import com.example.tpp_practice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RegistrationController {

    @Autowired
    UserService service;

    @Autowired
    EventLogger logger;

    @GetMapping("/reg")
    public String registration(Model model){
        model.addAttribute("userForm", new User());
        return "reg";
    }


    @PostMapping("/reg")
    public RedirectView addUser(@ModelAttribute("useForm") User useForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            logger.logEvent(Event.level(EventType.WARN).that("/reg: something went wrong on addUser method").now());
            return new RedirectView("/reg");
        }
        if(service.saveUser(useForm)){
            logger.logEvent(Event.level(EventType.WARN).that("error: duplicate of username" ).now());
            model.addAttribute("/reg: usernameError", "пользователь с таким именем уже существует");
            return new RedirectView("/reg");
        }
        logger.logEvent(Event.level(EventType.INFO).that("/reg: new user successfully added").now());
        service.saveUser(useForm);
        return new RedirectView("/getFiles?path=/&mode=1&up=1");
    }
}
