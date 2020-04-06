package org.devgateway.toolkit.persistence.service.ipar;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.ipar.PovertyDataset;
import org.devgateway.toolkit.persistence.dao.ipar.PovertyIndicator;
import org.devgateway.toolkit.persistence.dao.ipar.Region;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dao.ipar.categories.Gender;
import org.devgateway.toolkit.persistence.dao.ipar.categories.LocationType;
import org.devgateway.toolkit.persistence.dao.ipar.categories.PovertyLevel;
import org.devgateway.toolkit.persistence.dao.ipar.categories.ProfessionalActivity;
import org.devgateway.toolkit.persistence.repository.ipar.PovertyDatasetRepository;
import org.devgateway.toolkit.persistence.repository.ipar.PovertyIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.GenderRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.LocationTypeRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.PovertyLevelRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.ProfessionalActivityRepository;
import org.devgateway.toolkit.persistence.service.ipar.category.RegionService;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Daniel Oliva
 */
// @Service("povertyIndicatorImporter")
public class PovertyIndicatorImporter extends AbstractImportService<PovertyIndicator> {

    private static final Logger logger = LoggerFactory.getLogger(PovertyIndicatorImporter.class);

    @Autowired
    private PovertyIndicatorRepository repository;

    @Autowired
    private RegionService regionService;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private ProfessionalActivityRepository profActRepository;

    @Autowired
    private LocationTypeRepository locTypeRepository;

    @Autowired
    private PovertyLevelRepository povLevelRepository;

    @Autowired
    private PovertyDatasetRepository datasetRepository;

    private Map<String, Category> genderMap;
    private Map<String, Category> profActMap;
    private Map<String, Category> locTypeMap;
    private Map<String, Category> povLevelMap;

    @Override
    protected void generateDataInstanceFromSheet(Sheet sheet) {
        Iterator<Row> rowIterator = sheet.iterator();
        int rowNumber = 0;
        while (rowNumber < 1) {
            rowIterator.next();
            rowNumber++;
        }
        genderMap = genderRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        genderMap.putAll(genderRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        profActMap = profActRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        profActMap.putAll(profActRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        locTypeMap = locTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        locTypeMap.putAll(locTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        povLevelMap = povLevelRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        povLevelMap.putAll(povLevelRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));


        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();
                //Extract data
                if (row.getCell(0) != null) {
                    String regionName = ImportUtils.getStringFromCell(row.getCell(1));
                    Region region = getRegion(regionName);
                    PovertyIndicator data = new PovertyIndicator();
                    Double yearD = ImportUtils.getDoubleFromCell(row.getCell(0));
                    if (yearD == null) {
                        throw new Exception(YEAR_IS_MISSING);
                    }
                    data.setYear(yearD.intValue());
                    data.setRegion(region);
                    data.setLocationType((LocationType) getCategory(row.getCell(2), locTypeMap, "Location Type"));
                    data.setGender((Gender) getCategory(row.getCell(3), genderMap, "Gender"));
                    data.setAge(getIntegerValue(row.getCell(4), "Age"));
                    data.setProfessionalActivity((ProfessionalActivity) getCategory(row.getCell(5), profActMap,
                            "Professional activity"));
                    data.setPovertyScore(ImportUtils.getDoubleFromCell(row.getCell(6)));
                    data.setPovertyLevel((PovertyLevel) getCategory(row.getCell(7), povLevelMap,
                            "Poverty level"));

                    importResults.addDataInstance(data);
                }
            } catch (Exception e) { //Improve exception handling
                    logger.error("Error: " + e);
                importResults.setImportOkFlag(false);
                importResults.addError("At row " + rowNumber + " there was an error: " + e.getMessage());
            }
        }
    }

    @Override
    protected void processResults(final Dataset dataset) {
        if (importResults.isImportOkFlag()) {
            datasetRepository.saveAndFlush((PovertyDataset) dataset);
            importResults.getDataInstances().forEach(data -> {
                data.setDataset(dataset);
            });
            repository.saveAll(importResults.getDataInstances());
            repository.flush();
        }
    }


    private Region getRegion(String regionName) {
        Region region = null;
        if (StringUtils.isNotBlank(regionName)) {
            region = regionService.findByName(regionName.toLowerCase());
        }
        if (region == null) {
            throw new RuntimeException("Could not find region named " + regionName);
        }
        return region;
    }
}
