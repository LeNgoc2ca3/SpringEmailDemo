package com.example.SpringEmailDemo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")//đọc giá trị từ file properties
    private String queue;

    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

//    spring bean for rabbitmq queue
    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

//    spring bean for queue (store json messages)
    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueue);
    }

//    spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange() {//trao đổi
        return new TopicExchange(exchange);
    }



    //bingding between queue and exchange
    @Bean
    public Binding binding() {//liên kết 2 phương  thức trên bằng cách sử dung routing key
        return BindingBuilder.bind(queue()).to(exchange())
                .with(routingKey);
    }

    //bingding between json queue and exchange
    @Bean
    public Binding jsonBinding() {//liên kết 2 phương  thức trên bằng cách sử dung routing key
        return BindingBuilder.bind(jsonQueue()).to(exchange())
                .with(routingJsonKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
//         chuyển đổi các đối tượng Java sang định dạng JSON và ngược lại
//        Bằng cách chú thích phương thức này với @Bean, Spring sẽ đảm bảo rằng chỉ một phiên bản của
//        Jackson2JsonMessageConverterđược tạo và nó được quản lý bởi bộ chứa Spring,
    }

//    create a rabbitmq template
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

//    ConnectionFactory
//    RabbitTemplate
//    RabbitAdmin
//    Spring boot sẽ tự động cấu hình 3 cái trên
}
