package empapp;

import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.Session;

@Gateway
@AllArgsConstructor
public class EmployeesMessageGateway {

    private JmsTemplate jmsTemplate;

    public void sendMessage(String text) {
//        jmsTemplate.send("employeesQueue",
//                session -> session.createTextMessage(text));

        //jmsTemplate.convertAndSend(text);

        jmsTemplate.convertAndSend("employeesQueue", new MessageDto(text));
    }
}
