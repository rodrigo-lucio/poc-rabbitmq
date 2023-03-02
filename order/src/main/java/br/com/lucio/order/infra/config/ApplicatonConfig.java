package br.com.lucio.order.infra.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableJpaAuditing
@EnableAsync
public class ApplicatonConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
