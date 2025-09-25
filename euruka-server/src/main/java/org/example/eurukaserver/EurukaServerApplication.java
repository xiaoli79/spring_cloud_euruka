package org.example.eurukaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//加上这个注解用来开启eureka的功能~~
@EnableEurekaServer
@SpringBootApplication
public class EurukaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurukaServerApplication.class, args);
    }

}
