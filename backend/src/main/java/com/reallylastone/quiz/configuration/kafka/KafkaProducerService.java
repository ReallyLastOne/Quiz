package com.reallylastone.quiz.configuration.kafka;

import com.reallylastone.quiz.auth.model.UserInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, UserInformation> kafkaTemplate;

    public void send(String topicName, String key, UserInformation value) {
        var future = kafkaTemplate.send(topicName, key, value);

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }
            log.info("User send to Kafka topic: %s".formatted(value));
        });
    }
}
