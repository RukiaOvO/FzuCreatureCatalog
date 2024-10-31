package com.catalog.config;

import com.catalog.properties.AsyncPoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer
{
    @Autowired
    private AsyncPoolProperties asyncPoolProperties;

    @Bean("AsyncPool")
    @Override
    public Executor getAsyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(asyncPoolProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(asyncPoolProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(asyncPoolProperties.getThreadNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();

        return executor;
    }
}