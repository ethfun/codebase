package com.inglc.codebase.service;

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
public interface CacheService {


	List getAllInfos(long articleId);

	List getGoGoGo(long articleId);

	List getGoGoGoCaffeine(long articleId);

	List putgogo(long articleId);

	List del(long articleId);
}
