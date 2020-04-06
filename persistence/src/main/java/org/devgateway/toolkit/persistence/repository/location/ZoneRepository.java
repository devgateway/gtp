package org.devgateway.toolkit.persistence.repository.location;

import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;

@CacheConfig(cacheNames = "servicesCache")
@Transactional
public interface ZoneRepository extends BaseJpaRepository<Zone, Long> {

}
