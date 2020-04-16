package org.devgateway.toolkit.persistence.dao.reference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class RainSeasonPluviometricPostReferenceStart extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 3862531669881681345L;

    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

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