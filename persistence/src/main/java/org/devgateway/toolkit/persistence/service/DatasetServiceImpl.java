package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dto.DatasetDTO;
import org.devgateway.toolkit.persistence.repository.DatasetRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Daniel Oliva
 */
@Service("datasetService")
@CacheConfig(cacheNames = "servicesCache")
@Transactional(readOnly = true)
public class DatasetServiceImpl extends BaseJpaServiceImpl<Dataset>
        implements DatasetService<Dataset>  {

    @Autowired
    private DatasetRepository datasetRepository;

    @Override
    protected BaseJpaRepository<Dataset, Long> repository() {
        return datasetRepository;
    }

    public Page<DatasetDTO> findAllDTO(Specification<Dataset> spec, Pageable pageable) {
        Page<Dataset> datasetPage =  datasetRepository.findAll(spec, pageable);
        List<DatasetDTO> dtoList = datasetPage.get().map(d -> new DatasetDTO(d)).collect(Collectors.toList());
        return new PageImpl(dtoList, datasetPage.getPageable(), datasetPage.getTotalElements());
    }

    @Override
    public Dataset newInstance() {
        return new Dataset();
    }
}
