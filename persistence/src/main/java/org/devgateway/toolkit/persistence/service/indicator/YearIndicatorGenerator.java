package org.devgateway.toolkit.persistence.service.indicator;

import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.io.Serializable;

/**
 * @author Nadejda Mandrescu
 */
public interface YearIndicatorGenerator<T extends GenericPersistable & Serializable> extends BaseJpaService<T> {

    boolean existsByYear(Integer year);

    void generate(Integer year);
}
