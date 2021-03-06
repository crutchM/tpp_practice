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

    @PostMapping("/move")
    public RedirectView moveFile(@RequestParam("id") Long id, @RequestParam("dir") String dirName){
        try {
            var dist = "";
            if(dirName.equals("up")){
                dist = moveUp(service.getFile(id).getPath());
            } else {
                dist = dirName;
            }
            var movedFIle = service.move(id, dist);
            eventLogger.logEvent(Event.level(EventType.WARN).that("/move: success"));
            var rv = new RedirectView("/getFiles?path="+ movedFIle.getPath() + "&mode=1&up=1");
            rv.setStatusCode(HttpStatus.FOUND);
            return rv;
        } catch (IOException e){
            eventLogger.logEvent(Event.level(EventType.WARN).that("/move: can't find directory"));
            var rv = new RedirectView("/getFiles?path="+ service.getFile(id).getPath() + "&mode=1&up=1");
            rv.setStatusCode(HttpStatus.NOT_FOUND);
            return rv;
        }
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) throws IOException {
        try {
            Resource file = service.download(id);
            HttpHeaders headers = new HttpHeaders();
            eventLogger.logEvent(Event.level(EventType.INFO).that("/download: trying to download file"));
            return ResponseEntity.ok().header("Content-Disposition", "attachment;").body(file);
        } catch (IOException e){
            eventLogger.logEvent(Event.level(EventType.WARN).that("/download: error, can't find file"));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update")
    public RedirectView updateFile(@RequestParam("id") Long id, @RequestParam("newName") String newName,
                                   @RequestParam("path") String path) throws IOException{
        try {


            var result = service.update(id, newName);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/getFiles?path=" + path + "&mode=1" + "&up=1");
            eventLogger.logEvent(Event.level(EventType.INFO).that("/update:filename changed"));
            var rv = new RedirectView("/getFiles?path=" + path + "&mode=1" + "&up=1");
            rv.setStatusCode(HttpStatus.OK);
            return rv;
        } catch (IOException e){
            eventLogger.logEvent(Event.level(EventType.WARN).that("/update: can't change filename"));
            var rv =new RedirectView("/getFiles?path=" + path + "&mode=1" + "&up=1");
            rv.setStatusCode(HttpStatus.CONFLICT);
            return rv;
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<FileInfo> upload(@RequestParam MultipartFile attachment, @RequestParam("name") String name,
                                           @RequestParam("path") String path, @RequestParam("mode") Integer sortMode){
        try {
            var file =service.upload(attachment, name, path);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/getFiles?path=" + file.getPath() + "&mode=" + sortMode+"&up=1");
            eventLogger.logEvent(Event.level(EventType.INFO).that("/upload: file: " + name + " successfully added on drive by path " + path));
            return new ResponseEntity<>(file, headers, HttpStatus.FOUND);
        } catch (IOException e){
            eventLogger.logEvent(Event.level(EventType.WARN).that("/upload: can't upload a file"));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/getFiles?path=" + path + "&mode=" + sortMode+"&up=1");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<FileInfo> delete(@PathVariable("id") Long id){
        try {
            var file = service.delete(id);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location", "/getFiles?path=" + file.getPath() + "&mode=1"+"&up=1");
            eventLogger.logEvent(Event.level(EventType.INFO).that("/delete: file " + file.getName() + " successfully deleted"));
            return new ResponseEntity<>(file, httpHeaders, HttpStatus.FOUND);
        } catch (IOException e){
            eventLogger.logEvent(Event.level(EventType.WARN).that("/delete: can't find or delete file"));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/mkdir")
    public RedirectView mkdir(@RequestParam("name") String name, @RequestParam("path") String path){
        try {
            var folder = service.mkdir(path, name);
            //HttpHeaders httpHeaders = new HttpHeaders();
           //httpHeaders.add("Location", "/getFiles?path=" + path + "/" + name + "&mode=1"+"&up=1");
            eventLogger.logEvent(Event.level(EventType.INFO).that("/mkdir: directory " + folder.getName() + " successfully added"));
            //return new ResponseEntity<>(folder, httpHeaders, HttpStatus.OK);
            var rv = new RedirectView("/getFiles?path=" + folder.getPath()+ folder.getName() + "&mode=1"+"&up=1");
            rv.setStatusCode(HttpStatus.OK);
            return rv;
        } catch (IOException e){
            eventLogger.logEvent(Event.level(EventType.WARN).that("/mkdir: can't create a folder: " + name));
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            var rv =new RedirectView("/getFiles?path=" + path +"&mode=1&up=1");
            rv.setStatusCode(HttpStatus.NOT_FOUND);
            return rv;
        }
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
