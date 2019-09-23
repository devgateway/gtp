package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.Consumption;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@Transactional
public interface ConsumptionRepository extends AuditedEntityRepository<Consumption> {
}
