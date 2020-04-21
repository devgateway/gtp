package org.devgateway.toolkit.persistence.dao.reference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class RainSeasonStartReference extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 8155903947748748740L;

    @Column(nullable = false)
    private Integer referenceYearStart;

    @Column(nullable = false)
    private Integer referenceYearEnd;

    @Column(nullable = false)
    private Integer yearStart;

    @Column(nullable = false)
    private Integer yearEnd;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rainSeasonStartReference")
    @JsonIgnore
    private List<RainSeasonPluviometricPostReferenceStart> postReferences = new ArrayList<>();

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

    public List<RainSeasonPluviometricPostReferenceStart> getPostReferences() {
        return postReferences;
    }

    public void setPostReferences(List<RainSeasonPluviometricPostReferenceStart> postReferences) {
        this.postReferences = postReferences;
    }

    public void addPostReference(RainSeasonPluviometricPostReferenceStart postReference) {
        this.postReferences.add(postReference);
        postReference.setRainSeasonStartReference(this);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
