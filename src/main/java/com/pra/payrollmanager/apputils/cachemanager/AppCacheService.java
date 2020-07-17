package com.pra.payrollmanager.apputils.cachemanager;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.pra.payrollmanager.security.authorization.AuthorityService;
import com.pra.payrollmanager.utils.ObjectUtils;

@Service
public class AppCacheService {

	@Autowired
	CacheManager cacheManager;

	@Autowired
	AuthorityService authService;

	private String keyWithCompany(String key) {
		return String.format("%s-%s", authService.getSecurityCompany().getId(), key);
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

	public <T> T cacheWithCompany(String cacheStore, String key, Function<Object, T> functionToCache) {
		return this.cached(cacheStore, this.keyWithCompany(key), functionToCache);
	}

	public void removeByKey(String cacheStore, String key) {
		Cache cache = cacheManager.getCache(cacheStore);
		cache.evictIfPresent(key);
	}

	public void removeByKeys(String cacheStore, Collection<String> keys) {
		Cache cache = cacheManager.getCache(cacheStore);
		keys.forEach(key -> {
			cache.evictIfPresent(key);
		});
	}

	public void removeByUserId(String cacheStore) {
		Cache cache = cacheManager.getCache(cacheStore);
		cache.evictIfPresent(authService.getUserId());
	}

	public void removeByKeyWithCompany(String cacheStore, String key) {
		Cache cache = cacheManager.getCache(cacheStore);
		cache.evictIfPresent(this.keyWithCompany(key));
	}

	public void removeByKeysWithCompany(String cacheStore, Collection<String> keys) {
		Cache cache = cacheManager.getCache(cacheStore);
		keys.forEach(key -> {
			cache.evictIfPresent(this.keyWithCompany(key));
		});
	}

	public void clearCaches(String... cacheStores) {
		Stream.of(cacheStores).forEach(store -> {
			cacheManager.getCache(store).clear();
		});
	}

}
