package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.ipar.categories.DatasetType;
import org.devgateway.toolkit.persistence.dto.ipar.DatasetDTO;
import org.devgateway.toolkit.persistence.repository.ipar.DatasetRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.DatasetTypeRepository;
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
import java.util.Map;
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

    @Autowired
    private DatasetTypeRepository datasetTypeRepository;

    @Override
    protected BaseJpaRepository<Dataset, Long> repository() {
        return datasetRepository;
    }

    public Page<DatasetDTO> findAllDTO(Specification<Dataset> spec, Pageable pageable, String lang) {
        Page<Dataset> datasetPage =  datasetRepository.findAll(spec, pageable);

        Map<String, DatasetType> datasetTypeMap = datasetTypeRepository.findAllPopulatedLang()
                .stream().collect(Collectors.toMap(DatasetType::getDescription, d -> d));

        List<DatasetDTO> dtoList = datasetPage.get().map(d -> {
            DatasetType type = datasetTypeMap.get(d.getDtype());
            return new DatasetDTO(d, type, lang);
        }).collect(Collectors.toList());
        return new PageImpl(dtoList, datasetPage.getPageable(), datasetPage.getTotalElements());
    }

    @Override
    public Dataset newInstance() {
        return new Dataset();
    }
}
