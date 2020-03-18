package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.DepartmentIndicator;
import org.devgateway.toolkit.persistence.dto.GisDTO;
import org.devgateway.toolkit.persistence.dto.GisIndicatorDTO;
import org.devgateway.toolkit.persistence.repository.ConsumptionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.GisIndicatorDepartment;
import org.devgateway.toolkit.persistence.repository.GisSettingsDescriptionRepository;
import org.devgateway.toolkit.persistence.repository.ProductionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.DepartmentIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author dbianco
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional
public class DepartmentIndicatorServiceImpl extends BaseJpaServiceImpl<DepartmentIndicator>
        implements DepartmentIndicatorService {

    public static final int PROD_TYPE_ID = 2;
    public static final int CONSUMPTION_TYPE_ID = 3;

    @Autowired
    private GisSettingsDescriptionRepository descRepository;

    @Autowired
    private DepartmentIndicatorRepository repository;

    @Autowired
    private ProductionIndicatorRepository prodRepo;

    @Autowired
    private ConsumptionIndicatorRepository consRepo;


    @Override
    protected BaseJpaRepository<DepartmentIndicator, Long> repository() {
        return repository;
    }

    @Override
    public DepartmentIndicator newInstance() {
        return new DepartmentIndicator();
    }

    @Override
    public List<GisIndicatorDTO> findGisDepartmentIndicators(String lang) {
        List<GisIndicatorDTO> ret = new ArrayList<>();
        IndicatorUtils.fillIndicator(lang, ret, getGisDtoDepartmentList(prodRepo),
                descRepository.findByType(PROD_TYPE_ID));
        IndicatorUtils.fillIndicator(lang, ret, getGisDtoDepartmentList(consRepo),
                descRepository.findByType(CONSUMPTION_TYPE_ID));
        return ret;
    }

    List<GisDTO> getGisDtoDepartmentList(GisIndicatorDepartment repo) {
        return repo.findAllGisByDepartment();
    }


}
