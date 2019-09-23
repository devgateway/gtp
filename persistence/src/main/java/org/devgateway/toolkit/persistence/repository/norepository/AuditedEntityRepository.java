package org.devgateway.toolkit.persistence.repository.norepository;

import java.time.ZonedDateTime;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Octavian Ciubotaru
 */
@NoRepositoryBean
public interface AuditedEntityRepository<T extends AbstractAuditableEntity>
        extends BaseJpaRepository<T, Long> {

    @Query("select max(e.lastModifiedDate) from #{#entityName} e")
    ZonedDateTime getLastModifiedDate();
}
