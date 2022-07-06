package empapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class PoolConfig {

    @Bean
    public TaskExecutor checkTaskExecutor() {
        ThreadPoolTaskExecutor executor =  new ThreadPoolTaskExecutor();
        return executor;
    }
}
