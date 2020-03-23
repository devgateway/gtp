package org.devgateway.toolkit.web.rest.controller.filter.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.MarketPrice;
import org.devgateway.toolkit.persistence.dao.MarketPrice_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
public class MarketPriceFilterState extends DataFilterState<MarketPrice> {
    private static final long serialVersionUID = 8005371716983257722L;

    private MarketFilterPagingRequest filter;

    public MarketPriceFilterState(MarketFilterPagingRequest filter) {
        super(filter);
        this.filter = filter;
    }

    @Override
    public Specification<MarketPrice> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addDataIdPredicates(root, cb, predicates);
                addDatasetIdPredicates(root, cb, predicates);
                addCropTypePredicates(root, cb, predicates);
                addYearDatePredicates(root, cb, predicates, MarketPrice_.DATE);
                addMarketPredicates(root, cb, predicates);
            }
            addApprovedDatasets(root, cb, predicates);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    protected void addMarketPredicates(Root<MarketPrice> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addIntPredicates(root, cb, predicates, filter.getMarket(), MarketPrice_.MARKET);
    }

}
