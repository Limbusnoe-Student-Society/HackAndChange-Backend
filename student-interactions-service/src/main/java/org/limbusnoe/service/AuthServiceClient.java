package org.limbusnoe.service;

import org.limbusnoe.data.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "auth-service", path = "/api/auth", url = "auth-service:8081")
public interface AuthServiceClient {
    
    @PostMapping("/validate")
    TokenValidationResponse validateToken(@CookieValue(name = "jwt") String token);

    @GetMapping("/user/{id}")
    Map<String, Object> getUserById(@PathVariable Long id);
}