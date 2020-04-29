package org.devgateway.toolkit.persistence.repository.reference;

import java.util.List;

import org.devgateway.toolkit.persistence.dto.rainfall.MonthDecadalRainLevel;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface RainLevelReferenceRepository extends YearsReferenceRepository<RainLevelReference> {

    @Query("select new org.devgateway.toolkit.persistence.dto.rainfall.MonthDecadalRainLevel("
            + "rlmr.month, rlmr.decadal, rlmr.rain) "
            + "from RainLevelReference rlr "
            + "join rlr.postRainReferences prr "
            + "join prr.rainLevelMonthReferences rlmr "
            + "where prr.pluviometricPost.id = :pluviometricPostId "
            + "and rlr = :rainLevelReference "
            + "order by rlmr.month, rlmr.decadal")
    List<MonthDecadalRainLevel> findRainLevels(RainLevelReference rainLevelReference, Long pluviometricPostId);
}
