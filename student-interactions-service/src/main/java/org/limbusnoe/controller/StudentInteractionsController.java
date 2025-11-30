package org.limbusnoe.controller;

import lombok.RequiredArgsConstructor;
import org.limbusnoe.jpa.models.CourseAssignment;
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
    
    @PostMapping("assign-course/{course}/{student}")
    private void assignCourse(@PathVariable UUID course, @PathVariable Long student) {
        service.assignStudent(course, student);
    }
    @GetMapping("is-assigned/{course}/{student}")
    private boolean isAssigned(@PathVariable UUID course, @PathVariable Long student) {
        return service.isAssigned(student, course);
    }
    @GetMapping("is-page-read/{page}/{student}")
    private boolean isPageRead(@PathVariable UUID page, @PathVariable Long student) {
        return service.isCompleted(student, page);
    }
    @PostMapping("complete-page/{page}/{student}")
    private void completeCourse(@PathVariable UUID page, @PathVariable Long student) {
        if(service.isAssigned(student, page)) {
            service.completePage(page, student);
        }
    }
    @GetMapping("get-active-courses/{student}")
    private ResponseEntity<?> getActiveCourses(@PathVariable Long student) {
        List<CourseAssignment> courseAssignments = service.getCourseAssignments(student);
        courseAssignments.forEach(service::enrichWithDetails);
        return ResponseEntity.ok(courseAssignments);
    }
}
