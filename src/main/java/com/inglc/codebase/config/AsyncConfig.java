package com.inglc.codebase.config;

import java.util.concurrent.Executor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author L.C
 * @date 2020/5/7
 */

@Data
@EnableAsync
@Configuration
@ConfigurationProperties(prefix = "async.thread")
public class AsyncConfig implements AsyncConfigurer {


	private int corePoolSize;
	private int maxPoolSize;
	private int queueCapacity;

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(getCorePoolSize());
		executor.setMaxPoolSize(getMaxPoolSize());
		executor.setQueueCapacity(getQueueCapacity());
		executor.setThreadNamePrefix("SolrIndexing-");
		executor.initialize();
		return executor;
	}
}
