package org.limbusnoe.service;

import lombok.RequiredArgsConstructor;
import org.limbusnoe.data.CourseDetails;
import org.limbusnoe.data.UserDetails;
import org.limbusnoe.jpa.models.CourseAssignment;
import org.limbusnoe.jpa.models.PageCompletion;
import org.limbusnoe.jpa.repository.CourseAssignmentRepository;
import org.limbusnoe.jpa.repository.PageCompletionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class StudentInteractionsService {
    private final AuthServiceClient authService;
    private final CourseServiceClient courseService;
    private final CourseAssignmentRepository courseAssignmentRepository;
    private final PageCompletionRepository pageCompletionRepository;

    @Transactional
    public List<CourseAssignment> getCourseAssignments(Long student) {
        return courseAssignmentRepository.findByStudentId(student);
    }
    @Transactional
    public boolean isAssigned(Long student, UUID course) {
        return courseAssignmentRepository.existsByStudentIdAndCourseId(student, course);
    }
    @Transactional
    public List<PageCompletion> getCompletions(Long student) {
        return pageCompletionRepository.findByStudentId(student);
    }
    @Transactional
    public boolean isCompleted(Long student, UUID page) {
        return pageCompletionRepository.existsByStudentIdAndPageId(student, page);
    }

    public void enrichWithDetails(CourseAssignment assignment) {
        CompletableFuture<Map<String, Object>> student = CompletableFuture.supplyAsync(() -> authService.getUserById(assignment.getStudentId()));
        CompletableFuture<Map<String, Object>> course = CompletableFuture.supplyAsync(() -> courseService.getCourseById(assignment.getCourseId()));
        try {
            CompletableFuture.allOf(student, course).get(5000L, TimeUnit.MILLISECONDS);
            Map<String,Object> studentPars = student.join();
            Map<String, Object> coursePars = course.join();
            UserDetails userDetails = new UserDetails(
                    (String) studentPars.get("username"),
                    (String) studentPars.get("email"),
                    (String) studentPars.get("name"),
                    (String) studentPars.get("surname")
            );
            CourseDetails courseDetails = new CourseDetails(
                    (String) coursePars.get("title"),
                    (String) coursePars.get("description")
            );
            assignment.setStudentDetails(userDetails);
            assignment.setCourseDetails(courseDetails);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public void assignStudent(UUID course, Long student) {
        if(courseAssignmentRepository.existsByStudentIdAndCourseId(student, course)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Студент уже назначен на этот курс");
        }
        CourseAssignment assignment = new CourseAssignment();
        assignment.setCourseId(course);
        assignment.setStudentId(student);
        assignment.setAssignTime(Timestamp.from(Instant.now()));
        courseAssignmentRepository.save(assignment);
    }

    public void completePage(UUID page, Long student) {
        if(pageCompletionRepository.existsByStudentIdAndPageId(student, page)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Студент уже прочитал эту страницу");
        }
        PageCompletion completion = new PageCompletion();
        completion.setPageId(page);
        completion.setStudentId(student);
        completion.setCompletionTime(Timestamp.from(Instant.now()));
        pageCompletionRepository.save(completion);
    }
}
