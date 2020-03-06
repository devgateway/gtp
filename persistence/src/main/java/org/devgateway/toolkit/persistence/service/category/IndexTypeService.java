package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.IndexType;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.util.Optional;

public interface IndexTypeService extends BaseJpaService<IndexType> {

    Optional<IndexType> findByCategoryTypeId(int typeId);
}
