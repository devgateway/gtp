package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.devgateway.toolkit.persistence.service.UniquePropertyService;

/**
 * @author Octavian Ciubotaru
 */
public interface ProductService extends BaseJpaService<Product>, UniquePropertyService<Product> {
}
