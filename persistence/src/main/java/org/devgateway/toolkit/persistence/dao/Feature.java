package org.devgateway.toolkit.persistence.dao;

import static java.util.Comparator.comparing;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Comparator;

/**
 * @author Octavian Ciubotaru
 */
@Entity
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Feature extends AbstractAuditableEntity implements Comparable<Feature> {

    private static final Comparator<Feature> NATURAL = comparing(Feature::getOrderInHierarchy);

    @ManyToOne
    private Feature parent;

    @NotNull
    private String name;

    @NotNull
    private String label;

    @NotNull
    private Boolean enabled;

    @NotNull
    @Column(name = "item_order")
    private Integer order;

    private transient String orderInHierarchy;

    @Override
    public Feature getParent() {
        return parent;
    }

    public void setParent(Feature parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean isEnabledInHierarchy() {
        return (parent == null || parent.isEnabledInHierarchy()) && enabled;
    }

    public String getOrderInHierarchy() {
        if (orderInHierarchy == null) {
            orderInHierarchy = parent == null
                    ? order.toString()
                    : parent.getOrderInHierarchy() + "-" + order.toString();
        }
        return orderInHierarchy;
    }

    public int getDepth() {
        return parent == null ? 0 : parent.getDepth() + 1;
    }

    @Override
    public int compareTo(Feature o) {
        return NATURAL.compare(this, o);
    }
}
