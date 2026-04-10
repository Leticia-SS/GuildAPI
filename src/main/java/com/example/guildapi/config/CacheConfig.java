package com.example.guildapi.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager("topMissoes");
    }

    @Scheduled(fixedRate = 300000)
    @CacheEvict(value = "topMissoes", allEntries = true)
    public void limparCache() {
        System.out.println("Cache 'topMissoes' limpo");
    }
}
