package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.categories.DatasetType;
import org.devgateway.toolkit.persistence.dto.DatasetDTO;
import org.devgateway.toolkit.persistence.repository.DatasetRepository;
import org.devgateway.toolkit.persistence.repository.category.DatasetTypeRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
public class DatasetFinderServiceImpl extends BaseJpaServiceImpl<Dataset>
        implements DatasetFinderService<Dataset>  {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private DatasetTypeRepository datasetTypeRepository;

    @Override
    protected BaseJpaRepository<Dataset, Long> repository() {
        return datasetRepository;
    }

    @Override
    public List<DatasetDTO> findAllDTO(Specification<Dataset> spec, String lang) {
        List<Dataset> datasetList =  datasetRepository.findAll(spec);

        Map<String, DatasetType> datasetTypeMap = datasetTypeRepository.findAllPopulatedLang()
                .stream().collect(Collectors.toMap(DatasetType::getDescription, d -> d));

        List<DatasetDTO> dtoList = datasetList.stream().map(d -> {
            DatasetType type = datasetTypeMap.get(d.getDtype());
            return new DatasetDTO(d, type, lang);
        }).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public Dataset newInstance() {
        return new Dataset();
    }
}
