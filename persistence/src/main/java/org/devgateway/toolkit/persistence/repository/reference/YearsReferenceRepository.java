package org.devgateway.toolkit.persistence.repository.reference;

import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Nadejda Mandrescu
 */
@NoRepositoryBean
public interface YearsReferenceRepository<T> extends BaseJpaRepository<T, Long> {
    T findByYearStartLessThanEqualAndYearEndGreaterThanEqual(Integer yearStart,
            Integer yearEnd);
}
