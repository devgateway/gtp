package org.devgateway.toolkit.persistence.service;

import static java.util.stream.Collectors.toList;

import org.devgateway.toolkit.persistence.dao.AdminSettings;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.repository.AdminSettingsRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author idobre
 * @since 2019-03-04
 */
@Service
@Transactional(readOnly = true)
public class AdminSettingsServiceImpl extends BaseJpaServiceImpl<AdminSettings> implements AdminSettingsService {
    @Autowired
    private AdminSettingsRepository adminSettingsRepository;

    @Override
    protected BaseJpaRepository<AdminSettings, Long> repository() {
        return adminSettingsRepository;
    }

    @Override
    public AdminSettings newInstance() {
        return new AdminSettings();
    }

    @Override
    public AdminSettings get() {
        return repository().findAll().get(0);
    }

    @Override
    public Integer getStartingYear() {
        return get().getStartingYear();
    }

    @Override
    public List<Integer> getYears() {
        return IntStream.rangeClosed(getStartingYear(), LocalDate.now().getYear()).boxed().collect(Collectors.toList());
    }

    @Override
    public List<HydrologicalYear> getHydrologicalYears() {
        return IntStream.rangeClosed(getStartingYear(), HydrologicalYear.now().getYear()).boxed()
                .map(HydrologicalYear::new)
                .collect(toList());
    }
}
