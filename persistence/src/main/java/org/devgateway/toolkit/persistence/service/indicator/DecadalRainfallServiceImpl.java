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
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Service
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

    @Override
    @Transactional(readOnly = false)
    public <S extends DecadalRainfall> S saveAndFlush(final S entity) {
        List<PluviometricPostRainfall> pluviometricPostRainfalls = entity.getPostRainfalls()
                .stream()
                .filter(pluviometricPostRainfall -> {
                    List<Rainfall> rainfalls = pluviometricPostRainfall.getRainfalls()
                            .stream()
                            .filter(rainfall -> rainfall.getRain() != null)
                            .collect(toList());
                    pluviometricPostRainfall.setRainfalls(rainfalls);
                    return !rainfalls.isEmpty();
                })
                .collect(toList());
        entity.setPostRainfalls(pluviometricPostRainfalls);

        return repository().saveAndFlush(entity);
    }

    @Override
    public List<Long> findPluviometricPostsWithData() {
        return decadalRainfallRepository.findPluviometricPostsWithData();
    }

    @Override
    public List<Integer> findYearsWithData() {
        return decadalRainfallRepository.findYearsWithData();
    }

    @Override
    public List<DecadalInstantRainLevel> findRainLevels(Collection<Integer> years, Long pluviometricPostId) {
        List<DecadalRainfall> decadalRainfalls = decadalRainfallRepository
                .findByFormStatusAndYearIn(FormStatus.PUBLISHED, years);

        return decadalRainfalls.stream()
                .map(drf -> getRainLevelForPost(drf, pluviometricPostId))
                .sorted()
                .collect(toList());
    }

    private DecadalInstantRainLevel getRainLevelForPost(DecadalRainfall drf, Long pluviometricPostId) {
        double val = drf.getPostRainfalls().stream()
                .filter(prf -> prf.getPluviometricPost().getId().equals(pluviometricPostId))
                .findFirst()
                .map(prf -> prf.getRainfalls().stream().mapToDouble(Rainfall::getRain).sum())
                .orElse(0d);

        return new DecadalInstantRainLevel(drf.getYear(), drf.getMonth(), drf.getDecadal(), val);
    }

    @Override
    public List<MonthDecadalDaysWithRain> findMonthDecadalDaysWithRain(Integer year, Long pluviometricPostId) {
        List<DecadalRainfall> decadalRainfalls = decadalRainfallRepository
                .findByFormStatusAndYearIn(FormStatus.PUBLISHED, ImmutableList.of(year));

        return decadalRainfalls.stream()
                .map(drf -> getDaysWithRainForPost(drf, pluviometricPostId))
                .sorted()
                .collect(toList());
    }

    private MonthDecadalDaysWithRain getDaysWithRainForPost(DecadalRainfall drf, Long pluviometricPostId) {
        long days = drf.getPostRainfalls().stream()
                .filter(prf -> prf.getPluviometricPost().getId().equals(pluviometricPostId))
                .findFirst()
                .map(prf -> prf.getRainfalls().stream().filter(rf -> rf.getRain() > 0).count())
                .orElse(0L);

        return new MonthDecadalDaysWithRain(drf.getYear(), drf.getMonth(), drf.getDecadal(), days);
    }
}
