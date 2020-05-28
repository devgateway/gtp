package org.devgateway.toolkit.forms.wicket.components.table.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType_;
import org.devgateway.toolkit.persistence.dao.categories.Product_;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Octavian Ciubotaru
 */
public class ProductFilterState extends JpaFilterState<Product> {

    private List<String> productTypes = new ArrayList<>();

    private List<String> products = new ArrayList<>();

    @Override
    public Specification<Product> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!productTypes.isEmpty()) {
                predicates.add(root.get(Product_.productType).get(ProductType_.label).in(productTypes));
            }

            if (!products.isEmpty()) {
                predicates.add(root.get(Product_.name).in(products));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public List<String> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<String> productTypes) {
        this.productTypes = productTypes;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}
