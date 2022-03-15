package com.example.tpp_practice.controllers;

import com.example.tpp_practice.logger.Event.Event;
import com.example.tpp_practice.logger.Event.EventType;
import com.example.tpp_practice.logger.EventLogger;
import com.example.tpp_practice.model.FileInfo;
import com.example.tpp_practice.model.SortOptions;
import com.example.tpp_practice.model.User;
import com.example.tpp_practice.services.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class MainPageController {
    @Autowired
    FileInfoService service;

    @Autowired
    EventLogger eventLogger;

    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public String showSortedFiles(@AuthenticationPrincipal User user, @RequestParam("path") String path,
                                  @RequestParam("mode") Integer sortMode, Model model, @RequestParam("up") Integer up) throws IOException {
        SortOptions option = null;
        switch (sortMode){
            case 1:
                option = SortOptions.DEFAULT;
                break;
            case 2:
                option = SortOptions.DATE_ASCENDING;
                break;
            case 3:
                option = SortOptions.DATE_DESCENDING;
                break;
            case 4:
                option = SortOptions.NAME_ASCENDING;
                break;
            case 5:
                option = SortOptions.NAME_DESCENDING;
                break;
            case 6:
                option = SortOptions.SIZE_ASCENDING;
                break;
            case 7:
                option = SortOptions.SIZE_DESCENDING;
                break;
        }
        var upPath = "";
        if(up == 0){
             upPath= moveUp(path);
        } else {
            upPath = path;
        }
        ArrayList<FileInfo> files = (ArrayList<FileInfo>) service.sortFilesByDate(upPath, option);
        model.addAttribute("files", files);
        model.addAttribute("path", upPath);
        model.addAttribute("mode", sortMode);
        model.addAttribute("listSize", files.size());
        return "double";
    }

    public String moveUp(String path) {
        var mpn = path.lastIndexOf('/');
        var result = path.substring(0, mpn);
        if(result.equals("")){
            return "/";
        } else {
            return result;
        }
    }
}
