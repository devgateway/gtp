package org.devgateway.toolkit.persistence.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@BatchSize(size = 100)
public class Region extends GenericPersistable implements Serializable, Labelable {

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true)
    private String code;

    public Region() {
    }

    public Region(Long id) {
        setId(id);
    }

    public Region(Long id, String name, String code) {
        setId(id);
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void setLabel(String label) {
        this.name = label;
    }

    @Override
    public String getLabel() {
        return name;
    }

    @JsonProperty("labelFr")
    public String getLabelFr() {
        return name;
    }
}
