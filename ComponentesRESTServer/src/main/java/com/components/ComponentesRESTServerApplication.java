package com.components;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.components")
public class ComponentesRESTServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComponentesRESTServerApplication.class, args);
    }
}
