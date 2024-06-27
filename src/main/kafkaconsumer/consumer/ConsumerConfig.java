package consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ConsumerConfig {

    @Bean
    public KafkaConsumer<String,String> kafkaConsumer(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("bootstrap.servers","http//localhost:9092");
        hashMap.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        hashMap.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<>(hashMap);

    }

    @Bean
    public KafkaListener kafkaListener(){
        KafkaListener settingTopic = new KafkaListener(kafkaConsumer());
        settingTopic.setTopic("mytopic",1,5);
        return settingTopic;
    }
}
