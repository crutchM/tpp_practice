package com.example.tpp_practice.model;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "file_info")

public class FileInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name="path")
    private String path;
    @Column(name="extension")
    private String extension;
    @Column(name="size")
    private long size;
    @Column(name="user_id")
    private Long userId;
    private Date date;


    public FileInfo(String name, String path, String extension, long size){
        this.name = name;
        this.path = path;
        this.extension = extension;
        this.size = size;
        this.date = new Date();
    }

    public FileInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
