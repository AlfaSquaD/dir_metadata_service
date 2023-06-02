package com.dir_music.metadata_service;

import feign.Client;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }
}
