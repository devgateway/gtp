package org.devgateway.toolkit.persistence.repository.menu;

import org.devgateway.toolkit.persistence.dao.menu.CNSCHeader;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface CNSCHeaderRepository extends BaseJpaRepository<CNSCHeader, Long> {
}
