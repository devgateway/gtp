package org.devgateway.toolkit.web.rest.controller.filter.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Production;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class ProductionFilterState extends DataFilterState<Production> {
    private static final long serialVersionUID = 8005371716983257722L;

    private ProductionFilterPagingRequest filter;

    public ProductionFilterState(ProductionFilterPagingRequest filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public Specification<Production> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addDataIdPredicates(root, cb, predicates);
                addDatasetIdPredicates(root, cb, predicates);
                addRegionPredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addCropTypePredicates(root, cb, predicates);
            }
            addApprovedDatasets(root, cb, predicates);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
