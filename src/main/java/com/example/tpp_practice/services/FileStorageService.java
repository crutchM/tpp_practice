package com.example.tpp_practice.services;

import com.example.tpp_practice.model.FileInfo;
import com.example.tpp_practice.model.SortOptions;
import com.example.tpp_practice.repositories.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
@Service
public class FileStorageService implements FileInfoService{
    private static final String root= "D:\\node\\java\\tpp\\src\\main\\resources\\stash";

    @Autowired
    FileInfoRepository repository;

@Override
public FileInfo upload(MultipartFile resource, String name, String path) throws IOException {
        System.out.println("ошибка здесь");
        FileInfo newFile = new FileInfo(getName(name), path, getExtension(name), resource.getSize());
        var up = repository.save(newFile);
        FileOutputStream out = null;
        try {
            if(path.equals("/")) {
                out = new FileOutputStream(root + path + up.getName() +'.'+ up.getExtension());
            } else {
                out = new FileOutputStream(root + path + '/' + up.getName() +'.'+ up.getExtension());
            }
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
            path = Path.of(root + file.getPath() + file.getName() + '.' + file.getExtension());
        else {
            path = Path.of(root + file.getPath() + '/' + file.getName() + '.' + file.getExtension());
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
    public FileInfo delete(Long id) throws IOException {
        FileInfo file = repository.findById(id).get();
        var p = root + file.getPath() + file.getName();
        boolean b = Files.deleteIfExists(Path.of(p));
        repository.delete(file);
        return file;
    }

    @Override
    public List<FileInfo> sortFilesByDate(String path, SortOptions option) {
        switch (option){
            case DATE_ASCENDING:
                return repository.findByPath(path, Sort.by("date").ascending());
            case DATE_DESCENDING:
                return repository.findByPath(path, Sort.by("date").descending());
            case SIZE_ASCENDING:
                return repository.findByPath(path, Sort.by("size").ascending());
            case SIZE_DESCENDING:
                return repository.findByPath(path, Sort.by("size").descending());
            case NAME_ASCENDING:
                return repository.findByPath(path, Sort.by("name").ascending());
            case NAME_DESCENDING:
                return repository.findByPath(path, Sort.by("name").descending());
            case DEFAULT:
                return repository.findByPath(path, null);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public FileInfo update(Long id, String newName) {
        FileInfo file = repository.findById(id).get();
        File f = new File(file.getPath() + file.getName() + '.' + file.getExtension());
        file.setName(newName);
        File fn = new File(file.getPath() + newName + '.' + file.getExtension());
        if(f.renameTo(fn)) {
            repository.deleteById(id);
            repository.save(file);
            return file;
        } else {
            return null;
        }
    }

    public String getExtension(String name){
        String ext = "";
        var i = name.lastIndexOf('.');
        if(i > 0){
            ext = name.substring(i+1);
        }
        return ext;
    }

    public String getName(String line){
        StringBuilder name = new StringBuilder();
        for(char e : line.toCharArray()){
            if(e == '.'){
                break;
            } else {
                name.append(e);
            }
        }
        return name.toString();
    }

}
