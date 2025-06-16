package com.application.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
public class NotificationService {

    @KafkaListener(id = "1", topics = "userService")
    public void userServiceListener(ConsumerRecord<String, String> record) {

        if (record.key().equals("create")) {
            sendMessageToEmail(record.value(), "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
        }
        if (record.key().equals("delete")) {
            sendMessageToEmail(record.value(), "Здравствуйте! Ваш аккаунт был удалён.");
        }
    }

    private void sendMessageToEmail(String email, String message) {

        System.out.println("Email: " + email +"\nMessage: " + message);
    }
}
