package com.mariesto.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1/rabbitmq")
public class Publisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value ("${rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    @Value("${rabbitmq.routing-key}")
    private String ROUTING_KEY;

    @PostMapping ("/send")
    public void sendMessage(@RequestBody Message message) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
        System.out.println(" [x] Message sent : " + message.toString());
    }
}
