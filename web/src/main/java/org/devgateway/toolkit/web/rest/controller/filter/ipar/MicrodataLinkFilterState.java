package org.devgateway.toolkit.web.rest.controller.filter.ipar;

import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.persistence.dao.ipar.MicrodataLink;
// import org.devgateway.toolkit.persistence.dao.MicrodataLink_;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

/**
 * Created by Daniel Oliva
 */
public class MicrodataLinkFilterState {
    private static final long serialVersionUID = 8005371716983257722L;

    private DatasetFilterPagingRequest filter;

    public MicrodataLinkFilterState(DatasetFilterPagingRequest filter) {
        this.filter = filter;
    }

    public Specification<MicrodataLink> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter != null) {
                addDatePredicates(root, cb, predicates);
                addTextPredicates(root, cb, predicates);
                addOrganizationPredicates(root, cb, predicates);
            }
            // query.orderBy(new OrderImpl(root.get(MicrodataLink_.CREATED_DATE), false));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected void addOrganizationPredicates(Root<MicrodataLink> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (filter.getOrganization() != null && filter.getOrganization().size() > 0) {
            List<Predicate> intPred = new ArrayList<>();
            // Join<MicrodataLink, Organization> join = root.join(MicrodataLink_.ORGANIZATION, JoinType.LEFT);
            for (Integer value:filter.getOrganization()) {
                // intPred.add(cb.equal(join.get("id"), value));
            }
            predicates.add(cb.or(intPred.toArray(new Predicate[intPred.size()])));
        }
    }

    protected void addTextPredicates(Root<MicrodataLink> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (StringUtils.isNotBlank(filter.getText())) {
            String txt = filter.getText().toLowerCase();
            if (StringUtils.isNotEmpty(filter.getLang()) && filter.getLang().equalsIgnoreCase(LANG_FR)) {
                predicates.add(cb.and(cb.or(
                        // cb.like(cb.lower(root.get(MicrodataLink_.TITLE_FR)), "%" + txt + "%"),
                        // cb.like(cb.lower(root.get(MicrodataLink_.DESCRIPTION_FR)), "%" + txt + "%")
                )));
            } else {
                predicates.add(cb.and(cb.or(
                        // cb.like(cb.lower(root.get(MicrodataLink_.TITLE)), "%" + txt + "%"),
                        // cb.like(cb.lower(root.get(MicrodataLink_.DESCRIPTION)), "%" + txt + "%")
                )));
            }
        }
    }


    protected void addDatePredicates(Root<MicrodataLink> root, CriteriaBuilder cb, List<Predicate> predicates) {
        ZonedDateTime minDate = filter.getRealMinDate();
        if (minDate != null) {
            // predicates.add(cb.greaterThanOrEqualTo(root.get(MicrodataLink_.CREATED_DATE), minDate));
        }
        ZonedDateTime maxDate = filter.getRealMaxDate();
        if (filter.getMaxDate() != null) {
            // predicates.add(cb.lessThanOrEqualTo(root.get(MicrodataLink_.CREATED_DATE), maxDate));
        }
    }

}
