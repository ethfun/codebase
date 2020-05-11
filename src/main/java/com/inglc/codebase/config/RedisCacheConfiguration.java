package com.inglc.codebase.config;

import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author L.C
 * @date 2019/12/28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisCacheConfiguration extends RedisProperties {


//	@Value("${spring.redis.lettuce.pool.max-active}")
	private int maxTotal;

//	@Value("${spring.redis.lettuce.pool.max-idle}")
	private int maxIdle ;

//	@Value("${spring.redis.lettuce.pool.min-idle}")
	private int minIdle ;

	@Bean("redisConnectionFactory")
	@ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(getHost());
		redisStandaloneConfiguration.setPassword(getPassword());
		redisStandaloneConfiguration.setPort(getPort());
		redisStandaloneConfiguration.setDatabase(getDatabase());

		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxTotal(maxTotal);
		genericObjectPoolConfig.setMaxIdle(maxIdle);
		genericObjectPoolConfig.setMinIdle(minIdle);
		LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration
				.builder().poolConfig(genericObjectPoolConfig).build();

		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration,
				lettucePoolingClientConfiguration);
		return connectionFactory;
	}


	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	@Bean(name = "stringRedisTemplate")
	public StringRedisTemplate StringRedisTemplate() {
		return new StringRedisTemplate(redisConnectionFactory());
	}


}
