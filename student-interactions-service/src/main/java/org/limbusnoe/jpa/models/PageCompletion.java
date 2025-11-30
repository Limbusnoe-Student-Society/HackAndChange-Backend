package org.limbusnoe.jpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "page_completion")
public class PageCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_completion_id", updatable = false, nullable = false)
    private Long id;

    private Long studentId;

    private UUID pageId;

    private Timestamp completionTime;

}
