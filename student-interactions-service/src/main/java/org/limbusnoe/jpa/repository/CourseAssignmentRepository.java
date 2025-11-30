package org.limbusnoe.jpa.repository;

import org.limbusnoe.jpa.models.CourseAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, Long> {
    List<CourseAssignment> findByStudentId(Long studentId);
    List<CourseAssignment> findByCourseId(UUID courseId);

    Optional<CourseAssignment> findFirstByStudentIdAndCourseId(Long studentId, UUID courseId);

    boolean existsByStudentIdAndCourseId(Long studentId, UUID courseId);
}
