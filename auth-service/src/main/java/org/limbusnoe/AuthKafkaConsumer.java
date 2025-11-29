package org.limbusnoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuthKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(AuthKafkaConsumer.class);
}