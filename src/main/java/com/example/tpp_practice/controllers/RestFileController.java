package com.example.tpp_practice.controllers;

import com.example.tpp_practice.services.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/file")
public class RestFileController {

    @Autowired
    private FileInfoService service;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") Long id){
        try {
            Resource resource = service.download(id);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + resource.getFile().getName()).body(resource);

        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
