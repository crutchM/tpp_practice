package com.example.tpp_practice.services;

import com.example.tpp_practice.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileInfoService {
    FileInfo upload(MultipartFile resource, String name, String path) throws IOException;
    Resource download(Long id) throws IOException;
    FileInfo mkdir(String path, String name) throws IOException;
    List<FileInfo> getFiles(String path);
    FileInfo delete(Long id) throws IOException;
}
