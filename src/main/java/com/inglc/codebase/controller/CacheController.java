package com.inglc.codebase.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.inglc.codebase.service.CacheService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author inglc
 * @date 2020/3/11
 */

@Slf4j
@RestController
@RequestMapping("/cache")
public class CacheController {


	@Autowired
	CacheService cacheService;
	@Autowired
	CacheManager caffeineCacheManager;

	@GetMapping("/get/{infoId}")
	public ResponseEntity getCache(@PathVariable("infoId") Long infoId){

		return ResponseEntity.ok(cacheService.getGoGoGo(infoId));
	}

	@GetMapping("/get/caf/statistic")
	public Object getCafCache(){
		Map<String,Object> map = new HashMap<>();
		List<CacheStats> list = new ArrayList<>();
		for (String cacheName : caffeineCacheManager.getCacheNames()){
			CaffeineCache caffeineCache = (CaffeineCache)caffeineCacheManager.getCache(cacheName);
			Cache<Object, Object> allCache = caffeineCache.getNativeCache();
			map.put(cacheName,  caffeineCache.getNativeCache().stats().hitRate());
//			map.put(cacheName,  caffeineCache.getNativeCache().stats());
			CacheStats cashStats = caffeineCache.getNativeCache().stats();
			list.add(caffeineCache.getNativeCache().stats());
			log.info("cache statistic is {}", caffeineCache.getNativeCache().stats());
		}
		return map;
	}


	@GetMapping("/get/caf/{infoId}")
	public ResponseEntity getCacheCaf(@PathVariable("infoId") Long infoId){

		return ResponseEntity.ok(cacheService.getGoGoGoCaffeine(infoId));
	}

	@GetMapping("/get/info/{infoId}")
	public ResponseEntity getAllInfos(@PathVariable("infoId") Long infoId){

		return ResponseEntity.ok(cacheService.getAllInfos(infoId));
	}


	@GetMapping("/put/info/{infoId}")
	public ResponseEntity putAllInfos(@PathVariable("infoId") Long infoId){

		return ResponseEntity.ok(cacheService.putgogo(infoId));
	}


	@GetMapping("/del/{infoId}")
	public ResponseEntity delCache(@PathVariable("infoId") Long infoId){

		return ResponseEntity.ok(cacheService.del(infoId));
	}


}
