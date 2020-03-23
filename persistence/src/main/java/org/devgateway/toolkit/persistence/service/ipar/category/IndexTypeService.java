package org.devgateway.toolkit.persistence.service.ipar.category;

import org.devgateway.toolkit.persistence.dao.ipar.categories.IndexType;
import org.devgateway.toolkit.persistence.service.BaseJpaService;

import java.util.List;

public interface IndexTypeService extends BaseJpaService<IndexType> {

    List<IndexType> findByCategoryTypeId(int typeId);
}
