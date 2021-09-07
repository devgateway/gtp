package org.devgateway.toolkit.persistence.service.indicator.rainfallMap;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;

import org.devgateway.toolkit.persistence.dao.DBConstants;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.MonthDecadal;
import org.devgateway.toolkit.persistence.dao.YearMonthDecadal;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;
import org.devgateway.toolkit.persistence.repository.indicator.DecadalRainfallMapRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.status.DataEntryStatus;
import org.devgateway.toolkit.persistence.status.RainfallMapProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public RainfallMapProgress getProgress(Integer year) {
        Set<MonthDecadal> monthDecadalsWithData = repository.findByYear(year).stream()
                .filter(z -> !z.isEmpty())
                .map(z -> new MonthDecadal(z.getMonth(), z.getDecadal()))
                .collect(Collectors.toSet());

        YearMonthDecadal now = YearMonthDecadal.now();

        Map<MonthDecadal, DataEntryStatus> statuses = new HashMap<>();
        DBConstants.MONTH_DECADALS.forEach(md -> {
            if (monthDecadalsWithData.contains(md)) {
                statuses.put(md, DataEntryStatus.PUBLISHED);
            } else if (new YearMonthDecadal(year, md).compareTo(now) < 0) {
                statuses.put(md, DataEntryStatus.NO_DATA);
            } else {
                statuses.put(md, DataEntryStatus.NOT_APPLICABLE);
            }
        });

        return new RainfallMapProgress(statuses);
    }
}
