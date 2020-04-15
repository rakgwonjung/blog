package com.rock.boottutorial;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

// 메시지 저장을 담당하는 저장소
// @Component 은 제네릭 스테레오 타입이다 클래스에 이 어노테이션이 있으면 해당 클래스를 인스턴스화 한다
@Component
public class MessageRepository {

    private final static Log log = LogFactory.getLog(MessageRepository.class);

    public void saveMessage(Message message) {
        // 데이터베이스에 메세지를 저장한다
        log.info("Saved message : " + message.getText());
    }
}
