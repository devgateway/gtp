package org.devgateway.toolkit.persistence.dao.indicator;

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
public class PluviometricPostRainSeason extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 781589350960096214L;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private PluviometricPost pluviometricPost;

    private LocalDate startDate;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private RainSeason rainSeason;

    public PluviometricPost getPluviometricPost() {
        return pluviometricPost;
    }

    public void setPluviometricPost(PluviometricPost pluviometricPost) {
        this.pluviometricPost = pluviometricPost;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public RainSeason getRainSeason() {
        return rainSeason;
    }

    public void setRainSeason(RainSeason rainSeason) {
        this.rainSeason = rainSeason;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
