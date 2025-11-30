package org.limbusnoe.jpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "course_quiz_options")
public class CourseQuizOption {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "quiz_option_id", updatable = false, nullable = false)
    private UUID id;

    private String text;

    private int ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private CourseQuiz quiz;
}