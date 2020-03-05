package org.devgateway.toolkit.persistence.dao;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import java.io.Serializable;
import java.util.List;

@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GisSettings extends AbstractAuditableEntity implements Serializable {

    private static final long serialVersionUID = -1051140524022133178L;

    private String rightGisName;
    private String leftGisName;

    private String rightGisDepartmentName;
    private String leftGisDepartmentName;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "gisSettings", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderColumn(name = "index")
    private List<GisSettingsDescription> descriptions;

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

    public String getRightGisDepartmentName() {
        return rightGisDepartmentName;
    }

    public void setRightGisDepartmentName(String rightGisDepartmentName) {
        this.rightGisDepartmentName = rightGisDepartmentName;
    }

    public String getLeftGisDepartmentName() {
        return leftGisDepartmentName;
    }

    public void setLeftGisDepartmentName(String leftGisDepartmentName) {
        this.leftGisDepartmentName = leftGisDepartmentName;
    }

    public List<GisSettingsDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<GisSettingsDescription> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
