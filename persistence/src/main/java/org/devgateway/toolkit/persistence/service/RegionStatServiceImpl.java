package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.RegionStat;
import org.devgateway.toolkit.persistence.repository.RegionStatRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dbianco
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class RegionStatServiceImpl extends BaseJpaServiceImpl<RegionStat> implements RegionStatService {
    @Autowired
    private RegionStatRepository repository;

    @Override
    protected BaseJpaRepository<RegionStat, Long> repository() {
        return repository;
    }

    @Override
    public RegionStat newInstance() {
        return new RegionStat();
    }

    @Override
    public List<RegionStat> findPopulation() {
        return repository.findPopulation();
    }
}
