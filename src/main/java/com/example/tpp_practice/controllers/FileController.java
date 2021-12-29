package com.example.tpp_practice.controllers;

import com.example.tpp_practice.model.FileInfo;
import com.example.tpp_practice.model.User;
import com.example.tpp_practice.services.FileInfoService;
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

    @GetMapping(path = "/getFiles")
    public String mainPg(@AuthenticationPrincipal User user, @RequestParam("path") String path, Model model){
        ArrayList<FileInfo> files = (ArrayList<FileInfo>) service.getFiles(path);
        ArrayList<ArrayList<FileInfo>> dividedFiles = divideList(files);
        model.addAttribute("files", dividedFiles);
        return "index";
    }



    private ArrayList<ArrayList<FileInfo>> divideList(ArrayList<FileInfo> list){
        ArrayList<ArrayList<FileInfo>> result = new ArrayList<>();
        ArrayList<FileInfo> subResult = new ArrayList<>();
        if(list.isEmpty())
            return null;
        for(var e : list){
            subResult.add(e);
            if(subResult.size() == 5){
                result.add(subResult);
                subResult = new ArrayList<>();
            } else {
                subResult.add(e);
            }
        }
        return result;
    }
}
