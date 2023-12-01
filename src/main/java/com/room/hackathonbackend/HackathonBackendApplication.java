package com.room.hackathonbackend;

import com.directai.directaiexceptionhandler.config.EnableDirectExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDirectExceptionHandler
@ComponentScan({"com.room.hackathonbackend", "com.directai.directaiexceptionhandler", "com.befree.b3authauthorizationserver"})
public class HackathonBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackathonBackendApplication.class, args);
    }

}
