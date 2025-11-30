package org.limbusnoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StudentInteractionsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentInteractionsServiceApplication.class, args);
    }
}