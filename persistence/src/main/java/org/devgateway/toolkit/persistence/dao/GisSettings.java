package org.devgateway.toolkit.persistence.dao;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GisSettings  extends AbstractAuditableEntity implements Serializable {

    private static final long serialVersionUID = -1051140524022133178L;

    private String rightGisName;
    private String leftGisName;

    public String getRightGisName() {
        return rightGisName;
    }

    public void setRightGisName(String rightGisName) {
        this.rightGisName = rightGisName;
    }

    public String getLeftGisName() {
        return leftGisName;
    }

    public void setLeftGisName(String leftGisName) {
        this.leftGisName = leftGisName;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
