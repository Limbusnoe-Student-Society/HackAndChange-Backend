package org.limbusnoe.controller;

import lombok.RequiredArgsConstructor;
import org.limbusnoe.data.TokenValidationResponse;
import org.limbusnoe.jpa.models.CourseAssignment;
import org.limbusnoe.service.AuthServiceClient;
import org.limbusnoe.service.StudentInteractionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/student-interactions")
@RequiredArgsConstructor
public class StudentInteractionsController {
    private final StudentInteractionsService service;
    private final AuthServiceClient authService;
    @PostMapping("assign-course/{course}")
    private void assignCourse(@PathVariable UUID course, @CookieValue(name = "jwt") String token) {
        System.out.println(token);
        if(token != null) {
            TokenValidationResponse tokenValidationResponse = authService.validateToken(token);
            if(tokenValidationResponse.isValid()) {
                Long id = authService.getIdByUsername(tokenValidationResponse.getUsername());
                service.assignStudent(course, id);
            }
        }
    }
    @GetMapping("is-assigned/{course}/{student}")
    private boolean isAssigned(@PathVariable UUID course, @PathVariable Long student) {
        return service.isAssigned(student, course);
    }
    @GetMapping("is-page-read/{page}/{student}")
    private boolean isPageRead(@PathVariable UUID page, @PathVariable Long student) {
        return service.isCompleted(student, page);
    }

    @PostMapping("complete-page/{page}")
    private void completeCourse(@PathVariable UUID page, @CookieValue(name = "jwt") String token) {
        if (token != null) {
            TokenValidationResponse tokenValidationResponse = authService.validateToken(token);
            if (tokenValidationResponse.isValid()) {
                Long id = authService.getIdByUsername(tokenValidationResponse.getUsername());
                if (service.isAssigned(id, page)) {
                    service.completePage(page, id);
                }
            }
        }
    }
    @GetMapping("get-active-courses/{student}")
    private ResponseEntity<?> getActiveCourses(@PathVariable Long student) {
        List<CourseAssignment> courseAssignments = service.getCourseAssignments(student);
        courseAssignments.forEach(service::enrichWithDetails);
        return ResponseEntity.ok(courseAssignments);
    }
}
