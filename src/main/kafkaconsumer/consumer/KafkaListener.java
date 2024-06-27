package consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;


public class KafkaListener {

    public KafkaConsumer<String,String> kafkaConsumer;
    public KafkaListener(KafkaConsumer<String,String> kafkaConsumer){
        this.kafkaConsumer=kafkaConsumer;
    }


    public void setTopic(String topic,int partiton,int offset){
        TopicPartition part = new TopicPartition(topic,partiton);
        kafkaConsumer.assign(Collections.singleton(part));
        kafkaConsumer.seek(part,offset);
        listen();
    }


    public void listen(){
        Duration duration= Duration.ofMillis(100);//new Duration(x, TimeUnit.MILLISECONDS)
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(duration);
            for (ConsumerRecord<String, String> record : records){

                // print the offset,key and value for the consumer records.
                System.out.printf("partition = %d,offset = %d, key = %s, value = %s\n",
                        record.partition(),record.offset(), record.key(), record.value());

        }
    }
}}
