package org.limbusnoe.jpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "course-pages")
public class CoursePage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "page_id", updatable = false, nullable = false)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PageType type;
    @Column(nullable = false)
    private String title;

    private String content;

    private String videoUrl;

    @Column(nullable = false)
    private Integer ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    @JsonIgnore
    private CourseLesson lesson;

    public enum PageType {
        TEXT,
        VIDEO,
        QUIZ
    }
}
