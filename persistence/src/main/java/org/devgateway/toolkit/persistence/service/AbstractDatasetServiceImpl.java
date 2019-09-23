package org.devgateway.toolkit.persistence.service;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.devgateway.toolkit.persistence.dao.AbstractAuditableEntity;
import org.devgateway.toolkit.persistence.repository.norepository.AuditedEntityRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author Octavian Ciubotaru
 */
@CacheConfig(keyGenerator = "genericKeyGenerator", cacheNames = "servicesCache")
public abstract class AbstractDatasetServiceImpl<T extends AbstractAuditableEntity & Serializable>
        extends BaseJpaServiceImpl<T>
        implements AbstractDatasetService<T> {

    @Override
    protected abstract AuditedEntityRepository<T> repository();

    /**
     * Compute ETag value for data returned by findAll(). Current approach is to find the date of the
     * last modification.
     */
    @Override
    @Cacheable
    public String getETagForDump() {
        // TODO revise etag value generation once data validation mechanism is in place
        ZonedDateTime date = repository().getLastModifiedDate();
        if (date != null) {
            return Long.toString(date.toInstant().toEpochMilli());
        } else {
            return null;
        }
    }
}
