package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author idobre
 * @since 2019-03-04
 */
public interface TextSearchableService<T extends GenericPersistable & Serializable> {

    JpaRepository<T, Long> getRepository();

    /**
     * Retrieve results that match the term. If term is null then all results must be returned as if no filtering
     * is applied.
     *
     * @param term filter by this
     * @param page the pageable object
     * @return all matched objects
     */
    Page<T> searchText(String term, Pageable page);
}
