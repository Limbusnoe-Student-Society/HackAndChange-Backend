package org.limbusnoe.service;

import lombok.RequiredArgsConstructor;
import org.limbusnoe.jpa.models.Course;
import org.limbusnoe.jpa.models.CoursePage;
import org.limbusnoe.jpa.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public Course getCourse(UUID id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Курс не найден"));
    }
    @Transactional
    public UUID getCourseIdByPageId(UUID pageId) {
        return courseRepository.findCourseIdByPageId(pageId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Страница не принадлежит какому-либо курсу"));
    }
    @Transactional
    public Collection<Course> findAllCourses() {
        return courseRepository.findAll();
    }
    @Transactional
    public boolean isCourseExist(UUID id) {
        return courseRepository.existsById(id);
    }
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public List<CoursePage> getAllPages(UUID courseId) {
        return courseRepository.findAllByCourseIdOrdered(courseId);
    }
}
