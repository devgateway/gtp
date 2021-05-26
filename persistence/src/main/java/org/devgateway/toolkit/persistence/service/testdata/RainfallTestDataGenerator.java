package org.devgateway.toolkit.persistence.service.testdata;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.devgateway.toolkit.persistence.dao.DBConstants;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.FormStatus;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.StationDecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelMonthReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelPluviometricPostReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.repository.category.PluviometricPostRepository;
import org.devgateway.toolkit.persistence.repository.indicator.YearlyRainfallRepository;
import org.devgateway.toolkit.persistence.repository.reference.RainLevelReferenceRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;

/**
 * @author Octavian Ciubotaru
 */
@Component
@ConditionalOnProperty("generate-test-rainfall-data")
public class RainfallTestDataGenerator implements InitializingBean {

    private static final Map<Month, Integer> AVG_RAINY_DAYS = ImmutableMap.<Month, Integer>builder()
            .put(Month.MAY, 1)
            .put(Month.JUNE, 4)
            .put(Month.JULY, 9)
            .put(Month.AUGUST, 15)
            .put(Month.SEPTEMBER, 13)
            .put(Month.OCTOBER, 8)
            .build();

    private static final int TOTAL_AVG_RAINY_DAYS = AVG_RAINY_DAYS.values().stream().mapToInt(i -> i).sum();

    private static final int AVG_RAIN = 500;

    private final TransactionTemplate transactionTemplate;

    private final PluviometricPostRepository pluviometricPostRepository;
    private final YearlyRainfallRepository yearlyRainfallRepository;
    private final RainLevelReferenceRepository rainLevelReferenceRepository;

    private final List<Integer> years = ImmutableList.of(2018, 2019, 2020);

    public RainfallTestDataGenerator(
            PlatformTransactionManager transactionManager,
            PluviometricPostRepository pluviometricPostRepository,
            YearlyRainfallRepository yearlyRainfallRepository,
            RainLevelReferenceRepository rainLevelReferenceRepository) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.pluviometricPostRepository = pluviometricPostRepository;
        this.yearlyRainfallRepository = yearlyRainfallRepository;
        this.rainLevelReferenceRepository = rainLevelReferenceRepository;
    }

    @Override
    public void afterPropertiesSet() {
        transactionTemplate.execute(s -> {
            yearlyRainfallRepository.deleteAll();

            RainLevelReference rainLevelReference = rainLevelReferenceRepository
                    .findByYearStartLessThanEqualAndYearEndGreaterThanEqual(2018, 2002);
            rainLevelReference.getPostRainReferences().clear();

            return null;
        });
        transactionTemplate.execute(s -> {
            generateActual();
            generateRefs();
            return null;
        });
    }

    private void generateActual() {
        List<PluviometricPost> pluviometricPosts = pluviometricPostRepository.findAll();

        for (Integer year : years) {
            YearlyRainfall yr = new YearlyRainfall(year);
            yr.setFormStatus(FormStatus.PUBLISHED);
            for (Month m : DBConstants.MONTHS) {
                for (Decadal d : Decadal.values()) {
                    pluviometricPosts.stream()
                            .map(pp -> generateStationRainfall(new StationDecadalRainfall(pp, m, d)))
                            .forEach(yr::addStationDecadalRainfall);
                }
            }
            yearlyRainfallRepository.saveAndFlush(yr);
        }
    }

    private void generateRefs() {
        List<PluviometricPost> pluviometricPosts = pluviometricPostRepository.findAll();

        RainLevelReference rainLevelReference = rainLevelReferenceRepository
                .findByYearStartLessThanEqualAndYearEndGreaterThanEqual(2018, 2002);

        rainLevelReference.getPostRainReferences().addAll(pluviometricPosts.stream().map(pp -> {
            RainLevelPluviometricPostReference ppr = new RainLevelPluviometricPostReference();
            ppr.setPluviometricPost(pp);
            ppr.setRainLevelReference(rainLevelReference);
            ppr.setRainLevelMonthReferences(getRainLevelMonthReferences(ppr));
            return ppr;
        }).collect(toList()));
    }

    private List<RainLevelMonthReference> getRainLevelMonthReferences(
            RainLevelPluviometricPostReference ppr) {

        List<RainLevelMonthReference> rls = new ArrayList<>();
        for (Month m : DBConstants.MONTHS) {
            for (Decadal d : Decadal.values()) {
                RainLevelMonthReference rl = new RainLevelMonthReference();
                rl.setMonth(m);
                rl.setDecadal(d);
                rl.setRain(getRandomRainFall(m));
                rl.setRainLevelPluviometricPostReference(ppr);
                rls.add(rl);
            }
        }
        return rls;
    }

    private StationDecadalRainfall generateStationRainfall(StationDecadalRainfall sdr) {
        sdr.setRainfall(getRandomRainFall(sdr.getMonth()));
        sdr.setRainyDaysCount(getRandomRainyDays(sdr));
        return sdr;
    }

    private double getRandomRainFall(Month month) {
        Integer rainyDays = AVG_RAINY_DAYS.get(month);
        if (ThreadLocalRandom.current().nextInt(month.length(false)) < rainyDays) {
            double v = ThreadLocalRandom.current().nextDouble() * AVG_RAIN * rainyDays / TOTAL_AVG_RAINY_DAYS;
            return ((int) (v * 10)) / 10d;
        } else {
            return 0;
        }
    }

    private int getRandomRainyDays(StationDecadalRainfall sdr) {
        if (sdr.getRainfall() == 0) {
            return 0;
        }
        int maxDays = sdr.getDecadal().length(YearMonth.of(sdr.getYearlyRainfall().getYear(), sdr.getMonth()));
        return ThreadLocalRandom.current().nextInt(maxDays + 1);
    }
}
