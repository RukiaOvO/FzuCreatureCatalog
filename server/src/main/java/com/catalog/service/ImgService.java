package com.catalog.service;

import java.io.File;

public interface ImgService
{
    String getImgBedToken();
    String uploadImgToBed(File file);
    String deleteImgInBed(String key);
}
