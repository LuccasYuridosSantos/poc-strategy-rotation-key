package com.luccas.schedulerpii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SchedulerPiiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerPiiApplication.class, args);
    }

}
