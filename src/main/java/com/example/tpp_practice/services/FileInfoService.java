package com.example.tpp_practice.services;

import com.example.tpp_practice.model.FileInfo;
import com.example.tpp_practice.model.SortOptions;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileInfoService {
    FileInfo upload(MultipartFile resource, String name, String path) throws IOException;
    Resource download(Long id) throws IOException;
    FileInfo mkdir(String path, String name) throws IOException;
    FileInfo delete(Long id) throws IOException;
    List<FileInfo> sortFilesByDate(String path, SortOptions option);
    FileInfo update(Long id, String newName);
}
