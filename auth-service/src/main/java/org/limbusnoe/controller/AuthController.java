package org.limbusnoe.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.limbusnoe.data.LoginDto;
import org.limbusnoe.data.RegisterDto;
import org.limbusnoe.security.CookieData;
import org.limbusnoe.service.AuthService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterDto dto, HttpServletResponse response, RedirectAttributes attr) {
        try {
            authService.addUser(dto).respond(response);
        } catch (ResponseStatusException e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
        return "redirect:/home";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody LoginDto dto, HttpServletResponse response, RedirectAttributes attr) {
        try {
            authService.loginUser(dto).respond(response);
        } catch (ResponseStatusException e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logoutUser(HttpServletResponse response) {
        CookieData.NO_COOKIE.respond(response);
        return "redirect:/home";
    }
}
