package com.jperat.postit.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.jperat.postit.model.File;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    File store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
    
    boolean deleteUploadFile(String filename);

}
