package com.br.paulohbs.registraion_cd.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
