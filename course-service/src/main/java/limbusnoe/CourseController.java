package limbusnoe;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/send-message")
    public String sendMessage(@RequestBody String message) {
        kafkaTemplate.send("auth-topic", message);
        return "Message sent to auth service: " + message;
    }
}
