package org.devgateway.toolkit.persistence.dao.reference;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPostHolder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@MappedSuperclass
public abstract class YearsReference extends AbstractAuditableEntity
        implements Serializable {
    private static final long serialVersionUID = 6854251001554496374L;

    @Column(nullable = false)
    private Integer referenceYearStart;

    @Column(nullable = false)
    private Integer referenceYearEnd;

    @Column(nullable = false)
    private Integer yearStart;

    @Column(nullable = false)
    private Integer yearEnd;

    public Integer getReferenceYearStart() {
        return referenceYearStart;
    }

    public void setReferenceYearStart(Integer referenceYearStart) {
        this.referenceYearStart = referenceYearStart;
    }

    public Integer getReferenceYearEnd() {
        return referenceYearEnd;
    }

    public void setReferenceYearEnd(Integer referenceYearEnd) {
        this.referenceYearEnd = referenceYearEnd;
    }

    public Integer getYearStart() {
        return yearStart;
    }

    public void setYearStart(Integer yearStart) {
        this.yearStart = yearStart;
    }

    public Integer getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(Integer yearEnd) {
        this.yearEnd = yearEnd;
    }

    public abstract List<? extends PluviometricPostHolder> getReferences();
}
