package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dto.ipar.DatasetDTO;
import org.devgateway.toolkit.persistence.service.BaseJpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface DatasetService<T extends Dataset> extends BaseJpaService<T> {

    Page<DatasetDTO> findAllDTO(Specification<Dataset> spec, Pageable pageable, String lang);
}
