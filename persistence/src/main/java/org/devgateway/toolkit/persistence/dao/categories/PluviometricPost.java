package org.devgateway.toolkit.persistence.dao.categories;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;
import org.devgateway.toolkit.persistence.validator.SenegalLatitude;
import org.devgateway.toolkit.persistence.validator.SenegalLongitude;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
@BatchSize(size = 100)
public class PluviometricPost extends AbstractAuditableEntity implements Comparable<PluviometricPost>, Serializable,
        Labelable {
    private static final long serialVersionUID = -7372273374563439000L;

    // label is unique; cannot use ::toLabel (the overloaded method will be soon deleted), ::toString does the same
    private static final Comparator<PluviometricPost> NATURAL = Comparator.comparing(PluviometricPost::toString);

    @ExcelExport(name = "department", justExport = true, useTranslation = true)
    @NotNull
    @ManyToOne(optional = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("departmentId")
    private Department department;

    @ExcelExport(name = "pluviometricPost", useTranslation = true)
    @NotNull
    @Column(nullable = false, unique = true)
    private String label;

    @ExcelExport(name = "latitude", useTranslation = true)
    @NotNull @SenegalLatitude
    private Double latitude;

    @ExcelExport(name = "longitude", useTranslation = true)
    @NotNull @SenegalLongitude
    private Double longitude;

    public PluviometricPost() {
    }

    public PluviometricPost(Long id) {
        setId(id);
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getLabel(String lang) {
        return label;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return getLabel();
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public int compareTo(PluviometricPost pp) {
        return NATURAL.compare(this, pp);
    }
}
