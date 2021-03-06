package com.rock.boottutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 어플리케이션 시작 점 main 메소드
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        MessageService messageService = context.getBean(MessageService.class);
//        messageService.save("Hello, Spring");
        SpringApplication.run(Application.class, args);
    }
}
