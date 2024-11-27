package com.catalog.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil
{
    public static File MultipartFileToFile(MultipartFile multipartFile)
    {
        try
        {
            File tempFile = File.createTempFile("temp", multipartFile.getOriginalFilename());
            multipartFile.transferTo(tempFile);
            return tempFile;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}
