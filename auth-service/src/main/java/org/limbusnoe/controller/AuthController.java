package org.limbusnoe.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.limbusnoe.data.LoginDto;
import org.limbusnoe.data.RegisterDto;
import org.limbusnoe.data.TokenValidationResponse;
import org.limbusnoe.data.UserDetails;
import org.limbusnoe.jpa.models.User;
import org.limbusnoe.security.CookieData;
import org.limbusnoe.security.JwtComponent;
import org.limbusnoe.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtComponent jwtComponent;
    @PostMapping("/register")
    public String registerUser(@Valid RegisterDto dto, HttpServletResponse response, RedirectAttributes attr) {
        try {
            authService.addUser(dto).respond(response);
        } catch (ResponseStatusException e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
        return "redirect:/home";
    }
    @PostMapping("/login")
    public String loginUser(@Valid LoginDto dto, HttpServletResponse response, RedirectAttributes attr) {
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
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable Long id) {
        return ResponseEntity.ofNullable(authService.getUser(id).map(user -> new UserDetails(user.getUsername(), user.getEmail(), user.getName(), user.getSurname())).orElse(null));
    }
    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(@CookieValue(name = "jwt") String token) {
        if (token != null) {
            if (jwtComponent.validateToken(token)) {
                String username = jwtComponent.getUsernameFromToken(token);
                Set<String> roles = jwtComponent.getRolesFromToken(token);
                return ResponseEntity.ok(new TokenValidationResponse(true, username, roles));
            }
        }
        return ResponseEntity.ok(new TokenValidationResponse(false, null, null));
    }
}
