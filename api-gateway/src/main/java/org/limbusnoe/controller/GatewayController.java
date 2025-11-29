package org.limbusnoe.controller;

import lombok.RequiredArgsConstructor;
import org.limbusnoe.security.AuthServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GatewayController {
    private AuthServiceClient authService;

    @GetMapping("login")
    private String login(Model model) {
        return "login";
    }

    @GetMapping("register")
    private String register(Model model) {
        return "register";
    }

    @GetMapping("test")
    @PreAuthorize("isAuthenticated()")
    private ResponseEntity<String> test(Authentication authentication) {
        return ResponseEntity.ok("Access granted for: " + authentication.getName());
    }
    @GetMapping("/")
    public String blank() {
        return "redirect:/home";
    }
}
