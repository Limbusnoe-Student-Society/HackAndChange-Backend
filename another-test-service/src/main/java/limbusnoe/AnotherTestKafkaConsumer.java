package limbusnoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AnotherTestKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AnotherTestKafkaConsumer.class);

    @KafkaListener(topics = "another-test-topic", groupId = "another-test-group")
    public void listen(String message) {
        logger.info("Received message in Another Test Service: {}", message);
    }
}