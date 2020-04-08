package org.devgateway.toolkit.persistence.service.location;

import org.devgateway.toolkit.persistence.dao.location.Region;
import org.devgateway.toolkit.persistence.repository.location.RegionRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
    public JpaRepository<Region, Long> getRepository() {
        return regionRepository;
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

    @Override
    public Page<Region> searchText(String term, Pageable page) {
        return regionRepository.searchText(term, page);
    }
}
