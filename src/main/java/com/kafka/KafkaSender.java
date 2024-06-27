package com.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class KafkaSender {
    private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);
    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(String topicName,int partition,User user,String key) {
        try {
            logger.info("Отправка сообщения в топик");
            kafkaTemplate.send(topicName, partition, key, user);
        }
        catch (KafkaException e){
            logger.error("Error method sendMessage",e);
        }

    }
}
