package org.devgateway.toolkit.persistence.service;

import java.io.Serializable;

import org.devgateway.toolkit.persistence.dao.GenericPersistable;
import org.devgateway.toolkit.persistence.repository.norepository.TextSearchableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Octavian Ciubotaru
 */
public class TextSearchableAdapter<T extends GenericPersistable & Serializable>
        implements TextSearchableService<T>, Serializable {

    private TextSearchableRepository<T, Long> textSearchableRepository;

    public TextSearchableAdapter(TextSearchableRepository<T, Long> textSearchableRepository) {
        this.textSearchableRepository = textSearchableRepository;
    }

    @Override
    public JpaRepository<T, Long> getRepository() {
        return textSearchableRepository;
    }

    @Override
    public Page<T> searchText(String term, Pageable page) {
        if (term == null) {
            return textSearchableRepository.findAll(page);
        } else {
            return textSearchableRepository.searchText(term, page);
        }
    }
}
