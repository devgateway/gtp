package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class RainSeason extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 2478095496695101726L;

    @NotNull
    @Column(nullable = false)
    private Integer year;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rainSeason")
    @JsonIgnore
    private List<PluviometricPostRainSeason> postRainSeason = new ArrayList<>();

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<PluviometricPostRainSeason> getPostRainSeason() {
        return postRainSeason;
    }

    public void setPostRainSeason(List<PluviometricPostRainSeason> postRainSeason) {
        this.postRainSeason = postRainSeason;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
