package empapp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@AllArgsConstructor
@Slf4j
public class JobController
{
    private JobService jobService;

    @PostMapping
    public CreateJobResponse createJob() {
        return jobService.createJob();
    }

    @GetMapping("{id}")
    public JobStatus getJob(@PathVariable("id") long id) {
        return jobService.getJob(id);
    }

    @PostMapping("/hello")
    public void hello() {
        log.info(jobService.getClass().getName());
        jobService.hello();
    }

}
