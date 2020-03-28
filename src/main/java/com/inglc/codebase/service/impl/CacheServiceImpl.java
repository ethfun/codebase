package com.inglc.codebase.service.impl;

import com.inglc.codebase.service.CacheService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @author inglc
 * @date 2020/3/11
 */
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {


	@Cacheable(value= "allInfos", key= "#articleId")
	public List getAllInfos(long articleId) {

		log.error("come here {}", articleId);
		List<String> list = new ArrayList<>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		list.add("eee");
		list.add("fff");

		return list;
	}

	@Cacheable(value= "gogogo", key= "#articleId")
	public List getGoGoGo(long articleId) {

		log.error("come gogogo {}", articleId);
		List<String> list = new ArrayList<>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		list.add("eee");
		list.add("fff");

		return list;
	}

	@Cacheable(value= "gocaffeine", key= "#articleId", cacheManager = "caffeineCacheManager")
	public List getGoGoGoCaffeine(long articleId) {

		log.error("come getGoGoGoCaffeine {}", articleId);
		List<String> list = new ArrayList<>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		list.add("eee");
		list.add("fff");

		return list;
	}

	@CachePut(value= "putGoGo", key= "#articleId")
	public List putgogo(long articleId) {

		log.error("come putGoGo {}", articleId);
		List<String> list = new ArrayList<>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		list.add("eee");
		list.add("fff");

		return list;
	}



	@Caching(
			evict= {
					@CacheEvict(value= "allInfos", key= "#articleId"),
					@CacheEvict(value= "gogogo", key= "#articleId")
			}
	)
	public List del(long articleId) {

		log.info("do del stuff");
//		log.error("come here {}", articleId);
//		List<String> list = new ArrayList<>();
//		list.add("aaa");
//		list.add("bbb");
//		list.add("ccc");
//		list.add("ddd");
//		list.add("eee");
//		list.add("fff");

		return Collections.EMPTY_LIST;
	}
}
