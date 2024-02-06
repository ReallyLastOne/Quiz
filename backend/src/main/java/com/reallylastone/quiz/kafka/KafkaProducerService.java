package com.reallylastone.quiz.kafka;

import com.reallylastone.quiz.auth.model.UserInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, UserInformation> kafkaTemplate;

    @Async
    public void send(UserInformation user) {
        log.info("Sending user to Kafka topic: %s...".formatted(user));
        final String topicName = "user-topic";
        final String id = user.id().toString();
        var future = kafkaTemplate.send(topicName, id, user);

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }
        });
    }
}
