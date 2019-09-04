package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.ProductionEvent;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface ProductionEventRepository extends BaseJpaRepository<ProductionEvent, Long> {

}
