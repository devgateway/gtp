package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.forms.wicket.FormsWebApplication;
import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport;
import org.devgateway.toolkit.persistence.dao.indicator.AnnualGTPReport_;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class AnnualGTPReportFilterState extends JpaFilterState<AnnualGTPReport> {
    private static final long serialVersionUID = 5353159707010622294L;

    private Long departmentId;

    @Override
    public Specification<AnnualGTPReport> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (departmentId == null) {
                predicates.add(cb.isNull(root.get(AnnualGTPReport_.department)));
            } else {
                predicates.add(cb.equal(root.get(AnnualGTPReport_.department), departmentId));
            }

            AdminSettingsService adminSettingsService = FormsWebApplication.getBean(AdminSettingsService.class);
            predicates.add(cb.ge(root.get(AnnualGTPReport_.year), adminSettingsService.getStartingYear()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
