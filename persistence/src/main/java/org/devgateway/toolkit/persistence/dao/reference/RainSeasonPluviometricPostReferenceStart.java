package org.devgateway.toolkit.persistence.dao.reference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPostHolder;
import org.devgateway.toolkit.persistence.dao.converter.MonthDayStringAttributeConverter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.MonthDay;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class RainSeasonPluviometricPostReferenceStart extends AbstractAuditableEntity implements Serializable,
        PluviometricPostHolder {
    private static final long serialVersionUID = 3862531669881681345L;

    @Column(columnDefinition = "varchar", length = 6)
    @Convert(converter = MonthDayStringAttributeConverter.class)
    private MonthDay startReference;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private PluviometricPost pluviometricPost;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private RainSeasonStartReference rainSeasonStartReference;

    public MonthDay getStartReference() {
        return startReference;
    }

    public void setStartReference(MonthDay startReference) {
        this.startReference = startReference;
    }

    @Override
    public PluviometricPost getPluviometricPost() {
        return pluviometricPost;
    }

    public void setPluviometricPost(PluviometricPost pluviometricPost) {
        this.pluviometricPost = pluviometricPost;
    }

    public RainSeasonStartReference getRainSeasonStartReference() {
        return rainSeasonStartReference;
    }

    public void setRainSeasonStartReference(RainSeasonStartReference rainSeasonStartReference) {
        this.rainSeasonStartReference = rainSeasonStartReference;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
