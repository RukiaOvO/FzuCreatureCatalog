package com.catalog.config;

import com.catalog.service.ImgService;
import com.catalog.service.Impl.ImgServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Data
@Component
@Slf4j
public class CatalogConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
    @Autowired
    private ImgServiceImpl imgService;
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext)
    {
    }

    @EventListener
    public void handleApplicationReadyEvent(ApplicationReadyEvent e)
    {
        ImgServiceImpl.imgToken = imgService.getImgBedToken();
    }

    @EventListener
    public void handleContextClosedEvent(ContextClosedEvent e)
    {

    }
}
