package org.limbusnoe;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/send-message")
    public String sendMessage(@RequestBody String message) {
        kafkaTemplate.send("another-test-topic", message);
        return "Message sent to another-test-service: " + message;
    }
}
