package com.example.tpp_practice.controllers;

import com.example.tpp_practice.model.FileInfo;
import com.example.tpp_practice.services.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/upload")
    public ResponseEntity<FileInfo> upload(@RequestParam MultipartFile attachment, @RequestParam("path") String path){
        try {
            return new ResponseEntity<>(service.upload(attachment, path), HttpStatus.CREATED);

        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FileInfo> delete(@PathVariable("id") Long id){
        try {
            var file = service.delete(id);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location", "/getfiles?path=" + file.getPath());
            return new ResponseEntity<FileInfo>(file, httpHeaders, HttpStatus.OK);
        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
