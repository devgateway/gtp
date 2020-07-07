package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toList;
import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.FormStatus;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainfall;
import org.devgateway.toolkit.persistence.dto.drysequence.MonthDecadalDaysWithRain;
import org.devgateway.toolkit.persistence.dto.rainfall.DecadalInstantRainLevel;
import org.devgateway.toolkit.persistence.dao.indicator.Rainfall;
import org.devgateway.toolkit.persistence.repository.indicator.DecadalRainfallRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.service.category.PluviometricPostService;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.DecadalRainfallWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Nadejda Mandrescu
 */
@Service
@Transactional(readOnly = true)
public class DecadalRainfallServiceImpl extends BaseJpaServiceImpl<DecadalRainfall> implements DecadalRainfallService {

    private static final double EPSILON = 0.000001;

    @Autowired
    private DecadalRainfallRepository decadalRainfallRepository;

    @Autowired
    private PluviometricPostService pluviometricPostService;

    @Autowired
    private AdminSettingsService adminSettingsService;

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
    @Transactional
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

    @Override
    @Transactional
    public <S extends DecadalRainfall> S saveAndFlush(final S entity) {
        entity.getPostRainfalls().forEach(this::cleanRainfalls);

        return repository().saveAndFlush(entity);
    }

    private void cleanRainfalls(PluviometricPostRainfall pr) {
        if (pr.getNoData()) {
            pr.getRainfalls().clear();
        } else {
            pr.getRainfalls().removeIf(this::rainIsNullOrZero);
        }
    }

    private boolean rainIsNullOrZero(Rainfall rainfall) {
        return rainfall.getRain() == null || Math.abs(rainfall.getRain()) < EPSILON;
    }

    @Override
    public List<Long> findPluviometricPostsWithData() {
        return decadalRainfallRepository.findPluviometricPostsWithData().stream()
                .map(AbstractPersistable::getId)
                .collect(toList());
    }

    @Override
    public List<Integer> findYearsWithData() {
        return decadalRainfallRepository.findYearsWithData(adminSettingsService.getStartingYear());
    }

    @Override
    public List<DecadalInstantRainLevel> findRainLevels(Collection<Integer> years, Long pluviometricPostId) {
        List<DecadalRainfall> decadalRainfalls = decadalRainfallRepository
                .findByFormStatusAndYearIn(FormStatus.PUBLISHED, years);

        return decadalRainfalls.stream()
                .map(drf -> getRainLevelForPost(drf, pluviometricPostId))
                .filter(Objects::nonNull)
                .sorted()
                .collect(toList());
    }

    private DecadalInstantRainLevel getRainLevelForPost(DecadalRainfall drf, Long pluviometricPostId) {
        return drf.getPostRainfalls().stream()
                .filter(prf -> prf.getPluviometricPost().getId().equals(pluviometricPostId) && !prf.getNoData())
                .findFirst()
                .map(prf -> new DecadalInstantRainLevel(drf, prf.getTotal()))
                .orElse(null);
    }

    @Override
    public List<MonthDecadalDaysWithRain> findMonthDecadalDaysWithRain(Integer year, Long pluviometricPostId) {
        List<DecadalRainfall> decadalRainfalls = decadalRainfallRepository
                .findByFormStatusAndYearIn(FormStatus.PUBLISHED, ImmutableList.of(year));

        return decadalRainfalls.stream()
                .map(drf -> getDaysWithRainForPost(drf, pluviometricPostId))
                .filter(Objects::nonNull)
                .sorted()
                .collect(toList());
    }

    private MonthDecadalDaysWithRain getDaysWithRainForPost(DecadalRainfall drf, Long pluviometricPostId) {
        return drf.getPostRainfalls().stream()
                .filter(prf -> prf.getPluviometricPost().getId().equals(pluviometricPostId) && !prf.getNoData())
                .findFirst()
                .map(prf -> new MonthDecadalDaysWithRain(drf, prf.getRainyDaysCount()))
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public void export(DecadalRainfall decadalRainfall, OutputStream outputStream) throws IOException {
        DecadalRainfallWriter writer = new DecadalRainfallWriter(decadalRainfall);
        writer.write(outputStream);
    }

}
