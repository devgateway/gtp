package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.PersistedCollectionSize;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.dto.agriculture.AveragePrice;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartFilter;
import org.devgateway.toolkit.persistence.repository.category.ProductRepository;
import org.devgateway.toolkit.persistence.repository.indicator.ProductYearlyPricesRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.service.category.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Service
public class ProductYearlyPricesServiceImpl extends BaseJpaServiceImpl<ProductYearlyPrices>
        implements ProductYearlyPricesService {

    @Autowired
    private ProductYearlyPricesRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeService productTypeService;

    @Override
    protected BaseJpaRepository<ProductYearlyPrices, Long> repository() {
        return repository;
    }

    @Override
    public boolean existsByYear(Integer year) {
        return repository.existsByYear(year);
    }

    @Override
    @Transactional
    public void generate(Integer year) {
        productTypeService.findAll().forEach(pt -> repository.save(new ProductYearlyPrices(year, pt)));
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

    @Override
    public List<ProductPrice> findPrices(ProductPricesChartFilter filter) {
        List<PriceType> priceTypes = getPriceTypes(filter.getProductId());
        return repository.findPrices(filter.getYear(), filter.getProductId(), priceTypes, filter.getMarketId());
    }

    @Override
    public List<AveragePrice> findPreviousYearAveragePrices(ProductPricesChartFilter filter) {
        List<PriceType> priceTypes = getPriceTypes(filter.getProductId());
        int previousYear = filter.getYear() - 1;
        return repository.findAveragePrices(previousYear, filter.getProductId(), priceTypes, filter.getMarketId());
    }

    private List<PriceType> getPriceTypes(Long productId) {
        return productRepository.findById(productId)
                .map(Product::getPriceTypes)
                .orElseGet(ImmutableList::of);
    }

    @Override
    public List<Integer> findYearsWithPrices() {
        return repository.findYearsWithPrices();
    }

    @Override
    public boolean hasPrices(Integer year, Long productId) {
        return repository.countPrices(year, productId) > 0;
    }

    @Override
    public boolean hasPricesForProductAndPriceType(Long productId, Collection<Long> priceTypeIds) {
        return repository.countPricesForProductAndPriceType(productId, priceTypeIds) > 0;
    }

    @Override
    public Long getProductIdWithPrices(Integer year) {
        List<Product> products = repository.getProductsWithPrices(year);
        return products.isEmpty() ? null : products.get(0).getId();
    }

    @Override
    public Long getMarketIdWithPrices(Integer year, Long productId) {
        List<Market> markets = repository.getMarketsWithPrices(year, productId);
        return markets.isEmpty() ? null : markets.get(0).getId();
    }
}
