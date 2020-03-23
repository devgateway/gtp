package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.ipar.Department;
import org.devgateway.toolkit.persistence.dao.ipar.Market;
import org.devgateway.toolkit.persistence.dao.ipar.Region;
import org.devgateway.toolkit.persistence.dao.ipar.categories.AgeGroup;
import org.devgateway.toolkit.persistence.dao.ipar.categories.AgriculturalWomenGroup;
import org.devgateway.toolkit.persistence.dao.ipar.categories.ContentType;
import org.devgateway.toolkit.persistence.dao.ipar.categories.CropSubType;
import org.devgateway.toolkit.persistence.dao.ipar.categories.CropType;
import org.devgateway.toolkit.persistence.dao.ipar.categories.Gender;
import org.devgateway.toolkit.persistence.dao.ipar.categories.IndexType;
import org.devgateway.toolkit.persistence.dao.categories.LocationType;
import org.devgateway.toolkit.persistence.dao.ipar.categories.LossType;
import org.devgateway.toolkit.persistence.dao.ipar.categories.MethodOfEnforcement;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.ipar.categories.PartnerGroup;
import org.devgateway.toolkit.persistence.dao.ipar.categories.PovertyLevel;
import org.devgateway.toolkit.persistence.dao.ipar.categories.ProfessionalActivity;
import org.devgateway.toolkit.persistence.repository.ipar.DatasetRepository;
import org.devgateway.toolkit.persistence.service.ipar.DataService;
import org.devgateway.toolkit.persistence.service.ipar.category.AgeGroupService;
import org.devgateway.toolkit.persistence.service.ipar.category.AgriculturalWomenGroupService;
import org.devgateway.toolkit.persistence.service.ipar.category.ContentTypeService;
import org.devgateway.toolkit.persistence.service.ipar.category.CropSubTypeService;
import org.devgateway.toolkit.persistence.service.ipar.category.CropTypeService;
import org.devgateway.toolkit.persistence.service.ipar.category.DepartmentService;
import org.devgateway.toolkit.persistence.service.ipar.category.GenderService;
import org.devgateway.toolkit.persistence.service.ipar.category.IndexTypeService;
import org.devgateway.toolkit.persistence.service.ipar.category.LocationTypeService;
import org.devgateway.toolkit.persistence.service.ipar.category.LossTypeService;
import org.devgateway.toolkit.persistence.service.ipar.category.MarketService;
import org.devgateway.toolkit.persistence.service.ipar.category.MethodOfEnforcementService;
import org.devgateway.toolkit.persistence.service.category.OrganizationService;
import org.devgateway.toolkit.persistence.service.ipar.category.PartnerGroupService;
import org.devgateway.toolkit.persistence.service.ipar.category.PovertyLevelService;
import org.devgateway.toolkit.persistence.service.ipar.category.ProfessionalActivityService;
import org.devgateway.toolkit.persistence.service.ipar.category.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    private DataService dataService;

    @Autowired
    private PovertyLevelService povertyLevelService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PartnerGroupService partnerGroupService;

    @Autowired
    private ContentTypeService contentTypeService;

    @Autowired
    private DatasetRepository datasetRepository;

    @CrossOrigin
    @ApiOperation(value = "Get dataset years")
    @RequestMapping(value = "/dataset/years", method = {POST, GET})
    public List<Integer> getAllDatasetYears() {
        return datasetRepository.findDistinctYears();
    }

    @CrossOrigin
    @ApiOperation(value = "Get dataset organizations")
    @RequestMapping(value = "/dataset/organizations", method = {POST, GET})
    public List<Organization> getAllDatasetOrganizations() {
        return datasetRepository.findDistinctOrganizations();
    }

    @CrossOrigin
    @ApiOperation(value = "Get organization information")
    @RequestMapping(value = "/organization", method = {POST, GET})
    public List<Organization> getAllOrganizations() {
        return organizationService.findAll();
    }

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
    @RequestMapping(value = "/indexType/{typeId}", method = {POST, GET})
    public List<IndexType> getIndexTypeByCategoryIndexType(@PathVariable final int typeId) {
        return indexTypeService.findByCategoryTypeId(typeId);
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
    @RequestMapping(value = "/methodOfEnforcement", method = {POST, GET})
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
    @ApiOperation(value = "Get partner group information")
    @RequestMapping(value = "/partnerGroup", method = {POST, GET})
    public List<PartnerGroup> getAllPartnerGroup() {
        return partnerGroupService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get agricultural content type information")
    @RequestMapping(value = "/contentType", method = {POST, GET})
    public List<ContentType> getAllContentType() {
        return contentTypeService.findAll();
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
        List<Integer> years = dataService.findDistinctYears();
        List<Map<String, Integer>> ret = new ArrayList<>();
        for (Integer year : years) {
            Map<String, Integer> map = new HashMap<>();
            map.put("id", year);
            map.put("label", year);
            ret.add(map);
        }

        return ret;
    }
}
