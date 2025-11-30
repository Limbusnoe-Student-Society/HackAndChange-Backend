package org.limbusnoe.controller;

import lombok.RequiredArgsConstructor;
import org.limbusnoe.data.CourseData;
import org.limbusnoe.jpa.models.Course;
import org.limbusnoe.jpa.models.CourseLesson;
import org.limbusnoe.jpa.models.CourseModule;
import org.limbusnoe.jpa.models.CoursePage;
import org.limbusnoe.security.StudentInteractionsService;
import org.limbusnoe.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final StudentInteractionsService studentInteractionsService;

    @GetMapping("courses")
    private Collection<Course> getCourses() {
        return courseService.findAllCourses();
    }

    @PutMapping("course/{id}")
    private void addCourse(@RequestBody CourseData data, @PathVariable UUID id) {
        if (courseService.isCourseExist(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Курс с таким ID уже существует");
        } else {
            Course courseObj = parseCourse(data);
            courseObj.setId(id);
            courseService.save(courseObj);
        }
    }

    @GetMapping("course/{id}")
    private Course getCourse(@PathVariable UUID id) {
        return courseService.getCourse(id);
    }

    @GetMapping("/has-access-for-page/{page}/{student}")
    private boolean hasAccessForPage(@PathVariable UUID page, @PathVariable Long student) {
        return studentInteractionsService.isAssigned(courseService.getCourseIdByPageId(page), student);
    }

    public Course parseCourse(CourseData course) {
        Course courseObj = new Course();
        courseObj.setTitle(course.getTitle());
        courseObj.setDescription(course.getDescription());
        for (CourseData.CourseModuleData module : course.getModules()) {
            CourseModule moduleObj = parseModule(module);
            moduleObj.setCourse(courseObj);
            courseObj.getModules().add(moduleObj);
        }
        return courseObj;
    }

    public CourseModule parseModule(CourseData.CourseModuleData module) {
        CourseModule moduleObj = new CourseModule();
        moduleObj.setTitle(module.getTitle());
        moduleObj.setOrdering(module.getOrder());
        for (CourseData.CourseLessonData lesson : module.getLessons()) {
            CourseLesson lessonObj = parseLesson(lesson);
            lessonObj.setModule(moduleObj);
            moduleObj.getLessons().add(lessonObj);
        }
        return moduleObj;
    }

    public CourseLesson parseLesson(CourseData.CourseLessonData lesson) {
        CourseLesson lessonObj = new CourseLesson();
        lessonObj.setTitle(lesson.getTitle());
        lessonObj.setOrdering(lesson.getOrder());
        for (CourseData.CoursePageData page : lesson.getPages()) {
            CoursePage pageObj = parsePage(page);
            pageObj.setLesson(lessonObj);
            lessonObj.getPages().add(pageObj);
        }
        return lessonObj;
    }

    public CoursePage parsePage(CourseData.CoursePageData page) {
        CoursePage pageObj = new CoursePage();
        pageObj.setTitle(page.getTitle());
        pageObj.setType(CoursePage.PageType.valueOf(page.getPageType()));
        pageObj.setOrdering(page.getOrder());
        pageObj.setContent(page.getContent());
        pageObj.setVideoUrl(page.getVideoUrl());
        return pageObj;
    }

}
