package org.devgateway.toolkit.web.rest.controller.filter;

import org.devgateway.toolkit.persistence.dao.Consumption;
import org.devgateway.toolkit.persistence.dao.Consumption_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class ConsumptionFilterState extends DataFilterState<Consumption> {
    private static final long serialVersionUID = 8005371716983257722L;

    private ConsumptionFilterPagingRequest filter;

    public ConsumptionFilterState(ConsumptionFilterPagingRequest filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public Specification<Consumption> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addDataIdPredicates(root, cb, predicates);
                addDatasetIdPredicates(root, cb, predicates);
                addDeparmentPredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addCropTypePredicates(root, cb, predicates);
                addCropSubTypePredicates(root, cb, predicates);
            }
            addApprovedDatasets(root, cb, predicates);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addDeparmentPredicates(Root<Consumption> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getRegion(), Consumption_.DEPARTMENT); // TODO fix type mismatch
    }

    protected void addCropSubTypePredicates(Root<Consumption> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getCropSubType(), Consumption_.CROP_SUB_TYPE);
        // TODO fix type mismatch
    }
}
