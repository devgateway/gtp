package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.Partner;
import org.devgateway.toolkit.persistence.dao.Person;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface PartnerRepository extends BaseJpaRepository<Partner, Long> {

    int countByName(String name);

    @Query("select count(p) "
            + "from Partner p "
            + "where p.name=:name "
            + "and id<>:exceptId")
    int countByName(Long exceptId, String name);
}
