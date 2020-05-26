package org.devgateway.toolkit.forms.wicket.components.table.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.devgateway.toolkit.persistence.dao.categories.Product;
import org.devgateway.toolkit.persistence.dao.categories.ProductType_;
import org.devgateway.toolkit.persistence.dao.categories.Product_;
import org.devgateway.toolkit.persistence.service.SQLUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Octavian Ciubotaru
 */
public class ProductFilterState extends JpaFilterState<Product> {

    private String productType;

    private String name;

    @Override
    public Specification<Product> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (productType != null) {
                predicates.add(cb.equal(root.get(Product_.productType)
                        .get(ProductType_.label), productType));
            }

            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get(Product_.name)),
                        SQLUtils.likeContainsPattern(name.toLowerCase())));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
