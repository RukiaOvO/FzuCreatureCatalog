package com.catalog.service;

import com.catalog.entity.Img;

import java.io.File;

public interface ImgService
{
    String getImgBedToken();
    String uploadImgToBed(File file);
    void deleteImgInBed(String key);
    Img getImageById(int imgId);
}
