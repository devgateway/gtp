package org.devgateway.toolkit.persistence.dao.reference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class RainSeasonStartReference extends YearsReference {
    private static final long serialVersionUID = 8155903947748748740L;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rainSeasonStartReference")
    @JsonIgnore
    private List<RainSeasonPluviometricPostReferenceStart> postReferences = new ArrayList<>();

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

    @Override
    public List<RainSeasonPluviometricPostReferenceStart> getReferences() {
        return postReferences;
    }

}
