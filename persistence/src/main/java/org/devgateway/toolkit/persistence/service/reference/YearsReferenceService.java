package org.devgateway.toolkit.persistence.service.reference;

import org.devgateway.toolkit.persistence.repository.reference.YearsReferenceRepository;

/**
 * @author Nadejda Mandrescu
 */
public interface YearsReferenceService<T> {
    YearsReferenceRepository<T> yearsReferenceRepository();

    default T findByYearStartLessThanEqualAndYearEndGreaterThanEqual(Integer year) {
        return yearsReferenceRepository().findByYearStartLessThanEqualAndYearEndGreaterThanEqual(year, year);
    }

    void initialize(T reference);
}
