package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface FoodLossIndicatorRepository extends AuditedEntityRepository<FoodLossIndicator> {
}
