package org.devgateway.toolkit.persistence.repository.indicator;

import java.util.Collection;
import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dto.rainfall.DecadalInstantRainLevel;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface DecadalRainfallRepository extends BaseJpaRepository<DecadalRainfall, Long> {

    boolean existsByYear(Integer year);

    @Query("select distinct prf.pluviometricPost.id "
            + "from DecadalRainfall drf "
            + "join drf.postRainfalls prf "
            + "where drf.formStatus = 'PUBLISHED'")
    List<Long> findPluviometricPostsWithData();

    @Query("select distinct year "
            + "from DecadalRainfall "
            + "where formStatus = 'PUBLISHED'")
    List<Integer> findYearsWithData();

    @Query("select new org.devgateway.toolkit.persistence.dto.rainfall.DecadalInstantRainLevel("
            + "drf.year, drf.month, drf.decadal, sum(rf.rain)) "
            + "from DecadalRainfall drf "
            + "join drf.postRainfalls prf "
            + "join prf.rainfalls rf "
            + "where drf.formStatus = 'PUBLISHED' "
            + "and drf.year in :years "
            + "and prf.pluviometricPost.id = :pluviometricPostId "
            + "group by drf.year, drf.month, drf.decadal "
            + "order by drf.year, drf.month, drf.decadal")
    List<DecadalInstantRainLevel> findRainLevels(Collection<Integer> years, Long pluviometricPostId);
}
