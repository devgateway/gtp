package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public abstract class DiseaseYearlySituationFilterState extends JpaFilterState<DiseaseYearlySituation> {
    private static final long serialVersionUID = 7491799448530636815L;

    @Override
    public Specification<DiseaseYearlySituation> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // AdminSettingsService adminSettingsService = FormsWebApplication.getBean(AdminSettingsService.class);
            // predicates.add(cb.ge(root.get(DiseaseYearlySituation_.year), adminSettingsService.getStartingYear()));

            predicates.add(cb.ge(root.get(DiseaseYearlySituation_.year), getStartingYear()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    protected abstract Integer getStartingYear();
}
