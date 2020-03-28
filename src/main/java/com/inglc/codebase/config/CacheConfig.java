package com.inglc.codebase.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

/**
 * @author inglc
 * @date 2020/3/11
 */

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfig {


	@Autowired
	CacheProperties cacheProperties;

	@Bean
	@Primary
	public CacheManager caffeineCacheManager() {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		String specification = cacheProperties.getCaffeine().getSpec();
		if (StringUtils.hasText(specification)) {
			caffeineCacheManager.setCacheSpecification(specification);
		}
		caffeineCacheManager.setCaffeine(Caffeine.newBuilder().recordStats());
		return caffeineCacheManager;
	}


	@Bean
	public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

		redisCacheConfiguration.disableCachingNullValues()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));

		Redis redis = cacheProperties.getRedis();
		if (Objects.nonNull(redis.getTimeToLive())){
			redisCacheConfiguration = redisCacheConfiguration.entryTtl(redis.getTimeToLive());
		}
		if (!redis.isCacheNullValues()) {
			redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
		}
		if (!redis.isUseKeyPrefix()) {
			redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();
		}

		return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory)
				.cacheDefaults(redisCacheConfiguration).build();

	}

}
