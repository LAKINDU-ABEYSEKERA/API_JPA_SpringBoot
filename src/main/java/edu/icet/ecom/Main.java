package edu.icet.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan("edu.icet.ecom.model")

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);

    }
}