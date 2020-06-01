package org.devgateway.toolkit.persistence.service.testdata;

import static org.devgateway.toolkit.persistence.dao.categories.MarketType.PRODUCT_TYPES_BY_MARKET_TYPE;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.devgateway.toolkit.persistence.dao.categories.Market;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType;
import org.devgateway.toolkit.persistence.dao.indicator.ProductPrice;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.repository.indicator.ProductYearlyPricesRepository;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.category.MarketService;
import org.devgateway.toolkit.persistence.service.category.ProductService;
import org.devgateway.toolkit.persistence.service.category.ProductTypeService;
import org.devgateway.toolkit.persistence.service.indicator.ProductYearlyPricesService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Octavian Ciubotaru
 */
@Component
@ConditionalOnProperty("generate-product-prices-test-data")
public class ProductPricesTestDataGenerator implements InitializingBean {

    public static final int WEEKS_IN_YEAR = 52;
    public static final int MAX_PRICE = 10000;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private ProductYearlyPricesRepository productYearlyPricesRepository;

    @Autowired
    private ProductYearlyPricesService productYearlyPricesService;

    @Autowired
    private AdminSettingsService adminSettingsService;

    @Override
    public void afterPropertiesSet() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(s -> {
            productYearlyPricesRepository.deleteAll();

            return null;
        });

        transactionTemplate.execute(s -> {
            List<ProductType> productTypes = productTypeService.findAll();
            List<Integer> years = adminSettingsService.getYears();

            for (ProductType productType : productTypes) {
                for (Integer year : years) {

                    ProductYearlyPrices productYearlyPrices = new ProductYearlyPrices();
                    productYearlyPrices.setProductType(productType);
                    productYearlyPrices.setYear(year);

                    List<Product> products = productService.findAll().stream()
                            .filter(p -> p.getProductType().equals(productType))
                            .collect(Collectors.toList());
                    List<Market> markets = marketService.findAll();

                    for (Market market : markets) {
                        String marketTypeName = market.getType().getName();
                        for (int i = 0; i < WEEKS_IN_YEAR; i++) {
                            MonthDay monthDay = randomMonthDay(year);
                            for (Product product : products) {
                                String productTypeName = product.getProductType().getName();
                                if (PRODUCT_TYPES_BY_MARKET_TYPE.get(marketTypeName).contains(productTypeName)) {
                                    for (PriceType priceType : product.getPriceTypes()) {
                                        if (ThreadLocalRandom.current().nextDouble() > 0.6) {
                                            ProductPrice price = new ProductPrice();
                                            price.setMarket(market);
                                            price.setProduct(product);
                                            price.setPriceType(priceType);
                                            price.setMonthDay(monthDay);
                                            price.setPrice(ThreadLocalRandom.current().nextInt(MAX_PRICE));

                                            productYearlyPrices.addPrice(price);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    productYearlyPricesService.saveAndFlush(productYearlyPrices);
                }
            }

            return null;
        });
    }

    private MonthDay randomMonthDay(Integer year) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        Month month = Month.of(1 + rnd.nextInt(Month.values().length));
        return MonthDay.of(month, 1 + rnd.nextInt(month.length(Year.isLeap(year))));
    }
}
