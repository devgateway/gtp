package org.devgateway.toolkit.persistence.service.location;

import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.repository.location.ZoneRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class ZoneServiceImpl extends BaseJpaServiceImpl<Zone> implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    protected BaseJpaRepository<Zone, Long> repository() {
        return zoneRepository;
    }

    @Override
    public Zone newInstance() {
        return new Zone();
    }
}
