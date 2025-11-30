package org.limbusnoe.jpa.repository;

import org.limbusnoe.jpa.models.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoursePageRepository extends JpaRepository<CourseLesson, UUID> {
}
