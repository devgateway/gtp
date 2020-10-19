package org.devgateway.toolkit.persistence.dao.reference;

import static org.devgateway.toolkit.persistence.dao.DBConstants.MONTHS;
import static org.devgateway.toolkit.persistence.dao.DBConstants.RAIN_SEASON_DECADAL_COUNT;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
@Table(uniqueConstraints = @UniqueConstraint(name = "u_rain_level_pp_ref",
        columnNames = {"rain_level_reference_id", "pluviometric_post_id"}))
public class RainLevelPluviometricPostReference extends AbstractAuditableEntity implements Serializable,
        PluviometricPostHolder, Comparable<RainLevelPluviometricPostReference> {
    private static final long serialVersionUID = 2349382633920882594L;

    private static final Comparator<RainLevelPluviometricPostReference> NATURAL = Comparator.comparing(
            RainLevelPluviometricPostReference::getPluviometricPost);

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

    private transient boolean isValid = true;

    public RainLevelPluviometricPostReference() {
    }

    public RainLevelPluviometricPostReference(PluviometricPost pluviometricPost) {
        this.pluviometricPost = pluviometricPost;
    }

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

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean validate() {
        int countAscending = 0;
        int countValues = 0;
        if (!getRainLevelMonthReferences().isEmpty()) {
            Double pastValue = null;
            for (Month month : MONTHS) {
                Map<Decadal, RainLevelMonthReference> monthRef = getMonthReference(month);
                if (monthRef == null) {
                    continue;
                }
                for (Decadal decadal : Decadal.values()) {
                    RainLevelMonthReference decRef = monthRef.get(decadal);
                    Double rain = decRef == null ? null : decRef.getRain();
                    if (rain == null) {
                        continue;
                    }
                    countValues++;
                    if ((pastValue == null || pastValue <= rain)) {
                        countAscending++;
                    }
                    pastValue = rain;
                }
            }
        }
        isValid = countValues == 0 || countValues == countAscending && countValues == RAIN_SEASON_DECADAL_COUNT;
        return isValid;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public int compareTo(RainLevelPluviometricPostReference rppr) {
        return NATURAL.compare(this, rppr);
    }
}
