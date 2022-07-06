package empapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Gateway
@Slf4j
public class EmployeesMessageListener {

    @JmsListener(destination = "employeesQueue")
//    public void processMessage(Message message) {
//        if (message instanceof TextMessage) {
//            try {
//                log.info(((TextMessage) message).getText());
//            }
//            catch (JMSException ex) {
//                throw new RuntimeException("Error reading message", ex);
//            }
//        }
//        else {
//            throw new IllegalArgumentException("Message must be of type TextMessage");
//        }
//    }
    public void processMessage(MessageDto messageDto) {
        log.info(messageDto.getText());
    }
}
