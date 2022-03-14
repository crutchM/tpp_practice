package com.example.tpp_practice.controllers;

import com.example.tpp_practice.logger.Event.Event;
import com.example.tpp_practice.logger.Event.EventType;
import com.example.tpp_practice.logger.EventLogger;
import com.example.tpp_practice.model.FileInfo;
import com.example.tpp_practice.model.SortOptions;
import com.example.tpp_practice.model.User;
import com.example.tpp_practice.services.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class MainPageController {
    @Autowired
    FileInfoService service;

    @Autowired
    EventLogger eventLogger;

    @GetMapping(path = "/getFiles")
    public String showSortedFiles(@AuthenticationPrincipal User user, @RequestParam("path") String path,
                                  @RequestParam("mode") Integer sortMode, Model model){
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
        ArrayList<FileInfo> files = (ArrayList<FileInfo>) service.sortFilesByDate(path, option);
        model.addAttribute("files", files);
        model.addAttribute("path", path);
        model.addAttribute("mode", sortMode);
        return "double";
    }
}
