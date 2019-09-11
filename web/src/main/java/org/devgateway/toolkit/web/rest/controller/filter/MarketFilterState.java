package org.devgateway.toolkit.web.rest.controller.filter;

import org.devgateway.toolkit.persistence.dao.Data_;
import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.dao.Market_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class MarketFilterState extends DataFilterState<Market> {
    private static final long serialVersionUID = 8005371716983257722L;

    private MarketFilterPagingRequest filter;

    public MarketFilterState(MarketFilterPagingRequest filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public Specification<Market> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addRegionPredicates(root, cb, predicates);
                addCropPredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addMarketPredicates(root, cb, predicates);
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addMarketPredicates(Root<Market> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addStringPredicates(root, cb, predicates, filter.getMarket(), Market_.MARKET);
    }


}
