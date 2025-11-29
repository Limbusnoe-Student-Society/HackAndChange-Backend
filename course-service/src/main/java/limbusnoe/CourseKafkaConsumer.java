package limbusnoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CourseKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CourseKafkaConsumer.class);

    @KafkaListener(topics = "course-topic", groupId = "course-group")
    public void listen(String message) {
        logger.info("Received message in Course Service: {}", message);
    }
}