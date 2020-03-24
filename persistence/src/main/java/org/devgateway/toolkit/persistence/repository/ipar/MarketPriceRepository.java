package org.devgateway.toolkit.persistence.repository.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.MarketPrice;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daniel Oliva
 */
@NoRepositoryBean
@Transactional
public interface MarketPriceRepository extends AuditedEntityRepository<MarketPrice> {

}
