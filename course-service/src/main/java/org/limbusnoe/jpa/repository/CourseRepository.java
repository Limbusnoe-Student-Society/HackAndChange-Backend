package org.limbusnoe.jpa.repository;

import org.limbusnoe.jpa.models.Course;
import org.limbusnoe.jpa.models.CoursePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    @Query("""
            SELECT c.id FROM Course AS c
                JOIN CourseModule AS m ON m.course.id=c.id
                JOIN CourseLesson AS l ON l.module.id=m.id
                JOIN CoursePage AS p ON p.lesson.id=l.id
                WHERE p.id=:pageId""")
    Optional<UUID> findCourseIdByPageId(@Param("pageId") UUID pageId);

    @Query("SELECT cp FROM CoursePage cp " +
            "JOIN cp.lesson cl " +
            "JOIN cl.module cm " +
            "JOIN cm.course c " +
            "WHERE c.id = :courseId " +
            "ORDER BY cm.ordering ASC, cl.ordering ASC, cp.ordering ASC")
    List<CoursePage> findAllByCourseIdOrdered(@Param("courseId") UUID courseId);
}
