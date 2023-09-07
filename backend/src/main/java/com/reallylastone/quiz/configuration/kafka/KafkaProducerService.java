package com.reallylastone.quiz.configuration.kafka;

import com.reallylastone.quiz.auth.model.UserInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log
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
            log.info("User send to Kafka topic: " + value);
        });
    }
}

