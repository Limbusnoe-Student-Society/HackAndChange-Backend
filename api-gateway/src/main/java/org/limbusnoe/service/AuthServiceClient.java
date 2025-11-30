package org.limbusnoe.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.limbusnoe.data.LoginDto;
import org.limbusnoe.data.RegisterDto;
import org.limbusnoe.data.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "auth-service", path = "/api/auth", url = "auth-service:8081")
public interface AuthServiceClient {

    @PostMapping("/validate")
    TokenValidationResponse validateToken(@CookieValue(name = "jwt", required = true) String token);

    @PostMapping(path= "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String, Object> registerUser(@SpringQueryMap MultiValueMap<String, String> formData);

    @PostMapping(path= "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String, Object> loginUser(@SpringQueryMap MultiValueMap<String, String> formData);

    @PostMapping("/logout")
    Map<String, Object> logoutUser();
}