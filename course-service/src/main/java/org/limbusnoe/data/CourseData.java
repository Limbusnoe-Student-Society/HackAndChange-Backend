package org.limbusnoe.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CourseData {
    private String title;
    private String description;
    private List<CourseModuleData> modules;
    @Data
    @AllArgsConstructor
    public static class CourseModuleData {
        private String title;
        private int order;
        private List<CourseLessonData> lessons;
    }
    @Data
    @AllArgsConstructor
    public static class CourseLessonData {
        private String title;
        private int order;
        private List<CoursePageData> pages;
    }
    @Data
    @AllArgsConstructor
    public static class CoursePageData {
        private String pageType;
        private String title;
        private String content;
        private String videoUrl;
        private int order;
    }
}
