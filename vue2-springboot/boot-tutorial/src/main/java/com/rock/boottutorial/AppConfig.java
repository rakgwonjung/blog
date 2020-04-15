package com.rock.boottutorial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// 스프링이 컨테이너를 인스턴스화하는데 사용할 설정 메타데이터
// @Configuration 이 파일이 빈을 정의하기 위한 것임을 스프링에 알려주기 위함
@Configuration
// @ComponentScan 어노테이션이 달린 컴포넌트를 스캔할 기본 패키지를 스프링에 알려주기 위해 @Configuration과 함께 사용
@ComponentScan("com.rock.boottutorial")
public class AppConfig {

    // Bean을 생성, 메소드 명이 빈의 명이 된다.
//    @Bean
//    public MessageRepository messageRepository() {
//        return new MessageRepository();
//    }
//
//    @Bean
//    MessageService messageService() {
//        return new MessageService(messageRepository());
//    }

}
