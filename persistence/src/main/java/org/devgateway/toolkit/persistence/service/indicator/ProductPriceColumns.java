package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toList;
import static org.devgateway.toolkit.persistence.service.indicator.ProductPriceWriter.DATE_COL_IDX;
import static org.devgateway.toolkit.persistence.service.indicator.ProductPriceWriter.PRODUCT_COL_IDX;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.devgateway.toolkit.persistence.dao.categories.PriceType;
import org.devgateway.toolkit.persistence.dao.categories.Product;

/**
 * @author Octavian Ciubotaru
 */
public class ProductPriceColumns {

    private final boolean productsOnSeparateRows;
    private final Map<PriceType, Integer> priceTypeColIdx;
    private final Map<Product, Map<PriceType, Integer>> priceColIdx;
    private final Map<Product, Integer> quantityColIdx;

    public ProductPriceColumns(boolean productsOnSeparateRows) {
        this(productsOnSeparateRows, ImmutableList.of());
    }

    public ProductPriceColumns(boolean productsOnSeparateRows, List<Product> products) {
        this.productsOnSeparateRows = productsOnSeparateRows;
        if (productsOnSeparateRows) {
            priceTypeColIdx = new HashMap<>();
            priceColIdx = null;
            quantityColIdx = null;

            List<PriceType> priceTypes = products.stream()
                    .flatMap(p -> p.getPriceTypes().stream())
                    .distinct()
                    .sorted()
                    .collect(toList());

            for (int i = 0; i < priceTypes.size(); i++) {
                int col = PRODUCT_COL_IDX + 1 + i;
                priceTypeColIdx.put(priceTypes.get(i), col);
            }
        } else {
            priceTypeColIdx = null;
            priceColIdx = new HashMap<>();
            quantityColIdx = new HashMap<>();

            int col = DATE_COL_IDX + 1;
            for (Product product : products) {
                quantityColIdx.put(product, col++);

                List<PriceType> priceTypes = new ArrayList<>(product.getPriceTypes());
                Collections.sort(priceTypes);
                for (PriceType priceType : priceTypes) {
                    priceColIdx.computeIfAbsent(product, p -> new HashMap<>()).put(priceType, col++);
                }
            }
        }
    }

    public static String getPriceTypeColumnName(PriceType priceType) {
        return priceType.getLabel();
    }

    public static String getQuantityColumnName(Product product) {
        return product.getName() + " - quantité (" + product.getUnit().getLabel() + ")";
    }

    public static String getProductAndPriceTypeColumnName(Product product, PriceType priceType) {
        return product.getName() + " - " + priceType.getLabel();
    }

    public void addQuantityCol(Product product, int col) {
        if (productsOnSeparateRows) {
            throw new IllegalStateException();
        } else {
            if (getQuantityCol(product) != null) {
                throw new DuplicateElementException();
            }
            quantityColIdx.put(product, col);
        }
    }

    public void addPriceCol(Product product, PriceType priceType, int col) {
        if (productsOnSeparateRows) {
            throw new IllegalStateException();
        } else {
            if (getProductAndPriceTypeCol(product, priceType) != null) {
                throw new DuplicateElementException();
            }
            priceColIdx.computeIfAbsent(product, p -> new HashMap<>()).put(priceType, col);
        }
    }

    public void addPriceTypeCol(PriceType priceType, int col) {
        if (productsOnSeparateRows) {
            if (getPriceTypeCol(priceType) != null) {
                throw new DuplicateElementException();
            }
            priceTypeColIdx.put(priceType, col);
        } else {
            throw new IllegalStateException();
        }
    }

    public Integer getPriceTypeCol(PriceType priceType) {
        return priceTypeColIdx.get(priceType);
    }

    public Integer getQuantityCol(Product product) {
        return quantityColIdx.get(product);
    }

    public Integer getProductAndPriceTypeCol(Product product, PriceType priceType) {
        return priceColIdx.getOrDefault(product, ImmutableMap.of()).get(priceType);
    }

    public int getLastColumn() {
        Stream<Integer> s;
        if (productsOnSeparateRows) {
            s = priceTypeColIdx.values().stream();
        } else {
            s = Stream.concat(
                    quantityColIdx.values().stream(),
                    priceColIdx.values().stream().flatMap(m -> m.values().stream()));
        }
        return s.mapToInt(o -> o)
                .max()
                .orElseThrow(() -> new IllegalStateException("No columns"));
    }
}
