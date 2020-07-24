package com.inglc.codebase.controller;

import com.inglc.codebase.infrastructure.feign.FeignDemoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author inglc
 * @date 2020/3/25
 */

@Slf4j
@RestController
@RequestMapping("/feign")
public class FeignController {


	@Autowired
	private FeignDemoClient feignDemo;


	@GetMapping("/demo/{name}")
	public ResponseEntity getAll(@PathVariable("name") String name) {
		return ResponseEntity.ok(feignDemo.hello(name));
	}

	@GetMapping("/hello/{name}")
	public ResponseEntity hello(@PathVariable("name") String name) {
		log.error("name is {}", name);
		return ResponseEntity.ok(name);
	}

}
