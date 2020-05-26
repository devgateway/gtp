package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.TextSearchableService;
import org.devgateway.toolkit.persistence.service.UniquePropertyService;

/**
 * @author Octavian Ciubotaru
 */
public interface ProductTypeService extends BaseJpaService<ProductType>, UniquePropertyService<ProductType>,
        TextSearchableService<ProductType> {
}
