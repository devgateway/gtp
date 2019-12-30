package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dto.DatasetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface DatasetService<T extends Dataset> extends BaseJpaService<T>  {

    Page<DatasetDTO> findAllDTO(Specification<Dataset> spec, Pageable pageable, String lang);
}
