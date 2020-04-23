package org.devgateway.toolkit.persistence.dao.reference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPostHolder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class RainLevelPluviometricPostReference extends AbstractAuditableEntity implements Serializable,
        PluviometricPostHolder {
    private static final long serialVersionUID = 2349382633920882594L;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rainLevelPluviometricPostReference")
    @JsonIgnore
    private List<DecadalRainLevelReference> decadalRainReferences = new ArrayList<>();

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private PluviometricPost pluviometricPost;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private RainLevelReference rainLevelReference;

    public List<DecadalRainLevelReference> getDecadalRainReferences() {
        return decadalRainReferences;
    }

    public void setDecadalRainReferences(List<DecadalRainLevelReference> decadalRainReferences) {
        this.decadalRainReferences = decadalRainReferences;
    }

    public void addDecadalRainReference(DecadalRainLevelReference decadalRainLevelReference) {
        this.decadalRainReferences.add(decadalRainLevelReference);
        decadalRainLevelReference.setRainLevelPluviometricPostReference(this);
    }

    @Override
    public PluviometricPost getPluviometricPost() {
        return pluviometricPost;
    }

    public void setPluviometricPost(PluviometricPost pluviometricPost) {
        this.pluviometricPost = pluviometricPost;
    }

    public RainLevelReference getRainLevelReference() {
        return rainLevelReference;
    }

    public void setRainLevelReference(RainLevelReference rainLevelReference) {
        this.rainLevelReference = rainLevelReference;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
