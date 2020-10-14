package org.devgateway.toolkit.persistence.dao.indicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Audited
public class RainfallMapLayer extends AbstractAuditableEntity {
    private static final long serialVersionUID = -4289191210184144287L;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<FileMetadata> file = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private RainfallMapLayerType type;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToOne(fetch = FetchType.LAZY)
    private DecadalRainfallMap decadalRainfallMap;

    public Set<FileMetadata> getFile() {
        return file;
    }

    public void setFile(Set<FileMetadata> file) {
        this.file = file;
    }

    public FileMetadata getFileSingle() {
        return file.isEmpty() ? null : file.iterator().next();
    }

    public RainfallMapLayerType getType() {
        return type;
    }

    public void setType(RainfallMapLayerType type) {
        this.type = type;
    }

    public DecadalRainfallMap getDecadalRainfallMap() {
        return decadalRainfallMap;
    }

    public void setDecadalRainfallMap(DecadalRainfallMap decadalRainfallMap) {
        this.decadalRainfallMap = decadalRainfallMap;
    }

    public boolean isEmpty() {
        return file.isEmpty();
    }

    @Override
    public AbstractAuditableEntity getParent() {
        return null;
    }
}
