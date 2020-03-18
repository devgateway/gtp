package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.GisSettings;
import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.dto.GisDTO;
import org.devgateway.toolkit.persistence.dto.GisIndicatorDTO;
import org.devgateway.toolkit.persistence.repository.ConsumptionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.GisIndicatorRegion;
import org.devgateway.toolkit.persistence.repository.GisSettingsDescriptionRepository;
import org.devgateway.toolkit.persistence.repository.PovertyIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.ProductionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.RegionIndicatorRepository;
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
public class RegionIndicatorServiceImpl extends BaseJpaServiceImpl<RegionIndicator> implements RegionIndicatorService {

    public static final int POVERTY_TYPE_ID = 1;
    public static final int PROD_TYPE_ID = 2;
    public static final int CONSUMPTION_TYPE_ID = 3;

    @Autowired
    private GisSettingsDescriptionRepository descRepository;

    @Autowired
    private RegionIndicatorRepository repository;

    @Autowired
    private PovertyIndicatorRepository povertyRepo;

    @Autowired
    private ProductionIndicatorRepository prodRepo;

    @Autowired
    private ConsumptionIndicatorRepository consRepo;

    @Autowired
    private GisSettingsService gisSettingsService;

    @Override
    protected BaseJpaRepository<RegionIndicator, Long> repository() {
        return repository;
    }

    @Override
    public RegionIndicator newInstance() {
        return new RegionIndicator();
    }

    @Override
    public List<GisIndicatorDTO> findGisRegionIndicators(final String lang) {
        List<GisIndicatorDTO> ret = new ArrayList<>();
        IndicatorUtils.fillIndicator(lang, ret, getGisDtoRegionList(povertyRepo),
                descRepository.findByType(POVERTY_TYPE_ID));
        IndicatorUtils.fillIndicator(lang, ret, getGisDtoRegionList(prodRepo),
                descRepository.findByType(PROD_TYPE_ID));
        IndicatorUtils.fillIndicator(lang, ret, getGisDtoRegionList(consRepo),
                descRepository.findByType(CONSUMPTION_TYPE_ID));

        List<RegionIndicator> indicatorList = repository.findAll();
        indicatorList.stream().filter(n -> n.isApproved()).forEach(i -> ret.add(new GisIndicatorDTO(i, lang)));

        List<GisSettings> gisSettings = gisSettingsService.findAll();
        if (gisSettings.size() > 0) {
            ret.stream().forEach(n -> {
                if (n.getNameEnFr().equalsIgnoreCase(gisSettings.get(0).getLeftGisName())) {
                    n.setLeftMap(true);
                }
                if (n.getNameEnFr().equalsIgnoreCase(gisSettings.get(0).getRightGisName())) {
                    n.setRightMap(true);
                }
            });
        }
        return ret;
    }


    List<GisDTO> getGisDtoRegionList(GisIndicatorRegion repo) {
        return repo.findAllGisByRegion();
    }
}
