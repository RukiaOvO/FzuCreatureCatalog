package com.catalog.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.druid")
@Data
public class MysqlProperties
{
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String schemaAdr;
    private String dataAdr;
}
