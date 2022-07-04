package empapp;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@AllArgsConstructor
public class CheckController
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

}
