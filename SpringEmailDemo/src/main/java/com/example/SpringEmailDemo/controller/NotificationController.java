package com.example.SpringEmailDemo.controller;

import com.example.SpringEmailDemo.dto.MailModel;
import com.example.SpringEmailDemo.publisher.RabbitMQJsonProducer;
import com.example.SpringEmailDemo.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NotificationController {

    @Autowired
    private RabbitMQJsonProducer jsonProducer;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody MailModel mailModel){
        jsonProducer.sendJsonMessage(mailModel);
        return ResponseEntity.ok("Thành công");
    }
}
