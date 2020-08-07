package org.devgateway.toolkit.persistence.repository;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Octavian Ciubotaru
 */
public interface AnnualGTPReportRepository extends BaseJpaRepository<AnnualGTPReport, Long> {

    @CacheHibernateQueryResult
    @Query("select distinct b "
            + "from AnnualGTPReport b "
            + "join b.uploads "
            + "where b.year >= :startingYear")
    List<AnnualGTPReport> findAllWithUploads(int startingYear);
}
