package com.inglc.codebase.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "feignDemo", url = "localhost:9000", path = "/feign")
public interface FeignDemoClient {

	@GetMapping(value = "/hello/{name}")
	String hello(@PathVariable(value = "name") String name);
}
