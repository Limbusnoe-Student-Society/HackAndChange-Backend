package org.limbusnoe.service;

import org.limbusnoe.data.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "course-service", path = "/api/course", url = "course-service:8082")
public interface CourseServiceClient {

    @GetMapping("/course/{id}")
    Map<String, Object> getCourseById(@PathVariable UUID id);

    @GetMapping("/has-access-for-page/{page}/{student}")
    boolean hasAccessForPage(@PathVariable UUID page, @PathVariable Long student);
}