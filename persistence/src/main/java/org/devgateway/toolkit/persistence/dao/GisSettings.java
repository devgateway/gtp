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

    private String rightNationalName;
    private String leftNationalName;

    //Region
    private String rightGisRegionName;
    private String leftGisRegionName;

    private String rightGisDepartmentName;
    private String leftGisDepartmentName;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "gisSettings", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderColumn(name = "index")
    private List<GisSettingsDescription> descriptions;

    public String getRightNationalName() {
        return rightNationalName;
    }

    public void setRightNationalName(String rightNationalName) {
        this.rightNationalName = rightNationalName;
    }

    public String getLeftNationalName() {
        return leftNationalName;
    }

    public void setLeftNationalName(String leftNationalName) {
        this.leftNationalName = leftNationalName;
    }

    public String getRightGisRegionName() {
        return rightGisRegionName;
    }

    public void setRightGisRegionName(String rightGisName) {
        this.rightGisRegionName = rightGisName;
    }

    public String getLeftGisRegionName() {
        return leftGisRegionName;
    }

    public void setLeftGisRegionName(String leftGisName) {
        this.leftGisRegionName = leftGisName;
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
