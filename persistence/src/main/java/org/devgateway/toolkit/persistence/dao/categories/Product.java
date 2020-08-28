package org.devgateway.toolkit.persistence.dao.categories;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.Labelable;
import org.devgateway.toolkit.persistence.excel.annotation.ExcelExport;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@BatchSize(size = 100)
@JsonIgnoreProperties({"new"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"product_type_id", "name"}))
public class Product extends AbstractAuditableEntity implements Comparable<Product>, Labelable {
    private static final long serialVersionUID = 3176446575063348309L;

    private static final Comparator<Product> NATURAL = Comparator.comparing(Product::getName);

    @ExcelExport(name = "productType", justExport = true, useTranslation = true)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("productTypeId")
    private ProductType productType;

    @ExcelExport(name = "productName", useTranslation = true)
    @NotNull
    private String name;

    @ExcelExport(name = "productUnit", justExport = true, useTranslation = true)
    @NotNull
    @ManyToOne(optional = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "label")
    @JsonIdentityReference(alwaysAsId = true)
    private MeasurementUnit unit;

    @NotNull @NotEmpty
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @BatchSize(size = 100)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("priceTypeIds")
    private List<PriceType> priceTypes = new ArrayList<>();

    public Product() {
    }

    public Product(Long id, ProductType productType, String name, MeasurementUnit unit, List<PriceType> priceTypes) {
        setId(id);
        this.productType = productType;
        this.name = name;
        this.unit = unit;
        this.priceTypes = priceTypes;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MeasurementUnit getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnit unit) {
        this.unit = unit;
    }

    public List<PriceType> getPriceTypes() {
        return priceTypes;
    }

    public void setPriceTypes(List<PriceType> priceTypes) {
        this.priceTypes = priceTypes;
    }

    @Override
    @JsonIgnore
    public AbstractAuditableEntity getParent() {
        return null;
    }

    @Override
    public int compareTo(Product o) {
        return NATURAL.compare(this, o);
    }

    @Override
    public void setLabel(String label) {
        name = label;
    }

    @Override
    @JsonIgnore
    public String getLabel() {
        return name;
    }

    @Override
    public String getLabel(String lang) {
        return name;
    }
}
