package empapp;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.util.List;

import static org.ehcache.config.units.MemoryUnit.MB;

@Configuration
public class CacheConfig  {

    @Bean
    public org.springframework.cache.CacheManager cacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder()
                .heap(10, MB)
                .offheap(20, MemoryUnit.MB);

        CacheEventListenerConfigurationBuilder asynchronousListener = CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(new CacheEventLogger()
                        , EventType.CREATED, EventType.EXPIRED, EventType.EVICTED, EventType.REMOVED, EventType.UPDATED).unordered().asynchronous();


        CacheConfigurationBuilder<SimpleKey, List> config1 = CacheConfigurationBuilder.newCacheConfigurationBuilder(SimpleKey.class, List.class,
                resourcePoolsBuilder).withService(asynchronousListener);

        cacheManager.createCache("employees", Eh107Configuration.fromEhcacheCacheConfiguration(config1));

        CacheConfigurationBuilder<Long, EmployeeDto> config2 = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, EmployeeDto.class,
                resourcePoolsBuilder).withService(asynchronousListener);

        cacheManager.createCache("employee", Eh107Configuration.fromEhcacheCacheConfiguration(config2));

        return new JCacheCacheManager(cacheManager);
    }

}
