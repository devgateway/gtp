package org.devgateway.toolkit.persistence.repository.location;

import org.devgateway.toolkit.persistence.dao.location.Zone;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@CacheConfig(cacheNames = "servicesCache")
@Transactional
public interface ZoneRepository extends TextSearchableRepository<Zone, Long>, BaseJpaRepository<Zone, Long> {

    @Override
    @Query("select o from Zone o where lower(o.name) like %?1%")
    Page<Zone> searchText(String code, Pageable page);

}
