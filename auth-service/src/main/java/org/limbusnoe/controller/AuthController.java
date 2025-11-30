package org.limbusnoe.controller;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtComponent jwtComponent;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Map<String, Object>> registerUser(@RequestParam MultiValueMap<String, String> formData) {
        RegisterDto dto = new RegisterDto();
        dto.setUsername(formData.getFirst("username"));
        dto.setEmail(formData.getFirst("email"));
        dto.setName(formData.getFirst("name"));
        dto.setSurname(formData.getFirst("surname"));
        dto.setPatronymic(formData.getFirst("patronymic"));
        dto.setPassword(formData.getFirst("password"));
        dto.setPasswordConfirm(formData.getFirst("passwordConfirm"));
        String birthDateStr = formData.getFirst("birthDate");
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            try {
                dto.setBirthDate(LocalDate.parse(birthDateStr));
            } catch (Exception ignored) {
            }
        }
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("cookie", authService.addUser(dto).getCookie().toString());
            data.put("view", "redirect:/home");
        } catch (ResponseStatusException e) {
            data.put("error", e.getMessage());
            data.put("view", "redirect:/register");
        }
        return ResponseEntity.ok(data);
    }

    @PostMapping(path= "/login" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Map<String, Object>> loginUser(@RequestParam MultiValueMap<String, String> formData) {
        LoginDto dto = new LoginDto();
        dto.setUsername(formData.getFirst("username"));
        dto.setPassword(formData.getFirst("password"));
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("cookie", authService.loginUser(dto).getCookie().toString());
            data.put("view", "redirect:/home");
        } catch (ResponseStatusException e) {
            data.put("error", e.getMessage());
            data.put("view", "redirect:/login");
        }
        return ResponseEntity.ok(data);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logoutUser() {
        Map<String, Object> data = new HashMap<>();
        data.put("cookie", CookieData.NO_COOKIE.getCookie().toString());
        data.put("view", "redirect:/home");
        return ResponseEntity.ok(data);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable Long id) {
        return ResponseEntity.ofNullable(authService.getUser(id).map(user -> new UserDetails(user.getUsername(), user.getEmail(), user.getName(), user.getSurname())).orElse(null));
    }
    @GetMapping("/user/")
    public ResponseEntity<Long> getId(String username) {
        return ResponseEntity.ofNullable(authService.getUser(username).map(User::getId).orElse(null));
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
