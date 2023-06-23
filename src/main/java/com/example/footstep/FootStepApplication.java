package com.example.footstep;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FootStepApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootStepApplication.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
