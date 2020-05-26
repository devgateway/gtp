package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.repository.category.ProductTypeRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.norepository.UniquePropertyRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class ProductTypeServiceImpl extends BaseJpaServiceImpl<ProductType> implements ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Override
    protected BaseJpaRepository<ProductType, Long> repository() {
        return productTypeRepository;
    }

    @Override
    public ProductType newInstance() {
        return new ProductType();
    }

    @Override
    public UniquePropertyRepository<ProductType, Long> uniquePropertyRepository() {
        return productTypeRepository;
    }
}
