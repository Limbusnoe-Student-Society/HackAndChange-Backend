package org.limbusnoe.jpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.limbusnoe.data.CourseDetails;
import org.limbusnoe.data.UserDetails;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@Entity
@Table(name = "course_assignments")
public class CourseAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_assignment_id", updatable = false, nullable = false)
    private Long id;

    private Long studentId;

    private UUID courseId;

    private Timestamp assignTime;

    @Transient
    private UserDetails studentDetails;
    @Transient
    private CourseDetails courseDetails;
}
