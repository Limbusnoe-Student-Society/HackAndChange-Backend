package org.limbusnoe.jpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "course_lessons")
public class CourseLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "lesson_id", updatable = false, nullable = false)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer ordering;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    @JsonIgnore
    private CourseModule module;
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordering ASC")
    private List<CoursePage> pages = new ArrayList<>();
}
