package com.catalog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "catalog.img")
@Data
public class ImgBedProperties
{
    private String url;
    private String email;
    private String password;
}
