package org.devgateway.toolkit.persistence.service.category;

import java.util.List;

import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.repository.category.ProductRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.repository.norepository.UniquePropertyRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class ProductServiceImpl extends BaseJpaServiceImpl<Product> implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    protected BaseJpaRepository<Product, Long> repository() {
        return repository;
    }

    @Override
    public Product newInstance() {
        return new Product();
    }

    @Override
    public UniquePropertyRepository<Product, Long> uniquePropertyRepository() {
        return repository;
    }

    @Override
    public List<Product> findByProductType(ProductType productType) {
        return repository.findByProductType(productType);
    }

    @Override
    public JpaRepository<Product, Long> getRepository() {
        return repository;
    }

    @Override
    public Page<Product> searchText(String term, Pageable page) {
        return repository.searchText(term, page);
    }
}
