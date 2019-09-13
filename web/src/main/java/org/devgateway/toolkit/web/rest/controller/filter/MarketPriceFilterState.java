package org.devgateway.toolkit.web.rest.controller.filter;

import org.devgateway.toolkit.persistence.dao.MarketPrice;
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
                addRegionPredicates(root, cb, predicates);
                addCropPredicates(root, cb, predicates);
                addYearPredicates(root, cb, predicates);
                addMarketPredicates(root, cb, predicates);
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addCropPredicates(Root<MarketPrice> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addStringPredicates(root, cb, predicates, filter.getCrop(), Market_.CROP);
    }

    protected void addRegionPredicates(Root<MarketPrice> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addStringPredicates(root, cb, predicates, filter.getRegion(), Market_.REGION);
    }

    protected void addMarketPredicates(Root<MarketPrice> root, CriteriaBuilder cb, List<Predicate> predicates) {
        addStringPredicates(root, cb, predicates, filter.getMarket(), Market_.MARKET);
    }


    protected void addYearPredicates(Root<MarketPrice> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (filter.getYear() != null) {
            List<Predicate> yearPred = new ArrayList<>();
            for (Integer year:filter.getYear()) {
                yearPred.add(cb.equal(cb.function(YEAR, Integer.class, root.get(Market_.DATE)), year));
            }
            predicates.add(cb.or(yearPred.toArray(new Predicate[predicates.size()])));
        }
    }

}
