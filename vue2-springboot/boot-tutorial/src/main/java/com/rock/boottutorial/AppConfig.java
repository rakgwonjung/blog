package com.rock.boottutorial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

// 스프링이 컨테이너를 인스턴스화하는데 사용할 설정 메타데이터
// @Configuration 이 파일이 빈을 정의하기 위한 것임을 스프링에 알려주기 위함
@Configuration
// @ComponentScan 어노테이션이 달린 컴포넌트를 스캔할 기본 패키지를 스프링에 알려주기 위해 @Configuration과 함께 사용
@ComponentScan("com.rock.boottutorial")
public class AppConfig {

    // LocalSessionFactoryBean 를 생성하려면 javax.sql.DataSource 인스턴스가 필요하다.
    // 따라서 스프링에게 설정 클래스로 주입해 줄 것을 요청한다
    private DataSource dataSource;

    public AppConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public FilterRegistrationBean<AuditingFilter> auditingFilterRegistrationBean() {
        FilterRegistrationBean<AuditingFilter> registration = new FilterRegistrationBean<>();
        AuditingFilter filter = new AuditingFilter();
        registration.setFilter(filter);
        registration.setOrder(Integer.MAX_VALUE);
        registration.setUrlPatterns(Arrays.asList("/messages/*"));
        return registration;
    }

    // ** 하이버 네이트 사용 **
    // 스프링 ORM으로 SessionFactory의 인스턴스를 생성하려면 스프링 FactoryBean인
    // 스프링의 LocalSessionFactoryBean 을 사용하면 된다.
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // setPackagesToScan 메서드로 하이버네이트가 엔티티 클래스를 찾기 위해 검색할 패지키를 지정해야한다.
        sessionFactoryBean.setPackagesToScan("com.rock.boottutorial");
        return sessionFactoryBean;
    }


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
