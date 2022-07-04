package empapp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Service
@Slf4j
@AllArgsConstructor
public class ClientService {

    private ApplicationEventPublisher publisher;

    @Async
    public void getStatus(long id) {
        log.info("Get status");
        // HTTP kérés a háttérrendszer felé
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException ie) {
            log.error("interrupted", ie);
        }

        // OpenFeign
        // Spring WebClient - reaktív
        // Spring Project Reactor
        HttpClient client = HttpClient.create().responseTimeout(Duration.ofSeconds(5));

        WebClient.builder().clientConnector(new ReactorClientHttpConnector(client)).build()
                .get().uri("https://training360.com")
                .exchangeToMono(clientResponse -> Mono.just(clientResponse.statusCode().toString()))
                .subscribe(status -> publisher.publishEvent(new CallCompletedEvent(id, status)));

//        String status = "200";
//        publisher.publishEvent(new CallCompletedEvent(id, status));
//
//        log.info("Get status end");
    }
}
