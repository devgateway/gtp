package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class RainfallMap extends AbstractAuditableEntity {
    private static final long serialVersionUID = -4289191210184144287L;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<FileMetadata> polyline = new HashSet<>();

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<FileMetadata> polygon = new HashSet<>();

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToOne(fetch = FetchType.LAZY)
    private DecadalRainfallMap decadalRainfallMap;

    public Set<FileMetadata> getPolyline() {
        return polyline;
    }

    public void setPolyline(Set<FileMetadata> polyline) {
        this.polyline = polyline;
    }

    public FileMetadata getPolylineSingle() {
        return polyline.isEmpty() ? null : polyline.iterator().next();
    }

    public Set<FileMetadata> getPolygon() {
        return polygon;
    }

    public void setPolygon(Set<FileMetadata> polygon) {
        this.polygon = polygon;
    }

    public FileMetadata getPolygonSingle() {
        return polygon.isEmpty() ? null : polygon.iterator().next();
    }

    public DecadalRainfallMap getDecadalRainfallMap() {
        return decadalRainfallMap;
    }

    public void setDecadalRainfallMap(DecadalRainfallMap decadalRainfallMap) {
        this.decadalRainfallMap = decadalRainfallMap;
    }

    public boolean isEmpty() {
        return getPolylineSingle() == null && getPolygonSingle() == null;
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
