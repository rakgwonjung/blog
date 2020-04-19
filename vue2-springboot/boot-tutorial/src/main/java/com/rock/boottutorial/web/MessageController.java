package com.rock.boottutorial.web;
import com.rock.boottutorial.service.MessageService;
import com.rock.boottutorial.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import org.springframework.web.servlet.ModelAndView;

@Controller
// @RestController = @Controller 와 @ResponseBody의 조합이다
//@RestController
//@RequestMapping("/api/messages")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public String index() {
        logger.info("========CONTROLLER GET messages =========");
        System.out.println("CONTROLLER GET messages");
        return "index";
    }

    @GetMapping("/messages/welcome")
    public String welcome(Model model) {
        model.addAttribute("message", "Hello, Welcome to Spring Boot!");
        return "welcome";
    }

    @GetMapping("/api/messages")
    @ResponseBody
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = messageService.getMessages();
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/api/messages")
    @ResponseBody
    public ResponseEntity<Message> saveMessage(@RequestBody MessageData data) {
        Message saved = messageService.save(data.getText());
        if (saved == null) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(saved);
    }
}
