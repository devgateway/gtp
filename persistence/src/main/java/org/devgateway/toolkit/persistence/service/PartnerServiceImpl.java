package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Partner;
import org.devgateway.toolkit.persistence.repository.PartnerRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Service
@CacheConfig(cacheNames = "servicesCache") // TODO if not specified then persistence project can't run?
@Transactional(readOnly = true)
public class PartnerServiceImpl extends BaseJpaServiceImpl<Partner> implements PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    protected BaseJpaRepository<Partner, Long> repository() {
        return partnerRepository;
    }

    @Override
    public Partner newInstance() {
        return new Partner();
    }
}
