package org.limbusnoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {
    @Bean
    protected Logger logger() {
        return LoggerFactory.getLogger(AuthServiceApplication.class);
    }
}
