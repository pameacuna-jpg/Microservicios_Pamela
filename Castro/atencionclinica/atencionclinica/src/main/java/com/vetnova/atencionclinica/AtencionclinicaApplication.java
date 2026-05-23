package com.vetnova.atencionclinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; 

@SpringBootApplication
@EnableFeignClients 
public class AtencionclinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtencionclinicaApplication.class, args);
    }

}