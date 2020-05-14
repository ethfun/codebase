package com.inglc.codebase.config;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

/**
 * @author L.C
 * @date 2020/5/7
 */
@Slf4j
@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {


	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		log.error("Unexpected exception occurred invoking async method: " + method, ex, params);

		log.error("insert DB {}, {}", params);
		// @todo rabbitmq入库补偿机制
	}
}
