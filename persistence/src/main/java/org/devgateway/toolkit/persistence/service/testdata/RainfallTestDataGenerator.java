package org.devgateway.toolkit.persistence.service.testdata;

import static java.util.stream.Collectors.toList;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.devgateway.toolkit.persistence.dao.DBConstants;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.FormStatus;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.Rainfall;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelMonthReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelPluviometricPostReference;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.repository.category.PluviometricPostRepository;
import org.devgateway.toolkit.persistence.repository.indicator.DecadalRainfallRepository;
import org.devgateway.toolkit.persistence.repository.reference.RainLevelReferenceRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

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
    private final DecadalRainfallRepository decadalRainfallRepository;
    private final RainLevelReferenceRepository rainLevelReferenceRepository;

    private final List<Integer> years = ImmutableList.of(2018, 2019, 2020);

    public RainfallTestDataGenerator(
            PlatformTransactionManager transactionManager,
            PluviometricPostRepository pluviometricPostRepository,
            DecadalRainfallRepository decadalRainfallRepository,
            RainLevelReferenceRepository rainLevelReferenceRepository) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.pluviometricPostRepository = pluviometricPostRepository;
        this.decadalRainfallRepository = decadalRainfallRepository;
        this.rainLevelReferenceRepository = rainLevelReferenceRepository;
    }

    @Override
    public void afterPropertiesSet() {
        transactionTemplate.execute(s -> {
            decadalRainfallRepository.deleteAll();

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
            for (Month m : DBConstants.MONTHS) {
                for (Decadal d : Decadal.values()) {
                    DecadalRainfall drf = new DecadalRainfall();
                    drf.setYear(year);
                    drf.setMonth(m);
                    drf.setDecadal(d);
                    drf.setFormStatus(FormStatus.PUBLISHED);
                    generatePluviometricPostRainfalls(drf, pluviometricPosts).forEach(drf::addPostRainfall);
                    decadalRainfallRepository.saveAndFlush(drf);
                }
            }
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

    private List<PluviometricPostRainfall> generatePluviometricPostRainfalls(DecadalRainfall drf,
            List<PluviometricPost> pluviometricPosts) {
        return pluviometricPosts.stream().map(pp -> {
            PluviometricPostRainfall pprf = new PluviometricPostRainfall(pp);
            days(drf).mapToObj(d -> new Rainfall(d, getRandomRainFall(drf.getMonth())))
                    .forEach(pprf::addRainfall);
            return pprf;
        }).collect(toList());
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

    private IntStream days(DecadalRainfall drf) {
        if (drf.getDecadal() == Decadal.FIRST) {
            return IntStream.rangeClosed(1, 10);
        } else if (drf.getDecadal() == Decadal.SECOND) {
            return IntStream.rangeClosed(11, 20);
        } else {
            return IntStream.rangeClosed(21, drf.getMonth().length(Year.isLeap(drf.getYear())));
        }
    }
}
