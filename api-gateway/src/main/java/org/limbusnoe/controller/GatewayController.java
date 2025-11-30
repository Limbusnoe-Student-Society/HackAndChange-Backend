package org.limbusnoe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GatewayController {

    @GetMapping("login")
    private String login(Model model) {
        return "login";
    }

    @GetMapping("register")
    private String register(Model model) {
        return "register";
    }

    @GetMapping("/")
    public String blank() {
        return "redirect:/home";
    }
}
