package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.DepartmentIndicator;
import org.devgateway.toolkit.persistence.dao.GisSettings;
import org.devgateway.toolkit.persistence.dto.GisIndicatorDTO;
import org.devgateway.toolkit.persistence.repository.ConsumptionIndicatorRepository;
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

    @Autowired
    private GisSettingsService gisSettingsService;


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
        List<GisIndicatorDTO> ret = getFakeIndicatorDTOs(lang);

        //Add indicator groups to fake DI
        List<DepartmentIndicator> fakeList = repository.findAllFake();
        Map<String, DepartmentIndicator> indicatorFake = fakeList.stream()
                .collect(Collectors.toMap(DepartmentIndicator::getName, i -> i));
        indicatorFake.putAll(fakeList.stream()
                .collect(Collectors.toMap(DepartmentIndicator::getNameFr, i -> i)));
        ret.stream().forEach(i -> {
            DepartmentIndicator di = indicatorFake.get(i.getName());
            if (di != null) {
                i.setIndicatorGroup(di.getIndicatorGroup().getLabel(lang));
            }
        });

        List<DepartmentIndicator> indicatorList = repository.findAllApprovedNotFake();
        indicatorList.stream().forEach(i -> ret.add(new GisIndicatorDTO(i, lang)));

        List<GisSettings> gisSettings = gisSettingsService.findAll();
        if (gisSettings.size() > 0) {
            ret.stream().forEach(n -> {
                if (n.getNameEnFr().equalsIgnoreCase(gisSettings.get(0).getLeftGisDepartmentName())) {
                    n.setLeftMap(true);
                }
                if (n.getNameEnFr().equalsIgnoreCase(gisSettings.get(0).getRightGisDepartmentName())) {
                    n.setRightMap(true);
                }
            });
        }

        return ret;
    }

    @Override
    public List<GisIndicatorDTO> getFakeIndicatorDTOs(String lang) {
        List<GisIndicatorDTO> ret = new ArrayList<>();

        //production
        IndicatorUtils.fillIndicator(lang, ret, prodRepo.findAllGisProductionByDepartment(),
                descRepository.findByType(PROD_TYPE_ID), PROD_PROD_TYPE);

        //surface
        IndicatorUtils.fillIndicator(lang, ret, prodRepo.findAllGisSurfaceByDepartment(),
                descRepository.findByType(PROD_TYPE_ID), PROD_SURFACE_TYPE);

        //yield
        IndicatorUtils.fillIndicator(lang, ret, prodRepo.findAllGisYieldByDepartment(),
                descRepository.findByType(PROD_TYPE_ID), PROD_YIELD_TYPE);

        //Daily Consumption
        IndicatorUtils.fillIndicator(lang, ret, consRepo.findAllGisDailyByDepartment(),
                descRepository.findByType(CONSUMPTION_TYPE_ID), CONS_DAILY_TYPE);

        //Weekly
        IndicatorUtils.fillIndicator(lang, ret, consRepo.findAllGisWeeklyByDepartment(),
                descRepository.findByType(CONSUMPTION_TYPE_ID), CONS_WEEKLY_TYPE);

        //Size
        IndicatorUtils.fillIndicator(lang, ret, consRepo.findAllSizeByDepartment(),
                descRepository.findByType(CONSUMPTION_TYPE_ID), CONS_SIZE_TYPE);
        return ret;
    }

    @Override
    public List<GisIndicatorDTO> getFakeIndicatorDTOsMinimal(String lang) {
        List<GisIndicatorDTO> ret = new ArrayList<>();

        //production
        IndicatorUtils.fillIndicator(lang, ret, prodRepo.findAllGisProductionByDepartment(),
                null, PROD_PROD_TYPE);

        //surface
        IndicatorUtils.fillIndicator(lang, ret, prodRepo.findAllGisSurfaceByDepartment(),
                null, PROD_SURFACE_TYPE);

        //yield
        IndicatorUtils.fillIndicator(lang, ret, prodRepo.findAllGisYieldByDepartment(),
                null, PROD_YIELD_TYPE);

        //Daily Consumption
        IndicatorUtils.fillIndicator(lang, ret, consRepo.findAllGisDailyByDepartment(),
                null, CONS_DAILY_TYPE);

        //Weekly
        IndicatorUtils.fillIndicator(lang, ret, consRepo.findAllGisWeeklyByDepartment(),
                null, CONS_WEEKLY_TYPE);

        //Size
        IndicatorUtils.fillIndicator(lang, ret, consRepo.findAllSizeByDepartment(),
                null, CONS_SIZE_TYPE);
        return ret;
    }

    @Override
    public List<DepartmentIndicator> findAllFake() {
        return repository.findAllFake();
    }

    @Override
    public void deleteAllFake() {
        repository.deleteFake();
    }


}
