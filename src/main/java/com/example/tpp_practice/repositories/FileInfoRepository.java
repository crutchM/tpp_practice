package com.example.tpp_practice.repositories;

import com.example.tpp_practice.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    List<FileInfo> findByPath(String path);
}
