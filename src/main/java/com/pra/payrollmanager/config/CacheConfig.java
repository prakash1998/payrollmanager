package com.pra.payrollmanager.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		cacheManager.setCaffeine(caffeineCacheBuilder());
		cacheManager.getCache("test");
		return cacheManager;
	}

	Caffeine<Object, Object> caffeineCacheBuilder() {
		return Caffeine.newBuilder()
				.initialCapacity(10)
				.maximumSize(100);
//				.expireAfterAccess(10000, TimeUnit.MINUTES)
//				.weakKeys()
//				.recordStats();
	}

}
