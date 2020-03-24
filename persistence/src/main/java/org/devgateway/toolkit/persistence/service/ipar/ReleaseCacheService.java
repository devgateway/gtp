package org.devgateway.toolkit.persistence.service.ipar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Created by dbianco
 */
// @Service
public class ReleaseCacheService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ReleaseCacheService.class);

    @Autowired
    private CacheManager cacheManager;

    public void releaseCache() {
        LOGGER.info("Releasing Cache");
        cacheManager.getCacheNames().forEach(c -> cacheManager.getCache(c).clear());
    }
}
