package empapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@Service
@Slf4j
public class EventsService {

    @Async
    public void publishEvents(SseEmitter sseEmitter) {
        try {
            for (int i = 0; i < 10; i++) {
                log.info("Sending " + i);

                SseEmitter.SseEventBuilder builder = SseEmitter.event()
                        .name("event")
                        .comment("Sample hello event")
                        .id(UUID.randomUUID().toString())
                        .reconnectTime(10_000)
                        // JSON marshal
                        .data(new EventMessage("hello " + i));

                sseEmitter.send(builder);
                Thread.sleep(1000);
            }
        }
        catch (Exception e) {
            log.error("Error emitting", e);
        }
    }
}
