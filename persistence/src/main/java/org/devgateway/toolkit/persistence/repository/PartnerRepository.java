package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.ipar.Partner;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Octavian Ciubotaru
 */
@Transactional
public interface PartnerRepository extends BaseJpaRepository<Partner, Long> {

    @Query("select r "
            + "from Partner r "
            + "join fetch r.groupType rl "
            + "join fetch rl.localizedLabels")
    List<Partner> findAllPopulatedLang();

    @Query("select count(p) "
            + "from Partner p "
            + "where lower(p.name)=lower(:name) ")
    int countByName(@Param("name") String name);

    @Query("select count(p) "
            + "from Partner p "
            + "where p.name=:name "
            + "and id<>:exceptId")
    int countByName(Long exceptId, String name);

    @Query("select p from Partner p join p.groupType as g order by g.type asc, p.name asc")
    List<Partner> findPartnerOrdered();
}
