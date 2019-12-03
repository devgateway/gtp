package org.devgateway.toolkit.web.rest.controller.export;

import org.apache.poi.ss.usermodel.Workbook;
import org.devgateway.toolkit.persistence.dao.IndicatorMetadata;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dto.AgriculturalWomenDTO;
import org.devgateway.toolkit.persistence.dto.AgricultureOrientationIndexDTO;
import org.devgateway.toolkit.persistence.dto.ExcelFilterDTO;
import org.devgateway.toolkit.persistence.dto.ExcelInfo;
import org.devgateway.toolkit.persistence.dto.FoodLossDTO;
import org.devgateway.toolkit.persistence.repository.RegionRepository;
import org.devgateway.toolkit.persistence.service.IndicatorTranslateService;
import org.devgateway.toolkit.persistence.dto.PovertyDTO;
import org.devgateway.toolkit.persistence.excel.ExcelFile;
import org.devgateway.toolkit.persistence.excel.ExcelFileData;
import org.devgateway.toolkit.persistence.repository.category.CategoryRepository;
import org.devgateway.toolkit.persistence.service.AOIIndicatorService;
import org.devgateway.toolkit.persistence.service.AgriculturalWomenIndicatorService;
import org.devgateway.toolkit.persistence.service.FoodLossIndicatorService;
import org.devgateway.toolkit.persistence.service.IndicatorMetadataService;
import org.devgateway.toolkit.persistence.service.PovertyIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.AgriculturalWomenFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AgriculturalWomenFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.FoodLossFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.IndicatorFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.PovertyFilterState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@CacheConfig(keyGenerator = "genericKeyGenerator", cacheNames = "excelExportCache")
public class ExcelGenerator {

    public static final String AOI_INDICATOR = "AOI Indicator";
    public static final String AGRICULTURAL_WOMEN_INDICATOR = "Agricultural Women Indicator";
    public static final String FOOD_LOSS_INDICATOR = "Food Loss Indicator";
    public static final String POVERTY_INDICATOR = "Poverty Indicator";

    private static final Map<Integer, Category> CATEGORIES = new HashMap<>();
    private static final Map<Integer, Region> REGIONS = new HashMap<>();
    public static final String EMPTY_STR = "";
    public static final int POVERTY_TYPE = 1;
    public static final int AG_WOMAN_TYPE = 2;
    public static final int FOOD_LOSS_TYPE = 3;
    public static final int AOI_TYPE = 4;

    @Autowired
    private AOIIndicatorService aoiIndicatorService;

    @Autowired
    private AgriculturalWomenIndicatorService womenIndicatorService;

    @Autowired
    private FoodLossIndicatorService foodLossIndicatorService;

    @Autowired
    private PovertyIndicatorService povertyIndicatorService;

    @Autowired
    private IndicatorMetadataService indicatorMetadataService;

    public ExcelGenerator(CategoryRepository categoryRepository, RegionRepository regionRepository) {
        categoryRepository.findAll().stream().forEach(cat -> {
            Category c = (Category) cat;
            CATEGORIES.put(c.getId().intValue(), c);
        });
        regionRepository.findAll().stream().forEach(reg -> {
            REGIONS.put(reg.getId().intValue(), reg);
        });
    }

    public byte[] getExcelDownload(final IndicatorFilterPagingRequest req, Indicators sheet) throws IOException {
        List<ExcelInfo> sheetList = new ArrayList<>();
        if (sheet.equals(Indicators.ALL) || sheet.equals(Indicators.POVERTY)) {
            sheetList.add(getPovertyDTOExcelInfo(req));
        }
        if (sheet.equals(Indicators.ALL) || sheet.equals(Indicators.WOMEN)) {
            sheetList.add(getAgriculturalWomenExcelInfo(req));
        }
        if (sheet.equals(Indicators.ALL) || sheet.equals(Indicators.AOI)) {
            sheetList.add(getAOIExcelInfo(req));
        }
        if (sheet.equals(Indicators.ALL) || sheet.equals(Indicators.FOODLOSS)) {
            sheetList.add(getFoodLossExcelInfo(req));
        }

        ExcelFile excelFile = new ExcelFileData(sheetList);
        Workbook workbook = excelFile.createWorkbook();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        byte[] bytes = baos.toByteArray();

        return bytes;
    }

