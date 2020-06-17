package com.pra.payrollmanager.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

//	@Bean(destroyMethod = "shutdown")
//	RedissonClient redisson() throws IOException {
//		Config config = new Config();
//		config.useSingleServer()
//				.setAddress(null);
//		return Redisson.create(config);
//	}
//
//	@Bean
//    CacheManager cacheManager(RedissonClient redissonClient) {
//        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
//        // define local cache settings for "testMap" cache.
//        // ttl = 48 minutes and maxIdleTime = 24 minutes for local cache entries
//        LocalCachedMapOptions options = LocalCachedMapOptions.defaults()
//            .evictionPolicy(EvictionPolicy.LFU)
//            .timeToLive(48, TimeUnit.MINUTES)
//            .maxIdle(24, TimeUnit.MINUTES);
//            .cacheSize(1000);
//            
//        // create "testMap" Redis cache with ttl = 24 minutes and maxIdleTime = 12 minutes
//        config.put("testMap", new LocalCachedCacheConfig(24*60*1000, 12*60*1000, options));
//        return new RedissonSpringLocalCachedCacheManager(redissonClient, config);
//    }

}
