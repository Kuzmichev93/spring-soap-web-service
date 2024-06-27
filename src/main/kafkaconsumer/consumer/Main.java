package consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("src/main/kafkaconsumer/consumer") //to scan packages mentioned
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    };}