    private ExcelInfo<PovertyDTO> getPovertyDTOExcelInfo(IndicatorFilterPagingRequest filters) {
        PovertyFilterPagingRequest request = new PovertyFilterPagingRequest(filters);
        PovertyFilterState filterState = new PovertyFilterState(request);
        List<PovertyDTO> aoi = povertyIndicatorService.findAll(filterState.getSpecification())
                .stream().map(data -> new PovertyDTO(data, filters.getLang())).collect(Collectors.toList());
        ExcelFilterDTO excelFilter = new ExcelFilterHelper(request, CATEGORIES, REGIONS);
        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findByIndicatorType(POVERTY_TYPE);

        String intro = EMPTY_STR;
        if (indicatorMetadata != null) {
            intro = indicatorMetadata.getIntro(filters.getLang());
        }
        IndicatorTranslateService translator = new IndicatorTranslateService(filters.getLang());

        return (ExcelInfo<PovertyDTO>) new ExcelInfo(POVERTY_INDICATOR, intro, excelFilter, aoi, translator);
    }

    private ExcelInfo<AgricultureOrientationIndexDTO> getAOIExcelInfo(IndicatorFilterPagingRequest filters) {
        AOIFilterPagingRequest request = new AOIFilterPagingRequest(filters);
        AOIFilterState filterState = new AOIFilterState(request);
        List<AgricultureOrientationIndexDTO> aoi = aoiIndicatorService.findAll(filterState.getSpecification())
                .stream().map(data -> new AgricultureOrientationIndexDTO(data, filters.getLang()))
                .collect(Collectors.toList());
        ExcelFilterDTO excelFilter = new ExcelFilterHelper(request, CATEGORIES);
        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findByIndicatorType(AOI_TYPE);

        String intro = EMPTY_STR;
        if (indicatorMetadata != null) {
            intro = indicatorMetadata.getIntro(filters.getLang());
        }
        IndicatorTranslateService translator = new IndicatorTranslateService(filters.getLang());

        return (ExcelInfo<AgricultureOrientationIndexDTO>) new ExcelInfo(AOI_INDICATOR, intro, excelFilter, aoi,
                translator);
    }

    private ExcelInfo<AgriculturalWomenDTO> getAgriculturalWomenExcelInfo(IndicatorFilterPagingRequest filters) {
        AgriculturalWomenFilterPagingRequest request = new AgriculturalWomenFilterPagingRequest(filters);
        AgriculturalWomenFilterState filterState = new AgriculturalWomenFilterState(request);
        List<AgriculturalWomenDTO> women = womenIndicatorService.findAll(filterState.getSpecification())
                .stream().map(data -> new AgriculturalWomenDTO(data, filters.getLang())).collect(Collectors.toList());
        ExcelFilterDTO excelFilter = new ExcelFilterHelper(request, CATEGORIES);
        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findByIndicatorType(AG_WOMAN_TYPE);

        String intro = EMPTY_STR;
        if (indicatorMetadata != null) {
            intro = indicatorMetadata.getIntro(filters.getLang());
        }
        IndicatorTranslateService translator = new IndicatorTranslateService(filters.getLang());

        return (ExcelInfo<AgriculturalWomenDTO>) new ExcelInfo(AGRICULTURAL_WOMEN_INDICATOR,
                intro, excelFilter, women, translator);
    }

    private ExcelInfo<FoodLossDTO> getFoodLossExcelInfo(IndicatorFilterPagingRequest filters) {
        FoodLossFilterPagingRequest request = new FoodLossFilterPagingRequest(filters);
        FoodLossFilterState filterState = new FoodLossFilterState(request);
        List<FoodLossDTO> aoi = foodLossIndicatorService.findAll(filterState.getSpecification())
                .stream().map(data -> new FoodLossDTO(data, filters.getLang())).collect(Collectors.toList());
        ExcelFilterDTO excelFilter = new ExcelFilterHelper(request, CATEGORIES);
        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findByIndicatorType(FOOD_LOSS_TYPE);

        String intro = EMPTY_STR;
        if (indicatorMetadata != null) {
            intro = indicatorMetadata.getIntro(filters.getLang());
        }
        IndicatorTranslateService translator = new IndicatorTranslateService(filters.getLang());

        return (ExcelInfo<FoodLossDTO>) new ExcelInfo(FOOD_LOSS_INDICATOR, intro, excelFilter, aoi, translator);
    }

    enum Indicators {
        ALL,
        POVERTY,
        WOMEN,
        AOI,
        FOODLOSS
    }
}
