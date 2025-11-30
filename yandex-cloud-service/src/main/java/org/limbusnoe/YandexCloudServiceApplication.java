package org.limbusnoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class YandexCloudServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(YandexCloudServiceApplication.class, args);
    }
}
