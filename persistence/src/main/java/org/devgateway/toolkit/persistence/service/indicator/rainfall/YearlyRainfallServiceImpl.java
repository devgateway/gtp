package org.devgateway.toolkit.persistence.service.indicator.rainfall;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.FormStatus;
import org.devgateway.toolkit.persistence.dao.indicator.StationDecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.dto.drysequence.MonthDecadalDaysWithRain;
import org.devgateway.toolkit.persistence.dto.rainfall.DecadalInstantRainLevel;
import org.devgateway.toolkit.persistence.repository.indicator.YearlyRainfallRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.status.DataEntryStatus;
import org.devgateway.toolkit.persistence.status.RainfallYearProgress;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;

/**
 * @author Nadejda Mandrescu
 */
@Service
@Transactional(readOnly = true)
public class YearlyRainfallServiceImpl extends BaseJpaServiceImpl<YearlyRainfall> implements YearlyRainfallService {

    @Autowired
    private YearlyRainfallRepository yearlyRainfallRepository;

    @Autowired
    private PluviometricPostService pluviometricPostService;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Override
    protected BaseJpaRepository<YearlyRainfall, Long> repository() {
        return yearlyRainfallRepository;
    }

    @Override
    public YearlyRainfall newInstance() {
        return new YearlyRainfall();
    }

    @Override
    public boolean existsByYear(Integer year) {
        return yearlyRainfallRepository.existsByYear(year);
    }

    @Override
    @Transactional
    public void generate(Integer year) {
        yearlyRainfallRepository.saveAndFlush(new YearlyRainfall(year));
    }

    @Override
    @Transactional
    public <S extends YearlyRainfall> S saveAndFlush(final S entity) {
        entity.getStationDecadalRainfalls().removeIf(this::rainIsNull);

        return repository().saveAndFlush(entity);
    }

    private boolean rainIsNull(StationDecadalRainfall rainfall) {
        return rainfall.getRainfall() == null;
    }

    @Override
    public List<Long> findPluviometricPostsWithData() {
        return yearlyRainfallRepository.findPluviometricPostsWithData().stream()
                .map(AbstractPersistable::getId)
                .collect(toList());
    }

    @Override
    public List<Integer> findYearsWithData() {
        return yearlyRainfallRepository.findYearsWithData(adminSettingsService.getStartingYear());
    }

    @Override
    public List<DecadalInstantRainLevel> findRainLevels(Collection<Integer> years, Long pluviometricPostId) {
        List<YearlyRainfall> yearlyRainfalls = yearlyRainfallRepository
                .findByFormStatusAndYearIn(FormStatus.PUBLISHED, years);

        return yearlyRainfalls.stream()
                .map(YearlyRainfall::getStationDecadalRainfalls)
                .flatMap(Collection::stream)
                .filter(sdr -> sdr.getPluviometricPost().getId().equals(pluviometricPostId))
                .map(DecadalInstantRainLevel::new)
                .sorted()
                .collect(toList());
    }

    @Override
    public List<MonthDecadalDaysWithRain> findMonthDecadalDaysWithRain(Integer year, Long pluviometricPostId) {
        List<YearlyRainfall> yearlyRainfalls = yearlyRainfallRepository
                .findByFormStatusAndYearIn(FormStatus.PUBLISHED, ImmutableList.of(year));

        return yearlyRainfalls.stream()
                .map(YearlyRainfall::getStationDecadalRainfalls)
                .flatMap(Collection::stream)
                .filter(sdr -> sdr.getPluviometricPost().getId().equals(pluviometricPostId))
                .map(MonthDecadalDaysWithRain::new)
                .sorted()
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public void export(YearlyRainfall yearlyRainfall, OutputStream outputStream) throws IOException {
        YearlyRainfallWriter writer = new YearlyRainfallWriter(yearlyRainfall);
        writer.write(outputStream);
    }

    @Override
    @Transactional(readOnly = true)
    public YearlyRainfall getExample(Integer year) {
        YearlyRainfall yr = new YearlyRainfall(year);

        pluviometricPostService.findAll().forEach(pluviometricPost -> {
            for (Month month : MONTHS) {
                for (Decadal decadal : Decadal.values()) {
                    StationDecadalRainfall sdr = new StationDecadalRainfall();
                    sdr.setMonth(month);
                    sdr.setDecadal(decadal);
                    sdr.setPluviometricPost(pluviometricPost);
                    yr.addStationDecadalRainfall(sdr);
                }
            }
        });
        return yr;
    }

    public RainfallYearProgress getProgress(Integer year) {
        YearlyRainfall yearlyRainfall = yearlyRainfallRepository.findByYear(year);

        Map<Month, DataEntryStatus> statusByMonth = new HashMap<>();

        DataEntryStatus status;
        Set<Month> monthsWithData = new HashSet<>();
        if (yearlyRainfall == null || yearlyRainfall.getFormStatus() == FormStatus.NOT_STARTED) {
            status = DataEntryStatus.NO_DATA;
        } else {
            yearlyRainfall.getStationDecadalRainfalls().stream()
                    .filter(sdr -> sdr.getRainfall() != null)
                    .map(StationDecadalRainfall::getMonth)
                    .forEach(monthsWithData::add);

            status = yearlyRainfall.getFormStatus() == FormStatus.DRAFT
                    ? DataEntryStatus.DRAFT
                    : DataEntryStatus.PUBLISHED;
        }

        YearMonth now = YearMonth.now(AD3Clock.systemDefaultZone());

        for (Month month : MONTHS) {
            if (monthsWithData.contains(month)) {
                statusByMonth.put(month, status);
            } else if (YearMonth.of(year, month).isBefore(now)) {
                statusByMonth.put(month, DataEntryStatus.NO_DATA);
            } else {
                statusByMonth.put(month, DataEntryStatus.NOT_APPLICABLE);
            }
        }

        return new RainfallYearProgress(statusByMonth);
    }
}
