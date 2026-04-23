package com.components;

import com.components.observer.ComponentListLogger;
import com.components.service.ComponentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.components")
public class ComponentesRESTServerApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ComponentesRESTServerApplication.class, args);
        ComponentService service = ctx.getBean(ComponentService.class);
        service.addObserver(new ComponentListLogger());
    }
}
