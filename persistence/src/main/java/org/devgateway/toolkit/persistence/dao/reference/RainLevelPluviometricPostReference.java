package org.devgateway.toolkit.persistence.dao.reference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Decadal;
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
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<RainLevelMonthReference> rainLevelMonthReferences = new ArrayList<>();

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

    private transient Map<Month, Map<Decadal, RainLevelMonthReference>> month2Reference;

    public List<RainLevelMonthReference> getRainLevelMonthReferences() {
        return rainLevelMonthReferences;
    }

    public void setRainLevelMonthReferences(List<RainLevelMonthReference> rainLevelMonthReferences) {
        this.rainLevelMonthReferences = rainLevelMonthReferences;
    }

    public void addRainLevelMonthReference(RainLevelMonthReference rainLevelMonthReference) {
        this.rainLevelMonthReferences.add(rainLevelMonthReference);
        rainLevelMonthReference.setRainLevelPluviometricPostReference(this);
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

    public Map<Decadal, RainLevelMonthReference> getMonthReference(Month month) {
        if (month2Reference == null) {
            month2Reference = new HashMap<>();
        }
        if (month2Reference.isEmpty()) {
            getRainLevelMonthReferences().forEach(reference -> {
                Map<Decadal, RainLevelMonthReference> decadalRefs =
                        month2Reference.computeIfAbsent(reference.getMonth(), m -> new HashMap<>());
                decadalRefs.put(reference.getDecadal(), reference);
            });
        }
        return month2Reference.get(month);
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
