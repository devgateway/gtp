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
public class RainLevelReference extends YearsReference {
    private static final long serialVersionUID = -8190280665456195001L;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rainLevelReference")
    @JsonIgnore
    private List<RainLevelPluviometricPostReference> postRainReferences = new ArrayList<>();

    public List<RainLevelPluviometricPostReference> getPostRainReferences() {
        return postRainReferences;
    }

    public void setPostRainReferences(List<RainLevelPluviometricPostReference> postRainReferences) {
        this.postRainReferences = postRainReferences;
    }

    @Override
    public List<RainLevelPluviometricPostReference> getReferences() {
        return postRainReferences;
    }

    public void addPostReference(RainLevelPluviometricPostReference postReference) {
        postRainReferences.add(postReference);
        postReference.setRainLevelReference(this);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

}
