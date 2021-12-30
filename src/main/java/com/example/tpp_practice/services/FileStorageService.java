package com.example.tpp_practice.services;

import com.example.tpp_practice.model.FileInfo;
import com.example.tpp_practice.repositories.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
@Service
public class FileStorageService implements FileInfoService{
    private static final String root= "/home/crutchm/Загрузки/tpp_practice/src/main/resources/files";

    @Autowired
    FileInfoRepository repository;

//    @Override
//    public FileInfo upload(MultipartFile resource, String name,String path) throws IOException {
//        String extension = "";
//        extension = getExtension(name);
//        if(extension.equals("")){
//            extension = "folder";
//        }
//        FileInfo newFile =new FileInfo(name, path, extension, resource.getSize());
//        var upl = repository.save(newFile);
//        String p = "";
//        if(path.equals("/")){
//            p = root + path + upl.getName();
//        } else {
//            p = root + path + "/" + upl.getName();
//        }
//        File file = new File(p);
//        file.createNewFile();
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream(file);
//            out.write(resource.getBytes());
//        }finally {
//            out.close();
//        }
//        return upl;
//    }
@Override
public FileInfo upload(MultipartFile resource, String name, String path) throws IOException {
        System.out.println("ошибка здесь");
        FileInfo newFile = new FileInfo(name, path, getExtension(name), resource.getSize());
        var up = repository.save(newFile);
        File file;
        if(path.equals("/")){
            file = new File(root + path + up.getName());
        }else {
            file = new File(root + path + '/' + up.getName());
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(root + path + '/' + up.getName());
            out.write(resource.getBytes());
        } finally {
            out.close();
        }
        return up;
}

    @Override
    public Resource download(Long id) throws IOException {
        var file = repository.getById(id);
        Path path;
        if (file.getPath().equals("/"))
            path = Path.of(root + file.getPath() + file.getName());
        else {
            path = Path.of(root + file.getPath() + '/' + file.getName());
        }
        Resource resource = new UrlResource(path.toUri());
        if(resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new IOException();
        }
    }

    @Override
    public FileInfo mkdir(String path, String name) throws IOException {
        FileInfo info = new FileInfo(name, path, "folder", 0);
        var file = repository.save(info);
        Files.createDirectory(Path.of(root + path + name));
        return file;
    }

    @Override
    public List<FileInfo> getFiles(String path) {
        return repository.findByPath(path);
    }

    @Override
    public FileInfo delete(Long id) throws IOException {
        FileInfo file = repository.findById(id).get();
        var p = root + file.getPath() + file.getName();
        boolean b = Files.deleteIfExists(Path.of(p));
        repository.delete(file);
        return file;
    }

    public String getExtension(String name){
        String ext = "";
        var i = name.lastIndexOf('.');
        if(i > 0){
            ext = name.substring(i+1);
        }
        return ext;
    }

}
