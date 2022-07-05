package empapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class MessagesController {

    @MessageMapping("/messages")
    @SendTo("/topic/employees")
    public MessageResponse sendMessage(MessageCommand command) {
        log.info("Command has arrived: " + command);
        return new MessageResponse("Reply: " + command.getContent());
    }
}
