package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.AdminSettings;
import org.devgateway.toolkit.persistence.dao.Department;
import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.dao.categories.AgeGroup;
import org.devgateway.toolkit.persistence.dao.categories.AgriculturalWomenGroup;
import org.devgateway.toolkit.persistence.dao.categories.CropSubType;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.devgateway.toolkit.persistence.dao.categories.Gender;
import org.devgateway.toolkit.persistence.dao.categories.IndexType;
import org.devgateway.toolkit.persistence.dao.categories.LocationType;
import org.devgateway.toolkit.persistence.dao.categories.LossType;
import org.devgateway.toolkit.persistence.dao.categories.MethodOfEnforcement;
import org.devgateway.toolkit.persistence.dao.categories.PovertyLevel;
import org.devgateway.toolkit.persistence.dao.categories.ProfessionalActivity;
import org.devgateway.toolkit.persistence.service.AdminSettingsService;
import org.devgateway.toolkit.persistence.service.category.AgeGroupService;
import org.devgateway.toolkit.persistence.service.category.AgriculturalWomenGroupService;
import org.devgateway.toolkit.persistence.service.category.CropSubTypeService;
import org.devgateway.toolkit.persistence.service.category.CropTypeService;
import org.devgateway.toolkit.persistence.service.category.DepartmentService;
import org.devgateway.toolkit.persistence.service.category.GenderService;
import org.devgateway.toolkit.persistence.service.category.IndexTypeService;
import org.devgateway.toolkit.persistence.service.category.LocationTypeService;
import org.devgateway.toolkit.persistence.service.category.LossTypeService;
import org.devgateway.toolkit.persistence.service.category.MarketService;
import org.devgateway.toolkit.persistence.service.category.MethodOfEnforcementService;
import org.devgateway.toolkit.persistence.service.category.PovertyLevelService;
import org.devgateway.toolkit.persistence.service.category.ProfessionalActivityService;
import org.devgateway.toolkit.persistence.service.category.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/filter")
@CrossOrigin
@CacheConfig(keyGenerator = "genericKeyGenerator", cacheNames = "servicesCache")
@Cacheable
public class FilterController {

    @Autowired
    private RegionService regionService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CropTypeService cropTypeService;

    @Autowired
    private CropSubTypeService cropSubTypeService;

    @Autowired
    private GenderService genderService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private IndexTypeService indexTypeService;

    @Autowired
    private LossTypeService lossTypeService;

    @Autowired
    private AgriculturalWomenGroupService awGroupService;

    @Autowired
    private MethodOfEnforcementService mofService;

    @Autowired
    private AgeGroupService ageGroupService;

    @Autowired
    private ProfessionalActivityService profService;

    @Autowired
    private LocationTypeService locService;

    @Autowired
    private AdminSettingsService adminService;

    @Autowired
    private PovertyLevelService povertyLevelService;

    @CrossOrigin
    @ApiOperation(value = "Get regions information")
    @RequestMapping(value = "/region", method = {POST, GET})
    public List<Region> getAllRegions() {
        return regionService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get departments information")
    @RequestMapping(value = "/department", method = {POST, GET})
    public List<Department> getAllDeparments() {
        return departmentService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get crop types information")
    @RequestMapping(value = "/cropType", method = {POST, GET})
    public List<CropType> getAllCropType() {
        return cropTypeService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get crop subtypes information")
    @RequestMapping(value = "/cropSubType", method = {POST, GET})
    public List<CropSubType> getAllCropSubType() {
        return cropSubTypeService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get gender information")
    @RequestMapping(value = "/gender", method = {POST, GET})
    public List<Gender> getAllGender() {
        return genderService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get market information")
    @RequestMapping(value = "/market", method = {POST, GET})
    public List<Market> getAllMarket() {
        return marketService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get index type information")
    @RequestMapping(value = "/lossType", method = {POST, GET})
    public List<LossType> getAllLossType() {
        return lossTypeService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get loss type information")
    @RequestMapping(value = "/indexType", method = {POST, GET})
    public List<IndexType> getAllIndexType() {
        return indexTypeService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get agricultural women group information")
    @RequestMapping(value = "/awGroup", method = {POST, GET})
    public List<AgriculturalWomenGroup> getAllAwGroup() {
        return awGroupService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get age group information")
    @RequestMapping(value = "/ageGroup", method = {POST, GET})
    public List<AgeGroup> getAllAgeGroup() {
        return ageGroupService.findAll();
    }


    @CrossOrigin
    @ApiOperation(value = "Get method of enforcement information")
    @RequestMapping(value = "/methodOfEnforcement", method = GET)
    public List<MethodOfEnforcement> getAllMethodOfEnforcement() {
        return mofService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get location type information")
    @RequestMapping(value = "/locationType", method = {POST, GET})
    public List<LocationType> getAllLocationType() {
        return locService.findAll();
    }


    @CrossOrigin
    @ApiOperation(value = "Get professional activity information")
    @RequestMapping(value = "/professionalActivity", method = {POST, GET})
    public List<ProfessionalActivity> getAllProfActivity() {
        return profService.findAll();
    }


    @CrossOrigin
    @ApiOperation(value = "Get poverty level information")
    @RequestMapping(value = "/povertyLevel", method = {POST, GET})
    public List<PovertyLevel> getAllPovertyLevel() {
        return povertyLevelService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get campaing/year information")
    @RequestMapping(value = "/year", method = {POST, GET})
    public List<Map<String, Integer>> getAllYears() {
        Integer startingYear = 2010;
        List<AdminSettings> adminSettings =  adminService.findAll();
        if (adminSettings.size() > 0 && adminSettings.get(0).getStartingYear() != null) {
            startingYear = adminSettings.get(0).getStartingYear();
        }
        Map<String, Integer> yearMap = new HashMap<>();
        yearMap.put("id", startingYear);
        yearMap.put("label", startingYear);
        List<Map<String, Integer>> ret = new ArrayList<>();
        ret.add(yearMap);

        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int year = startingYear + 1;
        while (currentYear >= year) {
            Map<String, Integer> map = new HashMap<>();
            map.put("id", year);
            map.put("label", year);
            ret.add(map);
            year += 1;
        }

        return ret;
    }
}
