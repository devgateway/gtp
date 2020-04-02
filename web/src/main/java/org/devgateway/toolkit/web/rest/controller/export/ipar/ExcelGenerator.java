package org.devgateway.toolkit.web.rest.controller.export.ipar;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.devgateway.toolkit.persistence.dao.ipar.IndicatorMetadata;
import org.devgateway.toolkit.persistence.dao.ipar.Region;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dto.ipar.AgriculturalWomenDTO;
import org.devgateway.toolkit.persistence.dto.ipar.AgricultureOrientationIndexDTO;
import org.devgateway.toolkit.persistence.dto.ipar.ExcelFilterDTO;
import org.devgateway.toolkit.persistence.dto.ipar.ExcelInfo;
import org.devgateway.toolkit.persistence.dto.ipar.FoodLossDTO;
import org.devgateway.toolkit.persistence.repository.ipar.RegionRepository;
import org.devgateway.toolkit.persistence.service.ipar.IndicatorTranslateService;
import org.devgateway.toolkit.persistence.dto.ipar.PovertyDTO;
import org.devgateway.toolkit.persistence.excel.ExcelFile;
import org.devgateway.toolkit.persistence.excel.ExcelFileData;
import org.devgateway.toolkit.persistence.repository.category.CategoryRepository;
import org.devgateway.toolkit.persistence.service.ipar.AOIIndicatorService;
import org.devgateway.toolkit.persistence.service.ipar.AgriculturalWomenIndicatorService;
import org.devgateway.toolkit.persistence.service.ipar.FoodLossIndicatorService;
import org.devgateway.toolkit.persistence.service.ipar.IndicatorMetadataService;
import org.devgateway.toolkit.persistence.service.ipar.PovertyIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.AOIFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.AOIFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.AgriculturalWomenFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.AgriculturalWomenFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.FoodLossFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.FoodLossFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.IndicatorFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.PovertyFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.PovertyFilterState;
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

import static org.devgateway.toolkit.persistence.util.Constants.EMPTY_STRING;
import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

// @Service
@CacheConfig(keyGenerator = "genericKeyGenerator", cacheNames = "excelExportCache")
public class ExcelGenerator {

    public static final String AOI_INDICATOR = "AOI Indicator";
    public static final String AGRICULTURAL_WOMEN_INDICATOR = "Agricultural Women Indicator";
    public static final String FOOD_LOSS_INDICATOR = "Post-Harvest Loss";
    public static final String POVERTY_INDICATOR = "Poverty Indicator";
    public static final String NO_DATA_LABEL = "There are no records to export";

    public static final String AOI_INDICATOR_FR = "Indice d'Orientation Agricole";
    public static final String AGRICULTURAL_WOMEN_INDICATOR_FR = "Femmes dans le Secteur Agricole";
    public static final String FOOD_LOSS_INDICATOR_FR = "Pertes Alimentaires";
    public static final String POVERTY_INDICATOR_FR = "Pauvreté";
    public static final String NO_DATA_LABEL_FR = "Aucun enregistrement/fichier à télécharger";

