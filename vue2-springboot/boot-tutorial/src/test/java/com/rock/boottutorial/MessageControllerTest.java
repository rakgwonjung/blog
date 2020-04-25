package com.rock.boottutorial;

import com.rock.boottutorial.model.Message;
import com.rock.boottutorial.service.MessageService;
import com.rock.boottutorial.web.MessageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

// @RunWith 는 이 테스트 클래스의 JUnit Runner를 지정할 때 사용되는 JUnit4 어노테이션
@RunWith(SpringRunner.class)
// @WebMvcTest 이 어노테이션의 존재로 스프링은 스프링 MVC의 기반을 자동 설정 (스프링에서 제공)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    // MockMvc 클래스는 서버측 스프링 MVC 테스트 지원을 위한 주요 진입점이다.
    private MockMvc mvc;

    // @MockBean 어노테이션은 MessageService의 목을 생성하는데 활용
    @MockBean
    // MessageService 의 목은 스프링의 애플리케이션 컨텍스트에 추가되고, service 필드에도 주입된다.
    private MessageService service;

    // @Test 테스트 케이스로 실행될 수 있음을 JUnit에게 알려준다.
    @Test
    // 테스트의 명명 규약인 [작업 단위_테스트 중인 상태_예상되는 행동]
    public void getMessage_existingMessages_shouldReturnJsonArray() throws Exception {
        Message firstMessage = new Message("First Message");
        List<Message> allMessages = Arrays.asList(firstMessage);

        // service 목의 세부사항을 설정한다.
        when(service.getMessages()).thenReturn(allMessages);

        mvc.perform(get("/api/messages").contentType(MediaType.APPLICATION_JSON))
                // 상태코드가 200 인지 확인
                .andExpect(status().isOk())
                // JsonPath 를 사용해 결과 JSON에 하나의 항목만 있고
                .andExpect(jsonPath("$", hasSize(1)))
                // text 필드의 값이 First Message 인지 확인한다
                .andExpect(jsonPath("$[0].text", is(firstMessage.getText())));
    }
}
