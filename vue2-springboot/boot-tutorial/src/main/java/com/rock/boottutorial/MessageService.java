package com.rock.boottutorial;

import org.springframework.stereotype.Component;

// 클라이언트에 API를 제공하는 서비스
// @Component 은 제네릭 스테레오 타입이다 클래스에 이 어노테이션이 있으면 해당 클래스를 인스턴스화 한다
@Component
public class MessageService {

    private MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    @SecurityCheck
    public Message save(String text) {
        return repository.saveMessage(new Message(text));
    }
}
