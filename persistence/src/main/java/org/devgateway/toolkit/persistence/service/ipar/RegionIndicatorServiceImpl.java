package org.devgateway.toolkit.persistence.service.ipar;

import org.devgateway.toolkit.persistence.dao.ipar.GisSettings;
import org.devgateway.toolkit.persistence.dao.ipar.RegionIndicator;
import org.devgateway.toolkit.persistence.dto.ipar.GisIndicatorDTO;
import org.devgateway.toolkit.persistence.repository.ipar.ConsumptionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.ipar.GisSettingsDescriptionRepository;
import org.devgateway.toolkit.persistence.repository.ipar.PovertyIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.ipar.ProductionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.ipar.RegionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.devgateway.toolkit.persistence.service.BaseJpaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.devgateway.toolkit.persistence.util.Constants.CONS_DAILY_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_SIZE_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.CONS_WEEKLY_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_PROD_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_SURFACE_TYPE;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_YIELD_TYPE;


/**
 * @author dbianco
 */
// @Service
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
        List<GisIndicatorDTO> ret = getFakeIndicatorDTOs(lang);

        //Add indicator groups to fake DI
        List<RegionIndicator> fakeList = repository.findAllFake();
        Map<String, RegionIndicator> indicatorFake = fakeList.stream()
                .collect(Collectors.toMap(RegionIndicator::getName, i -> i));
        indicatorFake.putAll(fakeList.stream()
                .collect(Collectors.toMap(RegionIndicator::getNameFr, i -> i)));
        ret.stream().forEach(i -> {
            RegionIndicator di = indicatorFake.get(i.getName());
            if (di != null) {
                i.setIndicatorGroup(di.getIndicatorGroup().getLabel(lang));
            }
        });

        List<RegionIndicator> indicatorList = repository.findAllApprovedNotFake();
        indicatorList.stream().forEach(i -> ret.add(new GisIndicatorDTO(i, lang)));

        List<GisSettings> gisSettings = gisSettingsService.findAll();
        if (gisSettings.size() > 0) {
            ret.stream().forEach(n -> {
                if (n.getNameEnFr().equalsIgnoreCase(gisSettings.get(0).getLeftGisRegionName())) {
                    n.setLeftMap(true);
                }
                if (n.getNameEnFr().equalsIgnoreCase(gisSettings.get(0).getRightGisRegionName())) {
                    n.setRightMap(true);
                }
            });
        }
        return ret;
    }

    public List<GisIndicatorDTO> getFakeIndicatorDTOs(String lang) {
        List<GisIndicatorDTO> ret = new ArrayList<>();

        IndicatorUtils.fillIndicator(lang, ret, povertyRepo.findAllGisDailyConsumptionByRegion(),
                descRepository.findByType(POVERTY_TYPE_ID), 0);

        //production
        IndicatorUtils.fillIndicator(lang, ret, prodRepo.findAllGisProductionByRegion(),
                descRepository.findByType(PROD_TYPE_ID), PROD_PROD_TYPE);

        //surface
        IndicatorUtils.fillIndicator(lang, ret, prodRepo.findAllGisSurfaceByRegion(),
                descRepository.findByType(PROD_TYPE_ID), PROD_SURFACE_TYPE);

        //yield
        IndicatorUtils.fillIndicator(lang, ret, prodRepo.findAllGisYieldByRegion(),
                descRepository.findByType(PROD_TYPE_ID), PROD_YIELD_TYPE);

        //daily
        IndicatorUtils.fillIndicator(lang, ret, consRepo.findAllGisDailyByRegion(),
                descRepository.findByType(CONSUMPTION_TYPE_ID), CONS_DAILY_TYPE);

        //weekly
        IndicatorUtils.fillIndicator(lang, ret, consRepo.findAllGisWeeklyByRegion(),
                descRepository.findByType(CONSUMPTION_TYPE_ID), CONS_WEEKLY_TYPE);

        //Size
        IndicatorUtils.fillIndicator(lang, ret, consRepo.findAllGisSizeByRegion(),
                descRepository.findByType(CONSUMPTION_TYPE_ID), CONS_SIZE_TYPE);
        return ret;
    }

    @Override
    public List<RegionIndicator> findAllFake() {
        return repository.findAllFake();
    }

}