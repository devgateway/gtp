package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import com.google.common.collect.ImmutableList;
import org.devgateway.toolkit.persistence.dao.PersistedCollectionSize;
import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.indicator.ProductQuantity;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.dto.agriculture.AveragePrice;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductPricesChartFilter;
import org.devgateway.toolkit.persistence.dto.agriculture.ProductQuantitiesChartFilter;
import org.devgateway.toolkit.persistence.repository.category.ProductRepository;
import org.devgateway.toolkit.persistence.repository.indicator.ProductYearlyPricesRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.devgateway.toolkit.persistence.status.DataEntryStatus;
import org.devgateway.toolkit.persistence.status.ProductPriceAndAvailabilityProgress;
import org.devgateway.toolkit.persistence.status.ProductTypeYearStatus;
import org.devgateway.toolkit.persistence.service.category.MarketService;
import org.devgateway.toolkit.persistence.service.category.ProductService;
import org.devgateway.toolkit.persistence.service.category.ProductTypeService;
import org.devgateway.toolkit.persistence.time.AD3Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Month;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

    @Autowired
    private MarketService marketService;

    @Autowired
    private ProductService productService;

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

        Map<Long, Long> priceSizeById = repository.getPriceSizes(ids).stream()
                .collect(toMap(PersistedCollectionSize::getId, PersistedCollectionSize::getSize));

        Map<Long, Long> quantitySizeById = repository.getQuantitySizes(ids).stream()
                .collect(toMap(PersistedCollectionSize::getId, PersistedCollectionSize::getSize));

        page.get().forEach(p -> {
            p.setPricesSize(priceSizeById.getOrDefault(p.getId(), 0L));
            p.setQuantitiesSize(quantitySizeById.getOrDefault(p.getId(), 0L));
        });

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
    public List<Integer> findYearsWithQuantities() {
        return repository.findYearsWithQuantities();
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

    public ProductYearlyPrices getExample(Integer year, ProductType productType) {
        List<Product> products = productService.findByProductType(productType);

        ProductYearlyPrices prices = new ProductYearlyPrices(year, productType);

        getExamplePrices(products).forEach(prices::addPrice);

        return prices;
    }

    @Override
    @Transactional(readOnly = true)
    public void export(ProductYearlyPrices entity, OutputStream outputStream) throws IOException {
        List<Product> products = productService.findByProductType(entity.getProductType());

        boolean productsOnSeparateRows = entity.getProductType().areProductsOnSeparateRows();

        ProductPriceWriter writer = new ProductPriceWriter(products, productsOnSeparateRows);

        writer.write(entity.getPrices(), entity.getQuantities(), entity.getYear(), outputStream);
    }

    @Override
    public Long getMarketIdWithQuantities(Integer year, Long productTypeId) {
        List<Market> markets = repository.getMarketsWithQuantities(year, productTypeId);
        return markets.isEmpty() ? null : markets.get(0).getId();
    }

    @Override
    public Long getProductTypeIdWithQuantities(Integer year) {
        List<ProductType> types = repository.getProductTypesWithQuantities(year);
        return types.isEmpty() ? null : types.get(0).getId();
    }

    @Override
    public List<ProductQuantity> findQuantities(ProductQuantitiesChartFilter filter) {
        return repository.findQuantities(filter.getYear(), filter.getProductTypeId(), filter.getMarketId());
    }

    @Override
    public ProductPriceAndAvailabilityProgress getProgress(Integer year) {
        List<ProductType> productTypes = productTypeService.findAll();

        List<ProductTypeYearStatus> productTypeStatuses = new ArrayList<>();

        YearMonth now = YearMonth.now(AD3Clock.systemDefaultZone());

        for (ProductType productType : productTypes) {
            Set<Month> monthsWithData = repository.findMonthDaysWithPricesByYearAndProductType(year, productType)
                    .stream()
                    .map(MonthDay::getMonth)
                    .collect(Collectors.toSet());

            Map<Month, DataEntryStatus> statusByMonth = new HashMap<>();
            for (Month month : Month.values()) {
                DataEntryStatus status;
                if (monthsWithData.contains(month)) {
                    status = DataEntryStatus.PUBLISHED;
                } else if (YearMonth.of(year, month).isBefore(now)) {
                    status = DataEntryStatus.NO_DATA;
                } else {
                    status = DataEntryStatus.NOT_APPLICABLE;
                }
                statusByMonth.put(month, status);
            }
            productTypeStatuses.add(new ProductTypeYearStatus(productType, statusByMonth));
        }

        return new ProductPriceAndAvailabilityProgress(productTypeStatuses);
    }

    private SortedSet<ProductPrice> getExamplePrices(List<Product> products) {
        Product product = products.get(0);
        PriceType priceType = product.getPriceTypes().get(0);
        MonthDay monthDay = MonthDay.of(Month.JANUARY, 1);

        List<Market> markets = marketService.findByMarketType(product.getProductType().getMarketType());

        return markets.stream()
                .map(m -> new ProductPrice(product, m, monthDay, priceType, null))
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
