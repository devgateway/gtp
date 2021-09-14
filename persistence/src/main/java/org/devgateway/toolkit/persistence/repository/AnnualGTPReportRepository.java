package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * @author Octavian Ciubotaru
 */
public interface AnnualGTPReportRepository extends BaseJpaRepository<AnnualGTPReport, Long> {

    @CacheHibernateQueryResult
    @Query("select distinct b.department from AnnualGTPReport b join b.uploads where b.year >= :startingYear")
    Set<Department> findAllDepartments(int startingYear);

    @CacheHibernateQueryResult
    @Query("select distinct b "
            + "from AnnualGTPReport b "
            + "join b.uploads "
            + "where b.year >= :startingYear "
            + "and (:departmentId is null and b.department.id is null "
            + "or :departmentId is not null and b.department.id = :departmentId)")
    List<AnnualGTPReport> findAllWithUploadsAndDepartment(int startingYear, Long departmentId);

    @CacheHibernateQueryResult
    @Query("select distinct b "
            + "from AnnualGTPReport b "
            + "join b.uploads "
            + "where b.year = :year")
    List<AnnualGTPReport> findAllWithUploadsByYear(int year);
}
