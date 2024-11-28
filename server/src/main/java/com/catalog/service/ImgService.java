package com.catalog.service;

import com.catalog.entity.Img;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImgService
{
    String getImgBedToken();
    Img uploadImgToBed(MultipartFile file);
    void deleteImgInBed(String key);
    Img getImageById(int imgId);
}
