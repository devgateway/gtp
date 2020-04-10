package org.devgateway.toolkit.persistence.service.indicator;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.repository.indicator.DecadalRainfallRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class DecadalRainfallServiceImpl extends BaseJpaServiceImpl<DecadalRainfall> implements DecadalRainfallService {
    @Autowired
    private DecadalRainfallRepository decadalRainfallRepository;

    @Override
    protected BaseJpaRepository<DecadalRainfall, Long> repository() {
        return decadalRainfallRepository;
    }

    @Override
    public DecadalRainfall newInstance() {
        return new DecadalRainfall();
    }

    @Override
    public boolean existsByYear(Integer year) {
        return decadalRainfallRepository.existsByYear(year);
    }

    @Override
    @Transactional(readOnly = false)
    public void generate(Integer year) {
        List<DecadalRainfall> rainfalls = new ArrayList<>();
        for (Month month : MONTHS) {
            for (Decadal decadal : Decadal.values()) {
                DecadalRainfall decadalRainfall = new DecadalRainfall();
                decadalRainfall.setYear(year);
                decadalRainfall.setMonth(month);
                decadalRainfall.setDecadal(decadal);
                rainfalls.add(decadalRainfall);
            }
        }
        decadalRainfallRepository.saveAll(rainfalls);
    }
}
