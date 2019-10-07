package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.repository.RegionRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class RegionServiceImpl extends BaseJpaServiceImpl<Region> implements RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Override
    protected BaseJpaRepository<Region, Long> repository() {
        return regionRepository;
    }

    @Override
    public Region newInstance() {
        return new Region();
    }

    @Override
    @Cacheable
    public Region findByName(String name) {
        return regionRepository.findByName(name);
    }

    @Override
    @Cacheable
    public Region findByCode(String code) {
        return regionRepository.findByCode(code);
    }
}