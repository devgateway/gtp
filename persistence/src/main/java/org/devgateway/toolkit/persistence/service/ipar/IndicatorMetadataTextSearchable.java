package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.service.TextSearchableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public class IndicatorMetadataTextSearchable implements TextSearchableService<Person>, Serializable {
    private static final long serialVersionUID = 2762745187528278165L;

    @Override
    public JpaRepository<Person, Long> getRepository() {
        return null;
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public Page<Person> searchText(String term, Pageable page) {
        return null;
    }
}
