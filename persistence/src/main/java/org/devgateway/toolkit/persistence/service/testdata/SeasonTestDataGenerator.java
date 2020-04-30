package org.devgateway.toolkit.persistence.service.testdata;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.FormStatus;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.dao.indicator.RainSeason;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonPluviometricPostReferenceStart;
import org.devgateway.toolkit.persistence.dao.reference.RainSeasonStartReference;
import org.devgateway.toolkit.persistence.repository.category.PluviometricPostRepository;
import org.devgateway.toolkit.persistence.repository.indicator.RainSeasonRepository;
import org.devgateway.toolkit.persistence.repository.reference.RainSeasonStartReferenceRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Octavian Ciubotaru
 */
@Component
@ConditionalOnProperty("generate-test-season-data")
public class SeasonTestDataGenerator implements InitializingBean {

    private final TransactionTemplate transactionTemplate;

    private final RainSeasonRepository rainSeasonRepository;
    private final RainSeasonStartReferenceRepository rainSeasonStartReferenceRepository;
    private final PluviometricPostRepository pluviometricPostRepository;

    private final List<Integer> years = ImmutableList.of(2018, 2019, 2020);

    public SeasonTestDataGenerator(
            PlatformTransactionManager transactionManager,
            RainSeasonRepository rainSeasonRepository,
            RainSeasonStartReferenceRepository rainSeasonStartReferenceRepository,
            PluviometricPostRepository pluviometricPostRepository) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.rainSeasonRepository = rainSeasonRepository;
        this.rainSeasonStartReferenceRepository = rainSeasonStartReferenceRepository;
        this.pluviometricPostRepository = pluviometricPostRepository;
    }

    @Override
    public void afterPropertiesSet() {
        transactionTemplate.execute(s -> {
            generateData();
            generateRefData();
            return null;
        });
    }

    private void generateRefData() {
        List<RainSeasonStartReference> seasonReferences = ImmutableList.of(rainSeasonStartReferenceRepository
                .findByYearStartLessThanEqualAndYearEndGreaterThanEqual(2020, 2020));

        List<PluviometricPost> posts = pluviometricPostRepository.findAll();

        seasonReferences.forEach(sr -> {
            sr.getPostReferences().clear();
            posts.forEach(p -> {
                RainSeasonPluviometricPostReferenceStart spr = new RainSeasonPluviometricPostReferenceStart();
                spr.setPluviometricPost(p);
                spr.setStartReference(MonthDay.from(getRandomDate(sr.getReferenceYearStart())));
                sr.addPostReference(spr);
            });
        });
    }

    private void generateData() {
        rainSeasonRepository.deleteAll();

        List<PluviometricPost> posts = pluviometricPostRepository.findAll();

        years.forEach(y -> {
            RainSeason rs = new RainSeason();
            rs.setYear(y);
            posts.forEach(p -> {
                PluviometricPostRainSeason pprs = new PluviometricPostRainSeason();
                pprs.setPluviometricPost(p);
                pprs.setStartDate(getRandomDate(y));
                pprs.setFormStatus(FormStatus.PUBLISHED);
                rs.addPostRainSeason(pprs);
            });
            rainSeasonRepository.save(rs);
        });
    }

    private LocalDate getRandomDate(int year) {
        long min = LocalDate.of(year, 1, 1).toEpochDay();
        long max = LocalDate.of(year, 12, 31).toEpochDay();
        return LocalDate.ofEpochDay(ThreadLocalRandom.current().nextLong(min, max + 1));
    }
}
