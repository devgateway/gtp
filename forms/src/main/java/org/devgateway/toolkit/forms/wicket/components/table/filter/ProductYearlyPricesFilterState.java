package org.devgateway.toolkit.forms.wicket.components.table.filter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices_;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Octavian Ciubotaru
 */
public class ProductYearlyPricesFilterState extends JpaFilterState<ProductYearlyPrices> {

    private Integer year;

    @Override
    public Specification<ProductYearlyPrices> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get(ProductYearlyPrices_.year), year));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
