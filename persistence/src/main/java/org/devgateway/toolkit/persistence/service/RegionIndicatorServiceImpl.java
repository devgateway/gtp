package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.dto.PovertyGisDTO;
import org.devgateway.toolkit.persistence.dto.ProductionGisDTO;
import org.devgateway.toolkit.persistence.dto.RegionIndicatorDTO;
import org.devgateway.toolkit.persistence.dto.RegionStatDTO;
import org.devgateway.toolkit.persistence.repository.PovertyIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.ProductionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.RegionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.norepository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.devgateway.toolkit.persistence.util.Constants.EMPTY_STRING;
import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;
import static org.devgateway.toolkit.persistence.util.Constants.MINUS_STRING;
import static org.devgateway.toolkit.persistence.util.Constants.POVERTY_EN_STR;
import static org.devgateway.toolkit.persistence.util.Constants.POVERTY_FR_STR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_EN_STR;
import static org.devgateway.toolkit.persistence.util.Constants.PROD_FR_STR;
import static org.devgateway.toolkit.persistence.util.Constants.SPACE_STRING;

/**
 * @author dbianco
 */
@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional
public class RegionIndicatorServiceImpl extends BaseJpaServiceImpl<RegionIndicator> implements RegionIndicatorService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RegionIndicatorRepository repository;

    @Autowired
    private PovertyIndicatorRepository povertyRepo;

    @Autowired
    private ProductionIndicatorRepository prodRepo;

    @Override
    protected BaseJpaRepository<RegionIndicator, Long> repository() {
        return repository;
    }

    @Override
    public RegionIndicator newInstance() {
        return new RegionIndicator();
    }

    @Override
    public List<RegionIndicatorDTO> findGisIndicatorAndPovertyIndicator(final String lang) {
        List<RegionIndicatorDTO> ret = new ArrayList<>();
        fillPovertyIndicator(lang, ret);
        fillProductionIndicator(lang, ret);

        List<RegionIndicator> indicatorList = repository.findAll();
        indicatorList.stream().filter(n -> n.isApproved()).forEach(i -> ret.add(new RegionIndicatorDTO(i)));

        return ret;
    }

    @Override
    public void restoreLeftFlagToFalse() {
        entityManager.createQuery("update RegionIndicator set leftMap = false")
                .executeUpdate();
    }

    @Override
    public void restoreRightFlagToFalse() {
        entityManager.createQuery("update RegionIndicator set rightMap = false")
                .executeUpdate();
    }

    private void fillProductionIndicator(String lang, List<RegionIndicatorDTO> ret) {
        String prodStr = PROD_EN_STR;

        if (lang != null && lang.equalsIgnoreCase(LANG_FR)) {
            prodStr = PROD_FR_STR;
        }
        List<ProductionGisDTO> prodList = prodRepo.findAllProductionGis();
        int year = -1;
        String crop = EMPTY_STRING;
        RegionIndicatorDTO dto = null;
        for (ProductionGisDTO p : prodList) {
            if (p.getYear() != year || !crop.equals(p.getCrop())) {
                dto = new RegionIndicatorDTO();
                year = p.getYear();
                crop = p.getCrop();
                dto.setId(Long.valueOf(year) + p.getCrop().hashCode());
                String cropLabel = lang != null && lang.equalsIgnoreCase(LANG_FR) ? p.getCropFr() : p.getCrop();
                dto.setName(prodStr + MINUS_STRING + cropLabel + MINUS_STRING + year);
                if (p.getValue() != null) {
                    dto.setMaxValue(p.getValue());
                    dto.setMinValue(p.getValue());
                } else {
                    dto.setMaxValue(0D);
                    dto.setMinValue(0D);
                }
                dto.setRightMap(false);
                dto.setLeftMap(false);
                dto.setStats(new ArrayList<>());
                dto.setYear(year);
                if (p.getSource() != null) {
                    dto.getSources().add(p.getSource());
                }
                ret.add(dto);
            }
            fillRegionStat(dto, p.getCode(), p.getValue());
        }
    }

    private void fillPovertyIndicator(String lang, List<RegionIndicatorDTO> ret) {
        String povertyStr = POVERTY_EN_STR;

        if (lang != null && lang.equalsIgnoreCase(LANG_FR)) {
            povertyStr = POVERTY_FR_STR;
        }

        List<PovertyGisDTO> povertyList = povertyRepo.findAllPovertyGis();
        int year = -1;
        RegionIndicatorDTO dto = null;
        for (PovertyGisDTO p : povertyList) {
            if (p.getYear() != year) {
                dto = new RegionIndicatorDTO();
                year = p.getYear();
                dto.setId(Long.valueOf(year));
                dto.setName(povertyStr + SPACE_STRING + year);
                dto.setMaxValue(p.getValue());
                dto.setMinValue(p.getValue());
                dto.setRightMap(false);
                dto.setLeftMap(false);
                dto.setStats(new ArrayList<>());
                dto.setYear(year);
                if (p.getSource() != null) {
                    dto.getSources().add(p.getSource());
                }
                ret.add(dto);
            }
            fillRegionStat(dto, p.getCode(), p.getValue());
        }
    }

    private void fillRegionStat(RegionIndicatorDTO dto, String code, Double value) {
        RegionStatDTO rs = new RegionStatDTO();
        rs.setRegionCode(code);
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
