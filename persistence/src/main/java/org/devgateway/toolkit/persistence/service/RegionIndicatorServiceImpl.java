package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.GisSettings;
import org.devgateway.toolkit.persistence.dao.GisSettingsDescription;
import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.dto.GisDTO;
import org.devgateway.toolkit.persistence.dto.GisIndicatorDTO;
import org.devgateway.toolkit.persistence.dto.GisStatDTO;
import org.devgateway.toolkit.persistence.repository.ConsumptionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.GisIndicatorDepartment;
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
import java.util.Optional;

import static org.devgateway.toolkit.persistence.util.Constants.EMPTY_STRING;
import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

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
        fillIndicator(lang, ret, getGisDtoRegionList(povertyRepo), descRepository.findByType(POVERTY_TYPE_ID));
        fillIndicator(lang, ret, getGisDtoRegionList(prodRepo), descRepository.findByType(PROD_TYPE_ID));
        fillIndicator(lang, ret, getGisDtoRegionList(consRepo), descRepository.findByType(CONSUMPTION_TYPE_ID));

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

    @Override
    public List<GisIndicatorDTO> findGisDepartmentIndicators(String lang) {
        List<GisIndicatorDTO> ret = new ArrayList<>();
        fillIndicator(lang, ret, getGisDtoDepartmentList(prodRepo), descRepository.findByType(PROD_TYPE_ID));
        fillIndicator(lang, ret, getGisDtoDepartmentList(consRepo),
                descRepository.findByType(CONSUMPTION_TYPE_ID));
        return ret;
    }

    List<GisDTO> getGisDtoRegionList(GisIndicatorRegion repo) {
        return repo.findAllGisByRegion();
    }

    List<GisDTO> getGisDtoDepartmentList(GisIndicatorDepartment repo) {
        return repo.findAllGisByDepartment();
    }

    private void fillIndicator(String lang, List<GisIndicatorDTO> ret, List<GisDTO> gisDTOList,
                               Optional<GisSettingsDescription> description) {

        boolean isFR = lang != null && lang.equalsIgnoreCase(LANG_FR);
        int year = -1;
        String crop = EMPTY_STRING;
        GisIndicatorDTO dto = null;
        for (GisDTO p : gisDTOList) {

            if ((p.getYear() != year && p.getCrop() == null)
                    || (p.getCrop() != null && (p.getYear() != year || !crop.equals(p.getCrop())))) {
                dto = new GisIndicatorDTO();
                year = p.getYear();
                crop = p.getCrop() != null ? p.getCrop() : EMPTY_STRING;
                dto.setId(Long.valueOf(year) * 1000 + crop.hashCode());
                dto.setName(p.getName(isFR));
                dto.setNameEnFr(p.getNameEnFr());
                String desc = null;
                if (description.isPresent()) {
                    if (isFR) {
                        desc = description.get().getDescriptionFr();
                    } else {
                        desc = description.get().getDescription();
                    }
                }
                dto.setDescription(desc);
                if (p.getValue() != null) {
                    dto.setMaxValue(p.getValue());
                    dto.setMinValue(p.getValue());
                } else {
                    dto.setMaxValue(0D);
                    dto.setMinValue(0D);
                }
                dto.setMeasure(p.getMeasure(isFR));
                dto.setRightMap(false);
                dto.setLeftMap(false);
                dto.setStats(new ArrayList<>());
                dto.setYear(year);
                if (p.getSource() != null) {
                    dto.getSources().add(p.getSource());
                }
                ret.add(dto);
            }
            fillStat(dto, p.getCode(), p.getValue());
        }
    }

    private void fillStat(GisIndicatorDTO dto, String code, Double value) {
        GisStatDTO rs = new GisStatDTO();
        rs.setCode(code);
        rs.setValue(value);
        if (value != null && value > dto.getMaxValue()) {
            dto.setMaxValue(value);
        }
        if (value != null && value < dto.getMinValue()) {
            dto.setMinValue(value);
        }
        dto.getStats().add(rs);
    }
}
