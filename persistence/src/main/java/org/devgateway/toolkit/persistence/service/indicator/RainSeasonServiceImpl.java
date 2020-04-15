package org.devgateway.toolkit.persistence.service.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.RainSeason;
import org.devgateway.toolkit.persistence.repository.indicator.RainSeasonRepository;
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
public class RainSeasonServiceImpl extends BaseJpaServiceImpl<RainSeason> implements RainSeasonService {

    @Autowired
    private RainSeasonRepository rainSeasonRepository;

    @Override
    public RainSeason newInstance() {
        return new RainSeason();
    }

    @Override
    protected BaseJpaRepository<RainSeason, Long> repository() {
        return rainSeasonRepository;
    }

    @Override
    public boolean existsByYear(Integer year) {
        return rainSeasonRepository.existsByYear(year);
    }

    @Override
    @Transactional(readOnly = false)
    public void generate(Integer year) {
        RainSeason rainSeason = new RainSeason();
        rainSeason.setYear(year);
        rainSeasonRepository.saveAndFlush(rainSeason);
    }
}
