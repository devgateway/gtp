package org.devgateway.toolkit.persistence.service.category;

import org.devgateway.toolkit.persistence.dao.categories.IndexType;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.util.List;

public interface IndexTypeService extends BaseJpaService<IndexType> {

    List<IndexType> findByCategoryTypeId(int typeId);
}
