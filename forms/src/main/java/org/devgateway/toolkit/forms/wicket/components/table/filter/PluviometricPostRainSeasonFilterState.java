package org.devgateway.toolkit.forms.wicket.components.table.filter;

import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost_;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason;
import org.devgateway.toolkit.persistence.dao.indicator.PluviometricPostRainSeason_;
import org.devgateway.toolkit.persistence.dao.indicator.RainSeason_;
import org.devgateway.toolkit.persistence.dao.location.Department_;
import org.devgateway.toolkit.persistence.dao.location.Region_;
import org.devgateway.toolkit.persistence.dao.location.Zone_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
public class PluviometricPostRainSeasonFilterState extends JpaFilterState<PluviometricPostRainSeason> {
    private static final long serialVersionUID = 8682732798156199922L;

    private Integer year;

    private String zone;
    private String region;
    private String department;

    private String pluviometricPost;

    @Override
    public Specification<PluviometricPostRainSeason> getSpecification() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get(PluviometricPostRainSeason_.rainSeason).get(RainSeason_.year), year));
            predicates.add(cb.notEqual(root.get(PluviometricPostRainSeason_.deleted), true));

            if (zone != null) {
                predicates.add(cb.equal(root.get(PluviometricPostRainSeason_.pluviometricPost)
                        .get(PluviometricPost_.department)
                        .get(Department_.region)
                        .get(Region_.zone)
                        .get(Zone_.name), zone));
            }

            if (region != null) {
                predicates.add(cb.equal(root.get(PluviometricPostRainSeason_.pluviometricPost)
                        .get(PluviometricPost_.department)
                        .get(Department_.region)
                        .get(Region_.name), region));
            }

            if (department != null) {
                predicates.add(cb.equal(root.get(PluviometricPostRainSeason_.pluviometricPost)
                        .get(PluviometricPost_.department).get(Department_.name), department));
            }

            if (pluviometricPost != null) {
                predicates.add(cb.equal(root.get(PluviometricPostRainSeason_.pluviometricPost)
                                .get(PluviometricPost_.label), pluviometricPost));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPluviometricPost() {
        return pluviometricPost;
    }

    public void setPluviometricPost(String pluviometricPost) {
        this.pluviometricPost = pluviometricPost;
    }
}
