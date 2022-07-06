package emp;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

import java.util.Scanner;

import static java.util.concurrent.TimeUnit.SECONDS;

public class EmployeesMain {

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/api/events");
        try (SseEventSource source = SseEventSource.target(target)
                .reconnectingEvery(30, SECONDS)
                .build()) {
            source.register(
                    inboundSseEvent -> System.out.println(inboundSseEvent.readData(MessageDto.class)),
                    t -> {t.printStackTrace(); throw new IllegalStateException("Error processing sse", t);},
                    () -> System.out.println("Closing..."));
            source.open();
            // Várakozni!!!

            new Scanner(System.in).nextLine();

        }
        client.close();
    }
}
