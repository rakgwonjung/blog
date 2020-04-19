package com.rock.boottutorial.service;

import com.rock.boottutorial.model.Message;
import com.rock.boottutorial.repository.MessageRepository;
import com.rock.boottutorial.security.SecurityCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 클라이언트에 API를 제공하는 서비스
// @Component 은 제네릭 스테레오 타입이다 클래스에 이 어노테이션이 있으면 해당 클래스를 인스턴스화 한다
@Component
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    private MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    // 요청은 읽기모드만을 하면 되기 떄문에 옵션 추가
    @Transactional(readOnly = true)
    public List<Message> getMessages() {
        return repository.getMessages();
    }

    // SecurityCheck 어노테이션 적용 (SecurityChecker AOP)
    @SecurityCheck
    // 트랜잭션 어노테이션 적용
    // noRollbackFor = { UnsupportedOperationException.class } 트랜잭션 롤백되지 않게 설정
    @Transactional(noRollbackFor = { UnsupportedOperationException.class })
    public Message save(String text) {
        Message message = repository.saveMessage(new Message(text));
        log.debug("New message[id={}] saved", message.getId());
        // 강제 Exception
//        updateStatistics();
        return message;
    }

    // 트랜잭션 롤백을 발생
    private void updateStatistics() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}