    private static final Map<Integer, Category> CATEGORIES = new HashMap<>();
    private static final Map<Integer, Region> REGIONS = new HashMap<>();
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
        // categoryRepository.findAllFetchingLocalizedLabels().stream().forEach(cat -> {
        categoryRepository.findAll().stream().forEach(cat -> {
            Category c = (Category) cat;
            c.getLocalizedLabels().size();
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

    private ExcelInfo<PovertyDTO> getPovertyDTOExcelInfo(final IndicatorFilterPagingRequest filters) {
        PovertyFilterPagingRequest request = new PovertyFilterPagingRequest(filters);
        PovertyFilterState filterState = new PovertyFilterState(request);
        List<PovertyDTO> aoi = povertyIndicatorService.findAll(filterState.getSpecification())
                .stream().map(data -> new PovertyDTO(data, filters.getLang())).collect(Collectors.toList());
        ExcelFilterDTO excelFilter = new ExcelFilterHelper(request, CATEGORIES, REGIONS);
        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findByIndicatorType(POVERTY_TYPE);

        String intro = EMPTY_STRING;
        if (indicatorMetadata != null) {
            intro = indicatorMetadata.getIntro(filters.getLang());
        }
        IndicatorTranslateService translator = new IndicatorTranslateService(filters.getLang());
        String sheetName = getLabel(filters.getLang(), POVERTY_INDICATOR, POVERTY_INDICATOR_FR);
        String noDataLabel = getLabel(filters.getLang(), NO_DATA_LABEL, NO_DATA_LABEL_FR);

        return (ExcelInfo<PovertyDTO>) new ExcelInfo(sheetName, intro, excelFilter, aoi, translator, noDataLabel);
    }

    private String getLabel(String lang, String engLabel, String frLabel) {
        String ret = engLabel;
        if (StringUtils.isNotEmpty(lang) && lang.equalsIgnoreCase(LANG_FR)) {
            ret = frLabel;
        }
        return ret;
    }

    private ExcelInfo<AgricultureOrientationIndexDTO> getAOIExcelInfo(final IndicatorFilterPagingRequest filters) {
        AOIFilterPagingRequest request = new AOIFilterPagingRequest(filters);
        AOIFilterState filterState = new AOIFilterState(request);
        List<AgricultureOrientationIndexDTO> aoi = aoiIndicatorService.findAll(filterState.getSpecification())
                .stream().map(data -> new AgricultureOrientationIndexDTO(data, filters.getLang()))
                .collect(Collectors.toList());
        ExcelFilterDTO excelFilter = new ExcelFilterHelper(request, CATEGORIES);
        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findByIndicatorType(AOI_TYPE);

        String intro = EMPTY_STRING;
        if (indicatorMetadata != null) {
            intro = indicatorMetadata.getIntro(filters.getLang());
        }
        IndicatorTranslateService translator = new IndicatorTranslateService(filters.getLang());

        String sheetName = getLabel(filters.getLang(), AOI_INDICATOR, AOI_INDICATOR_FR);
        String noDataLabel = getLabel(filters.getLang(), NO_DATA_LABEL, NO_DATA_LABEL_FR);
        return (ExcelInfo<AgricultureOrientationIndexDTO>) new ExcelInfo(sheetName, intro, excelFilter, aoi,
                translator, noDataLabel);
    }

    private ExcelInfo<AgriculturalWomenDTO> getAgriculturalWomenExcelInfo(final IndicatorFilterPagingRequest filters) {
        AgriculturalWomenFilterPagingRequest request = new AgriculturalWomenFilterPagingRequest(filters);
        AgriculturalWomenFilterState filterState = new AgriculturalWomenFilterState(request);
        List<AgriculturalWomenDTO> women = womenIndicatorService.findAll(filterState.getSpecification())
                .stream().map(data -> new AgriculturalWomenDTO(data, filters.getLang())).collect(Collectors.toList());
        ExcelFilterDTO excelFilter = new ExcelFilterHelper(request, CATEGORIES);
        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findByIndicatorType(AG_WOMAN_TYPE);

        String intro = EMPTY_STRING;
        if (indicatorMetadata != null) {
            intro = indicatorMetadata.getIntro(filters.getLang());
        }
        IndicatorTranslateService translator = new IndicatorTranslateService(filters.getLang());

        String sheetName = getLabel(filters.getLang(), AGRICULTURAL_WOMEN_INDICATOR, AGRICULTURAL_WOMEN_INDICATOR_FR);
        String noDataLabel = getLabel(filters.getLang(), NO_DATA_LABEL, NO_DATA_LABEL_FR);

        return (ExcelInfo<AgriculturalWomenDTO>) new ExcelInfo(sheetName, intro, excelFilter, women, translator,
                noDataLabel);
    }

    private ExcelInfo<FoodLossDTO> getFoodLossExcelInfo(final IndicatorFilterPagingRequest filters) {
        FoodLossFilterPagingRequest request = new FoodLossFilterPagingRequest(filters);
        FoodLossFilterState filterState = new FoodLossFilterState(request);
        List<FoodLossDTO> aoi = foodLossIndicatorService.findAll(filterState.getSpecification())
                .stream().map(data -> new FoodLossDTO(data, filters.getLang())).collect(Collectors.toList());
        ExcelFilterDTO excelFilter = new ExcelFilterHelper(request, CATEGORIES);
        IndicatorMetadata indicatorMetadata = indicatorMetadataService.findByIndicatorType(FOOD_LOSS_TYPE);

        String intro = EMPTY_STRING;
        if (indicatorMetadata != null) {
            intro = indicatorMetadata.getIntro(filters.getLang());
        }
        IndicatorTranslateService translator = new IndicatorTranslateService(filters.getLang());

        String sheetName = getLabel(filters.getLang(), FOOD_LOSS_INDICATOR, FOOD_LOSS_INDICATOR_FR);
        String noDataLabel = getLabel(filters.getLang(), NO_DATA_LABEL, NO_DATA_LABEL_FR);

        return (ExcelInfo<FoodLossDTO>) new ExcelInfo(sheetName, intro, excelFilter, aoi, translator, noDataLabel);
    }

    enum Indicators {
        ALL,
        POVERTY,
        WOMEN,
        AOI,
        FOODLOSS
    }
}