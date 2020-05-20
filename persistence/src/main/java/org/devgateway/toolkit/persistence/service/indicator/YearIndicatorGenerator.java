package org.devgateway.toolkit.persistence.service.indicator;

import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.io.Serializable;

/**
 * @param <T> Entity type
 * @param <Y> Year object type
 * @author Nadejda Mandrescu
 */
public interface YearIndicatorGenerator<T extends GenericPersistable & Serializable, Y extends Serializable>
        extends BaseJpaService<T> {

    boolean existsByYear(Y year);

    void generate(Y year);
}
