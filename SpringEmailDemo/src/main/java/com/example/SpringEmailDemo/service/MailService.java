package com.example.SpringEmailDemo.service;

import com.example.SpringEmailDemo.dto.MailModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class MailService {

    @Autowired
    JavaMailSender mailSender;
    List<MimeMessage> queue = new ArrayList<>();

    public void pushEmail(String to,String subject,String body) throws MessagingException {
        MailModel mailModel = new MailModel(to,subject,body);
        this.push(mailModel);
    }

    public void push(MailModel mail) throws MessagingException {//Tạo MimeMessage và bỏ vào queue
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
//        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubJect());
        helper.setText(mail.getBody(),true);//true được sử dụng để xác định liệu nội dung của chuỗi được định dạng hay không.
//        helper.setReplyTo(mail.getFrom());
        for (String email : mail.getCc()) {
            helper.addCc(email);
        }

        for (String email: mail.getBcc()) {
            helper.addBcc(email);
        }

        for (File file: mail.getFile()) {
            helper.addAttachment(file.getName(), file);
        }
        queue.add(message);
        run();
    }

//    @Scheduled(fixedDelay = 1000)
    public void run(){
        int success = 0,error = 0;
        while (!queue.isEmpty()){
            MimeMessage message = queue.remove(0);
            //vòng lặp while được sử dụng để lặp lại việc gửi email cho đến khi hàng đợi email rỗng. Mỗi lần lặp lại,
            // email đầu tiên trong hàng đợi được loại bỏ bằng phương thức remove() và lưu trữ trong biến
            // message kiểu MimeMessage.
            try {
                mailSender.send(message);
                success++;
            } catch (Exception e){
                System.out.println(e);
                error++;
            }
        }
        System.out.printf("Sent: %d, Error: %d\r\n", success,error);
    }
}
