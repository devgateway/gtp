package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.TextSearchableService;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
public interface PriceTypeService extends BaseJpaService<PriceType>,
        TextSearchableService<PriceType> {

    List<PriceType> findByIds(List<Long> ids);

    PriceType findByName(String name);
}
