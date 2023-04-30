package com.example.SpringEmailDemo.consumer;

import com.example.SpringEmailDemo.dto.MailModel;
import com.example.SpringEmailDemo.service.MailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    @Autowired
    MailService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})//lắng nghe tín hiệu, dùng để đọc message từ queue
    public void consumeJsonMessage(MailModel mailModel) throws MessagingException {
        service.pushEmail(mailModel.getTo(), mailModel.getSubJect(), mailModel.getBody());

        LOGGER.info(String.format("Received message -> %s", mailModel.toString()));//Ghi lại message từ queue
    }
}
