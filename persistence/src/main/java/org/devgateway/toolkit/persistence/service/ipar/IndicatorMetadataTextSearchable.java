package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.service.TextSearchableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public class IndicatorMetadataTextSearchable implements TextSearchableService<Person>, Serializable {



    @Override
    public JpaRepository<Person, Long> getRepository() {
        return null;
    }

    @Override
    public Page<Person> searchText(String term, Pageable page) {
        return null;
    }
}
