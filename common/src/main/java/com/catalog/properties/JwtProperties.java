package com.catalog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "catalog.jwt")
@Data
public class JwtProperties
{
    private String adminName;
    private String adminPassword;
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}