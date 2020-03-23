package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.RapidLink;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface RapidLinkRepository extends BaseJpaRepository<RapidLink, Long> {

    @Query("select r "
            + "from RapidLink r "
            + "join fetch r.rapidLinkPosition rl "
            + "join fetch rl.localizedLabels")
    List<RapidLink> findAllPopulatedLang();

    Optional<RapidLink> findByRapidLinkPositionId(Long id);

    Iterable<RapidLink> findByRapidLinkPositionIdNotNull();
}
