package org.devgateway.toolkit.persistence.service.indicator.rainfallMap;

import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.repository.indicator.DecadalRainfallMapRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;

/**
 * @author Nadejda Mandrescu
 */
@Service
@Transactional(readOnly = true)
public class DecadalRainfallMapServiceImpl extends BaseJpaServiceImpl<DecadalRainfallMap>
        implements DecadalRainfallMapService {

    @Autowired
    private DecadalRainfallMapRepository repository;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Override
    protected BaseJpaRepository<DecadalRainfallMap, Long> repository() {
        return repository;
    }

    @Override
    public boolean existsByYear(Integer year) {
        return repository.existsByYear(year);
    }

    @Override
    @Transactional
    public void generate(Integer year) {
        List<DecadalRainfallMap> rainfalls = new ArrayList<>();
        for (Month month : MONTHS) {
            for (Decadal decadal : Decadal.values()) {
                DecadalRainfallMap decadalRainfallMap = new DecadalRainfallMap();
                decadalRainfallMap.setYear(year);
                decadalRainfallMap.setMonth(month);
                decadalRainfallMap.setDecadal(decadal);
                rainfalls.add(decadalRainfallMap);
            }
        }
        repository.saveAll(rainfalls);

    }

    @Override
    public DecadalRainfallMap newInstance() {
        return new DecadalRainfallMap();
    }

    @Override
    public List<Integer> findYearsWithData() {
        return this.repository.findYearsWithData(adminSettingsService.getStartingYear());
    }

    @Override
    public Month findLastMonthWithData(Integer year) {
        return this.repository.findLastMonthWithData(year);
    }

    @Override
    public Decadal findLastDecadalWithData(Integer year, Month month) {
        return this.repository.findLastDecadalWithData(year, month);
    }

    @Override
    public DecadalRainfallMap findByYearAndMonthAndDecadal(Integer year, Month month, Decadal decadal) {
        return repository.findByYearAndMonthAndDecadal(year, month, decadal);
    }
}
