package com.example.tpp_practice.controllers;

import com.example.tpp_practice.model.FileInfo;
import com.example.tpp_practice.model.User;
import com.example.tpp_practice.services.FileInfoService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.ArrayList;

@Controller
public class FileController {
    @Autowired
    FileInfoService service;

    @GetMapping(path = "/")
    public String mainPg(@AuthenticationPrincipal User user, @RequestParam("path") String path, Model model){
        ArrayList<FileInfo> files = (ArrayList<FileInfo>) service.getFiles(path);
        model.addAttribute("files", files);
        return "index";
    }
}
