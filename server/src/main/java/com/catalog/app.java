package com.catalog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@Slf4j
public class app
{

    public static void main(String[] args)
    {
        SpringApplication.run(app.class, args);
        log.info("FzuCreatureCatalog Server Started");
    }

}
