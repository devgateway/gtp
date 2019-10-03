package org.devgateway.toolkit.web.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.persistence.dao.Department;
import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.dao.categories.AgeGroup;
import org.devgateway.toolkit.persistence.dao.categories.AgriculturalWomenGroup;
import org.devgateway.toolkit.persistence.dao.categories.CropSubType;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.devgateway.toolkit.persistence.dao.categories.Gender;
import org.devgateway.toolkit.persistence.dao.categories.LocationType;
import org.devgateway.toolkit.persistence.dao.categories.LossType;
import org.devgateway.toolkit.persistence.dao.categories.MethodOfEnforcement;
import org.devgateway.toolkit.persistence.dao.categories.ProfessionalActivity;
import org.devgateway.toolkit.persistence.service.category.AgeGroupService;
import org.devgateway.toolkit.persistence.service.category.AgriculturalWomenGroupService;
import org.devgateway.toolkit.persistence.service.category.CropSubTypeService;
import org.devgateway.toolkit.persistence.service.category.CropTypeService;
import org.devgateway.toolkit.persistence.service.category.DepartmentService;
import org.devgateway.toolkit.persistence.service.category.GenderService;
import org.devgateway.toolkit.persistence.service.category.LocationTypeService;
import org.devgateway.toolkit.persistence.service.category.LossTypeService;
import org.devgateway.toolkit.persistence.service.category.MarketService;
import org.devgateway.toolkit.persistence.service.category.MethodOfEnforcementService;
import org.devgateway.toolkit.persistence.service.category.ProfessionalActivityService;
import org.devgateway.toolkit.persistence.service.category.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Daniel Oliva
 */
@RestController
@RequestMapping(value = "/data/filter")
@CrossOrigin
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

    @CrossOrigin
    @ApiOperation(value = "Get regions information")
    @RequestMapping(value = "/region", method = GET)
    public List<Region> getAllRegions() {
        return regionService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get departments information")
    @RequestMapping(value = "/department", method = GET)
    public List<Department> getAllDeparments() {
        return departmentService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get crop types information")
    @RequestMapping(value = "/cropType", method = GET)
    public List<CropType> getAllCropType() {
        return cropTypeService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get crop subtypes information")
    @RequestMapping(value = "/cropSubType", method = GET)
    public List<CropSubType> getAllCropSubType() {
        return cropSubTypeService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get gender information")
    @RequestMapping(value = "/gender", method = GET)
    public List<Gender> getAllGender() {
        return genderService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get market information")
    @RequestMapping(value = "/market", method = GET)
    public List<Market> getAllMarket() {
        return marketService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get loss type information")
    @RequestMapping(value = "/lossType", method = GET)
    public List<LossType> getAllLossType() {
        return lossTypeService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get agricultural women group information")
    @RequestMapping(value = "/awGroup", method = GET)
    public List<AgriculturalWomenGroup> getAllAwGroup() {
        return awGroupService.findAll();
    }

    @CrossOrigin
    @ApiOperation(value = "Get age group information")
    @RequestMapping(value = "/ageGroup", method = GET)
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
    @RequestMapping(value = "/locationType", method = GET)
    public List<LocationType> getAllLocationType() {
        return locService.findAll();
    }


    @CrossOrigin
    @ApiOperation(value = "Get professional activity information")
    @RequestMapping(value = "/professionalActivity", method = GET)
    public List<ProfessionalActivity> getAllProfActivity() {
        return profService.findAll();
    }
}
