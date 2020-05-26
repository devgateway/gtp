package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.repository.category.ProductRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.norepository.UniquePropertyRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class ProductServiceImpl extends BaseJpaServiceImpl<Product> implements ProductService {

    @Autowired
    private ProductRepository productTypeRepository;

    @Override
    protected BaseJpaRepository<Product, Long> repository() {
        return productTypeRepository;
    }

    @Override
    public Product newInstance() {
        return new Product();
    }

    @Override
    public UniquePropertyRepository<Product, Long> uniquePropertyRepository() {
        return productTypeRepository;
    }
}
