package org.devgateway.toolkit.persistence.repository.cnsc.menu;

import org.devgateway.toolkit.persistence.dao.cnsc.menu.CNSCMenuGroup;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nadejda Mandrescu
 */
@Transactional
public interface CNSCMenuGroupRepository extends BaseJpaRepository<CNSCMenuGroup, Long> {

    CNSCMenuGroup findByName(String name);
}
