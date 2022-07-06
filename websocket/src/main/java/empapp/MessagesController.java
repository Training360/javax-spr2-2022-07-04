package empapp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@AllArgsConstructor
public class MessagesController {

    private SimpMessagingTemplate template;

    @MessageMapping("/messages")
    @SendTo("/topic/employees")
    public MessageResponse sendMessage(MessageCommand command) {
        log.info("Command has arrived: " + command);
        return new MessageResponse("Reply: " + command.getContent());
    }

    @EventListener
    public void sendEmployeeHasBeenCreated(EmployeeCreatedEvent event) {
        template.convertAndSend("/topic/employees",
                new MessageResponse(String.format("Employee has been created: (%d) %s", event.getEmployeeDto().getId(), event.getEmployeeDto().getName())));
    }
}
