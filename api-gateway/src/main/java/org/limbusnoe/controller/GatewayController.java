package org.limbusnoe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.limbusnoe.data.LoginDto;
import org.limbusnoe.data.RegisterDto;
import org.limbusnoe.service.AuthServiceClient;
import org.limbusnoe.service.CourseServiceClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class GatewayController {
    private final AuthServiceClient authService;
    private final CourseServiceClient courseService;
    @GetMapping("login")
    public String login() {
        return "login";
    }
    @PostMapping("login")
    public String loginUser(@Valid LoginDto dto, HttpServletResponse response, RedirectAttributes attr) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", dto.getUsername());
        formData.add("password", dto.getPassword());
        Map<String, Object> data = authService.loginUser(formData);
        if(data.containsKey("cookie")) {
            response.addHeader(HttpHeaders.SET_COOKIE, (String) data.get("cookie"));
        }
        if(data.containsKey("error")) {
            attr.addFlashAttribute("error", data.get("error"));
        }
        return (String) data.get("view");
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }
    @GetMapping("home")
    public String home(Model model) {
        var courses = courseService.getCourses();
        model.addAttribute("courses", courses);
        return "home";
    }
    @GetMapping("course/{id}")
    public String course(Model model, @PathVariable UUID id) {
        var course = courseService.getCourse(id);
        model.addAttribute("course", course);
        return "course";
    }
    @GetMapping("course/{id}/{page}")
    public String coursePage(Model model, @PathVariable UUID id, @PathVariable Integer page) {
        var course = courseService.getCourse(id);
        System.out.println(course);
        System.out.println(course.getClass());
        model.addAttribute("course", course);
        List pages = (List) courseService.getAllPages(id);
        model.addAttribute("pages", pages);
        model.addAttribute("page", pages.get(page-1));
        System.out.println(pages);
        System.out.println(pages.getClass());
        return "course_page";
    }
    @PostMapping("register")
    public String register(RegisterDto dto,
                           HttpServletResponse response,
                           RedirectAttributes attr) {
        MultiValueMap<String, String> formData = convert(dto);
        Map<String, Object> data = authService.registerUser(formData);
        if(data.containsKey("cookie")) {
            response.addHeader(HttpHeaders.SET_COOKIE, (String) data.get("cookie"));
        }
        if(data.containsKey("error")) {
            attr.addFlashAttribute("error", data.get("error"));
        }
        return (String) data.get("view");
    }

    private static MultiValueMap<String, String> convert(RegisterDto dto) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", dto.getUsername());
        formData.add("email", dto.getEmail());
        formData.add("name", dto.getName());
        formData.add("surname", dto.getSurname());
        formData.add("patronymic", dto.getPatronymic());
        formData.add("birthDate", dto.getBirthDate().toString());
        formData.add("password", dto.getPassword());
        formData.add("passwordConfirm", dto.getPasswordConfirm());
        return formData;
    }

    @PostMapping("logout")
    public String logout(HttpServletResponse response) {
        Map<String, Object> data = authService.logoutUser();
        if(data.containsKey("cookie")) {
            response.addHeader(HttpHeaders.SET_COOKIE, (String) data.get("cookie"));
        }
        return (String) data.get("view");
    }
    @GetMapping("/")
    public String blank() {
        return "redirect:/home";
    }
}
