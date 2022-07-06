package empapp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/api/check")
@AllArgsConstructor
@Slf4j
public class CheckController {

    private ClientService clientService;

    @GetMapping
    public DeferredResult<String> check() {
        log.info("Start");
        DeferredResult<String> result = new DeferredResult<>();
        clientService.getStatus(result);
        log.info("End");
        return result;
    }
}
