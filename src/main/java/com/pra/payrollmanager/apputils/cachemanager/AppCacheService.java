package com.pra.payrollmanager.apputils.cachemanager;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.constants.KafkaTopics;
import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.utils.ObjectUtils;

@Service
public class AppCacheService {

	@Value("${spring.kafka.enabled}")
	private boolean kafkaEnabled;

	@Autowired
	KafkaTemplate<String, CacheSyncMsg> cacheSyncTopic;

	@Autowired
	CacheManager cacheManager;

	@Autowired
	AuthorityService authService;

	public void removeByKey(String cacheStore, String key) {
		Cache cache = cacheManager.getCache(cacheStore);
		cache.evictIfPresent(key);
		if (kafkaEnabled) {
			cacheSyncTopic.send(KafkaTopics.CACHE_SYNC, CacheSyncMsg.of(CacheEvictionMsg.of(cacheStore, key)));
		}
	}

	public void removeByKeys(String cacheStore, Collection<String> keys) {
		Cache cache = cacheManager.getCache(cacheStore);
		keys.forEach(key -> {
			cache.evictIfPresent(key);
		});
		if (kafkaEnabled) {
			cacheSyncTopic.send(KafkaTopics.CACHE_SYNC, CacheSyncMsg.of(CacheEvictionMsg.of(cacheStore, keys)));
		}
	}

	public void clearCaches(String... cacheStores) {
		Stream.of(cacheStores).forEach(store -> {
			cacheManager.getCache(store).clear();
		});
		if (kafkaEnabled) {
			cacheSyncTopic.send(KafkaTopics.CACHE_SYNC,
					CacheSyncMsg.of(Stream.of(cacheStores).map(store -> CacheEvictionMsg.of(store))
							.collect(Collectors.toList())));
		}
	}

	public <T> T cached(String cacheStore, String key, Function<Object, T> functionToCache) {
		Cache cache = cacheManager.getCache(cacheStore);
		ValueWrapper value = cache.get(key);
		if (value != null)
			return ObjectUtils.unSafeCast(value.get());
		T result = functionToCache.apply(key);
		cache.put(key, result);
		return result;
	}

	public <T> T cacheWithUserId(String cacheStore, Function<Object, T> functionToCache) {
		return this.cached(cacheStore, authService.getUserId(), functionToCache);
	}

	private String keyWithCompany(String key) {
		return String.format("%s-%s", authService.getSecurityCompany().getId(), key);
	}

	public <T> T cacheWithCompany(String cacheStore, String key, Function<Object, T> functionToCache) {
		return this.cached(cacheStore, this.keyWithCompany(key), functionToCache);
	}

	public void removeByUserId(String cacheStore) {
		this.removeByKey(cacheStore, authService.getUserId());
	}

	public void removeByKeyWithCompany(String cacheStore, String key) {
		this.removeByKey(cacheStore, this.keyWithCompany(key));
	}

	public void removeByKeysWithCompany(String cacheStore, Collection<String> keys) {
		this.removeByKeys(cacheStore, keys.stream()
				.map(key -> this.keyWithCompany(key))
				.collect(Collectors.toList()));
	}

}
