package org.devgateway.toolkit.forms.util;

import org.devgateway.toolkit.persistence.dao.categories.ProductType;

/**
 * @author Octavian Ciubotaru
 */
public final class ProductTypeUtil {

    private ProductTypeUtil() {
    }

    public static boolean areProductsOnSeparateRows(ProductType productType) {
        String productTypeName = productType.getName();
        return productTypeName.equals(ProductType.FRESH_FISH)
                || productTypeName.equals(ProductType.PROCESSED_FISH);
    }
}
