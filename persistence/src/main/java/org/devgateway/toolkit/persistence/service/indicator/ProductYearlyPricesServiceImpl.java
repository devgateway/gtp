package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;

import org.devgateway.toolkit.persistence.dao.PersistedCollectionSize;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.repository.indicator.ProductYearlyPricesRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<ProductYearlyPrices> findAll(Specification<ProductYearlyPrices> spec, Pageable pageable) {
        Page<ProductYearlyPrices> page = super.findAll(spec, pageable);

        List<Long> ids = page.get()
                .map(AbstractPersistable::getId)
                .collect(toList());

        Map<Long, Long> sizeById = repository.getPriceSizes(ids).stream()
                .collect(toMap(PersistedCollectionSize::getId, PersistedCollectionSize::getSize));

        page.get().forEach(p -> p.setPricesSize(sizeById.getOrDefault(p.getId(), 0L)));

        return page;
    }

    @Override
    public ProductYearlyPrices newInstance() {
        return new ProductYearlyPrices();
    }
}
