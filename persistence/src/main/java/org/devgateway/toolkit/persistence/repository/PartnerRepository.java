package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.Partner;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Query("select p from Partner p join p.groupType as g order by g.type asc, p.name asc")
    List<Partner> findPartnerOrdered();
}
