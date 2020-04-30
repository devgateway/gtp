package org.devgateway.toolkit.persistence.service.testdata;

import static java.util.stream.Collectors.toList;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
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
            generateActual();
            generateRefs();
            return null;
        });
    }

    private void generateActual() {
        decadalRainfallRepository.deleteAll();

        List<PluviometricPost> pluviometricPosts = pluviometricPostRepository.findAll();

        for (Integer year : years) {
            for (Month m : DBConstants.MONTHS) {
                for (Decadal d : Decadal.values()) {
                    DecadalRainfall drf = new DecadalRainfall();
                    drf.setYear(year);
                    drf.setMonth(m);
                    drf.setDecadal(d);
                    drf.setFormStatus(FormStatus.PUBLISHED);
                    drf.setPostRainfalls(generatePluviometricPostRainfalls(drf, pluviometricPosts));
                    decadalRainfallRepository.saveAndFlush(drf);
                }
            }
        }
    }

    private void generateRefs() {
        List<PluviometricPost> pluviometricPosts = pluviometricPostRepository.findAll();

        RainLevelReference rainLevelReference = rainLevelReferenceRepository
                .findByYearStartLessThanEqualAndYearEndGreaterThanEqual(2018, 2002);

        rainLevelReference.getPostRainReferences().clear();
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
        Random random = new Random();
        List<RainLevelMonthReference> rls = new ArrayList<>();
        for (Month m : DBConstants.MONTHS) {
            for (Decadal d : Decadal.values()) {
                RainLevelMonthReference rl = new RainLevelMonthReference();
                rl.setMonth(m);
                rl.setDecadal(d);
                rl.setRain(random.nextInt(100) / 10d);
                rl.setRainLevelPluviometricPostReference(ppr);
                rls.add(rl);
            }
        }
        return rls;
    }

    private List<PluviometricPostRainfall> generatePluviometricPostRainfalls(DecadalRainfall drf,
            List<PluviometricPost> pluviometricPosts) {
        Random random = new Random();
        return pluviometricPosts.stream().map(pp -> {
            PluviometricPostRainfall pprf = new PluviometricPostRainfall();
            pprf.setDecadalRainfall(drf);
            pprf.setPluviometricPost(pp);
            pprf.setRainfalls(days(drf).mapToObj(d -> {
                Rainfall rf = new Rainfall();
                rf.setPluviometricPostRainfall(pprf);
                rf.setDay(d);
                rf.setRain(random.nextInt(100) / 10d);
                return rf;
            }).collect(toList()));
            return pprf;
        }).collect(toList());
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
