package com.jung0407.it_book_review_app.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;

@Slf4j
@EnableCaching
@Configuration
@EnableScheduling
public class CacheManagerConfig {

    public static final String CACHE_ID = "books";

    @Scheduled(cron = "0 0 0 * * SUN")
    @CacheEvict(value = {CACHE_ID}, allEntries = true)
    public void expiringCache() {
        log.info("캐시 evict 수행");
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache(CACHE_ID)       // 캐시 등록
        ));

        return cacheManager;
    }
}
