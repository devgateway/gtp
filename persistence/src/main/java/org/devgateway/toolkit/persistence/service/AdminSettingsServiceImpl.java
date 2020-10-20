package org.devgateway.toolkit.persistence.service;

import static java.util.stream.Collectors.toList;

import org.devgateway.toolkit.persistence.dao.AdminSettings;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.repository.AdminSettingsRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Transactional
    public <S extends AdminSettings> S save(final S entity) {
        preProcessRebootAlert(entity);
        return repository().save(entity);
    }

    @Override
    @Transactional
    public <S extends AdminSettings> S saveAndFlush(final S entity) {
        preProcessRebootAlert(entity);
        return repository().saveAndFlush(entity);
    }

    private <S extends AdminSettings> void preProcessRebootAlert(S as) {
        if (!as.isRebootServer()) {
            as.setRebootAlertSince(null);
        } else if (as.getRebootAlertSince() == null) {
            as.setRebootAlertSince(LocalDateTime.now());
        }
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
        return IntStream.rangeClosed(getStartingYear(), LocalDate.now(AD3Clock.systemDefaultZone()).getYear())
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    public List<HydrologicalYear> getHydrologicalYears() {
        return IntStream.rangeClosed(getStartingYear(), HydrologicalYear.now().getYear())
                .mapToObj(HydrologicalYear::new)
                .collect(toList());
    }
}
