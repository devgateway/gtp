package org.devgateway.toolkit.persistence.service.indicator;

import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.repository.indicator.ProductYearlyPricesRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class ProductYearlyPricesServiceImpl extends BaseJpaServiceImpl<ProductYearlyPrices>
        implements ProductYearlyPricesService {

    @Autowired
    private ProductYearlyPricesRepository repository;

    @Override
    protected BaseJpaRepository<ProductYearlyPrices, Long> repository() {
        return repository;
    }

    @Override
    public boolean existsByYear(Integer year) {
        return false;
    }

    @Override
    public void generate(Integer year) {
    }

    @Override
    public ProductYearlyPrices newInstance() {
        return new ProductYearlyPrices();
    }
}
