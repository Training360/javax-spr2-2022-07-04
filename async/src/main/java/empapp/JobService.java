package empapp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class JobService {

    private JobRepository jobRepository;
    public CreateJobResponse createJob() {
        Job job = new Job();
        jobRepository.save(job);

        getStatus();

        return new CreateJobResponse(job.getId());
    }

    @Async
    public void getStatus() {
        log.info("Get status");
        // Háttérrendszer meghívása - URL lekérése
    }

    public JobStatus getJob(long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No job found"));
        return new JobStatus(job.getId(), job.getResult());
    }
}
