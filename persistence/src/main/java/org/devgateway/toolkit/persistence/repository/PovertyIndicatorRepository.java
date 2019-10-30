package org.devgateway.toolkit.persistence.repository;

import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Daniel Oliva
 */
@Transactional
public interface PovertyIndicatorRepository extends AuditedEntityRepository<PovertyIndicator> {

}
