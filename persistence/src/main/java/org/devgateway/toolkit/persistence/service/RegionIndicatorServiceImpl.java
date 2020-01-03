package org.devgateway.toolkit.persistence.service;

import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.dto.PovertyGisDTO;
import org.devgateway.toolkit.persistence.dto.RegionIndicatorDTO;
import org.devgateway.toolkit.persistence.dto.RegionStatDTO;
import org.devgateway.toolkit.persistence.repository.PovertyIndicatorRepository;
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

    public static final String POVERTY_FR_STR = "Pauvreté";
    public static final String POVERTY_EN_STR = "Poverty";

    @Autowired
    private RegionIndicatorRepository repository;

    @Autowired
    private PovertyIndicatorRepository povertyRepo;

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
        String povertyStr = POVERTY_EN_STR;

        if (lang != null && lang.equalsIgnoreCase("fr")) {
            povertyStr = POVERTY_FR_STR;
        }

        List<PovertyGisDTO> povertyList = povertyRepo.findAllPovertyGis();
        int year = -1;
        RegionIndicatorDTO dto = null;
        for (PovertyGisDTO p : povertyList) {
            if (p.getYear() != year) {
                dto = new RegionIndicatorDTO();
                year = p.getYear();
                dto.setId(new Long(year));
                dto.setName(povertyStr + " " + year);
                dto.setMaxValue(p.getValue());
                dto.setMinValue(p.getValue());
                dto.setStats(new ArrayList<>());
                dto.setYear(year);
                ret.add(dto);
            }
            RegionStatDTO rs = new RegionStatDTO();
            rs.setRegionCode(p.getCode());
            rs.setValue(p.getValue());
            if (p.getValue() != null && p.getValue() > dto.getMaxValue()) {
                dto.setMaxValue(p.getValue());
            }
            if (p.getValue() != null && p.getValue() < dto.getMinValue()) {
                dto.setMinValue(p.getValue());
            }
            dto.getStats().add(rs);
        }

        List<RegionIndicator> indicatorList = repository.findAll();
        indicatorList.stream().forEach(i -> ret.add(new RegionIndicatorDTO(i)));

        return ret;
    }
}
