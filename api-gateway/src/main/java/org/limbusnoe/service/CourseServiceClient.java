package org.limbusnoe.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "course-service", path = "/api/course", url = "course-service:8082")
public interface CourseServiceClient {
    @GetMapping("/courses")
    Object getCourses();
    @GetMapping("/course/{id}")
    Object getCourse(@PathVariable UUID id);
    @GetMapping("/all-pages/{id}")
    Object getAllPages(@PathVariable UUID id);
}
