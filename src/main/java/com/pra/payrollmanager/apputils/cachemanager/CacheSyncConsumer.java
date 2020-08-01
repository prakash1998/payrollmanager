package com.pra.payrollmanager.apputils.cachemanager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.constants.KafkaTopics;

@Service
public class CacheSyncConsumer {

	@Autowired
	CacheManager cacheManager;

	@KafkaListener(topics = KafkaTopics.CACHE_SYNC, autoStartup = "${spring.kafka.enabled}")
	public void syncCache(CacheSyncMsg message) {

		message.getEl().forEach(eviction -> {
			Cache cache = cacheManager.getCache(eviction.getStore());
			Collection<String> keys = eviction.getKeys();

			// clear cache if empty list is passed
			if (keys.isEmpty()) {
				cache.clear();
			} else {
				keys.forEach(key -> {
					cache.evictIfPresent(key);
				});
			}
		});

	}

}
