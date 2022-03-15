package com.example.tpp_practice.controllers;

import com.example.tpp_practice.logger.Event.Event;
import com.example.tpp_practice.logger.Event.EventType;
import com.example.tpp_practice.logger.EventLogger;
import com.example.tpp_practice.model.FileInfo;
import com.example.tpp_practice.services.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@RequestMapping(path = "/file")
public class FileController {

    @Autowired
    private FileInfoService service;

    @Autowired
    private EventLogger eventLogger;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) throws IOException {
        try {
            Resource file = service.download(id);
            eventLogger.logEvent(Event.level(EventType.INFO).that("trying to download file"));
            return ResponseEntity.ok().header("Content-Disposition", "attachment;").body(file);
        } catch (IOException e){
            eventLogger.logEvent(Event.level(EventType.WARN).that("error, can't find file"));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update")
    public RedirectView updateFile(@RequestParam("id") Long id, @RequestParam("newName") String newName,
                                   @RequestParam("path") String path, RedirectAttributes attrs) throws IOException{
        try {


            var result = service.update(id, newName);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/getFiles?path=" + path + "&mode=1" + "&up=1");
            eventLogger.logEvent(Event.level(EventType.INFO).that("filename changed"));
            attrs.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
            attrs.addAttribute("attribute", "redirectWithRedirectView");
            return new RedirectView("/getFiles?path=" + path + "&mode=1" + "&up=1");
        } catch (IOException e){
            eventLogger.logEvent(Event.level(EventType.WARN).that("can't change filename"));
            return new RedirectView("/getFiles?path=" + path + "&mode=1" + "&up=1");
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<FileInfo> upload(@RequestParam MultipartFile attachment, @RequestParam("name") String name,
                                           @RequestParam("path") String path, @RequestParam("mode") Integer sortMode){
        try {
            var file =service.upload(attachment, name, path);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/getFiles?path=" + file.getPath() + "&mode=" + sortMode+"&up=1");
            eventLogger.logEvent(Event.level(EventType.INFO).that("file: " + name + " successfully added on drive by path " + path));
            return new ResponseEntity<>(file, headers, HttpStatus.FOUND);
        } catch (IOException e){
            eventLogger.logEvent(Event.level(EventType.WARN).that("can't upload a file"));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<FileInfo> delete(@PathVariable("id") Long id){
        try {
            var file = service.delete(id);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location", "/getFiles?path=" + file.getPath() + "&mode=1"+"&up=1");
            eventLogger.logEvent(Event.level(EventType.INFO).that("file " + file.getName() + " successfully deleted"));
            return new ResponseEntity<>(file, httpHeaders, HttpStatus.FOUND);
        } catch (IOException e){
            eventLogger.logEvent(Event.level(EventType.WARN).that("can't find or delete file"));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/mkdir")
    public ResponseEntity<FileInfo> mkdir(@RequestParam("name") String name, @RequestParam("path") String path){
        try {
            var folder = service.mkdir(path, name);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location", "/getFiles?path=" + folder.getPath()  + folder.getName()
                    + "/" + "&mode=1"+"&up=1");
            //eventLogger.logEvent(Event.level(EventType.INFO).that("directory " + folder.getName() + " successfully added"));
            return new ResponseEntity<>(folder, httpHeaders, HttpStatus.FOUND);
        } catch (IOException e){
            //eventLogger.logEvent(Event.level(EventType.WARN).that("can't create a folder: " + name));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
