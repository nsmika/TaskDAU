package ru.kamagames.interview.dau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DauApplication {
    public static void main(String[] args) {
        SpringApplication.run(DauApplication.class, args);
    }
}
