package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dto.DatasetDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DatasetFinderService<T extends Dataset> extends DatasetService<T>  {

    List<DatasetDTO> findAllDTO(Specification<Dataset> spec, String lang);
}
