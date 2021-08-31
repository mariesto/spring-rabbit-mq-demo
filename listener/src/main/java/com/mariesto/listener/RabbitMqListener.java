package com.mariesto.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RabbitMqListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        log.info(" [x] Message Received : " + new String(message.getBody()));
    }
}
