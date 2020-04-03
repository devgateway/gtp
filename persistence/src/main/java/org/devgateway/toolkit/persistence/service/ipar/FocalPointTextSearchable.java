package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.repository.PersonRepository;
import org.devgateway.toolkit.persistence.service.TextSearchableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author Octavian Ciubotaru
 */
public class FocalPointTextSearchable implements TextSearchableService<Person>, Serializable {

    private PersonRepository personRepository;

    public FocalPointTextSearchable(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public JpaRepository<Person, Long> getRepository() {
        return personRepository;
    }

    @Override
    public Page<Person> searchText(String term, Pageable page) {
        /*
        if (term == null) {
            return personRepository.findFocalPoints(page);
        } else {
            return personRepository.findFocalPoints(term, page);
        }
        */
        return null;
    }
}
