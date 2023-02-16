package com.project.sioscms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SioscmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SioscmsApplication.class, args);
    }

}
