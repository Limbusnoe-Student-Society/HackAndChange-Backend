package org.limbusnoe.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CourseDetails {
    private String title;
    private String description;
}