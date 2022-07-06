package empapp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@Slf4j
@AllArgsConstructor
public class EventsController {

    private EventsService eventsService;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/api/events")
    public SseEmitter events() {
        SseEmitter sseEmitter = new SseEmitter();

//        eventsService.publishEvents(sseEmitter);
        emitters.add(sseEmitter);

        return sseEmitter;
    }

    @EventListener
    public void handleEvent(EmployeeCreatedEvent event) {
        List<SseEmitter> badEmitters = new ArrayList<>();

        for (SseEmitter emitter: emitters) {

            SseEmitter.SseEventBuilder builder = SseEmitter.event()
                    .name("event")
                    .comment("Sample hello event")
                    .id(UUID.randomUUID().toString())
                    .reconnectTime(10_000)
                    // JSON marshal
                    .data(new EventMessage(event.getEmployeeDto().toString()));

            try {
                emitter.send(builder);
            }
            catch (Exception e) {
                badEmitters.add(emitter);
            }
        }
        emitters.removeAll(badEmitters);
    }
}
