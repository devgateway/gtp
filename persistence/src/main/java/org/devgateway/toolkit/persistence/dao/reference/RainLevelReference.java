package org.devgateway.toolkit.persistence.dao.reference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.AbstractImportableEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

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
public class RainLevelReference extends YearsReference implements AbstractImportableEntity {
    private static final long serialVersionUID = -8190280665456195001L;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rainLevelReference")
    @JsonIgnore
    private List<RainLevelPluviometricPostReference> postRainReferences = new ArrayList<>();

    public List<RainLevelPluviometricPostReference> getPostRainReferences() {
        return postRainReferences;
    }

    @Formula("(select count(mr.*) from rain_level_month_reference mr "
            + "join rain_level_pluviometric_post_reference rlppr on "
            + "rlppr.id=mr.rain_level_pluviometric_post_reference_id "
            + "where rlppr.rain_level_reference_id=id "
            + "and mr.rain is not null)")
    @NotAudited
    private Long quantitiesSize;

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

    public Long getQuantitiesSize() {
        return quantitiesSize;
    }

    public void setQuantitiesSize(Long quantitiesSize) {
        this.quantitiesSize = quantitiesSize;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return getQuantitiesSize() == 0;
    }
}
