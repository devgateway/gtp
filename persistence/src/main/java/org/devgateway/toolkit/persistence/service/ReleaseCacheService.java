package org.devgateway.toolkit.persistence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Created by dbianco
 */
@Service
public class ReleaseCacheService {

    protected static Logger logger = LoggerFactory.getLogger(ReleaseCacheService.class);

    @Autowired
    private CacheManager cacheManager;

    public void releaseCache() {
        logger.info("Releasing Cache");
        cacheManager.getCacheNames().forEach(c -> cacheManager.getCache(c).clear());
    }
}
