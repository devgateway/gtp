package org.devgateway.toolkit.persistence.service.ipar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.ipar.AgriculturalWomenDataset;
import org.devgateway.toolkit.persistence.dao.ipar.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.ipar.categories.AgriculturalWomenGroup;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dao.ipar.categories.Gender;
import org.devgateway.toolkit.persistence.repository.ipar.AgriculturalWomenDatasetRepository;
import org.devgateway.toolkit.persistence.repository.ipar.AgriculturalWomenIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.AgeGroupRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.AgriculturalWomenGroupRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.CropTypeRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.GenderRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.MethodOfEnforcementRepository;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel Oliva
 */
// @Service("agriculturalWomenIndicatorImporter")
public class AgriculturalWomenIndicatorImporter extends AbstractImportService<AgriculturalWomenIndicator> {

    private static final Logger logger = LoggerFactory.getLogger(AgriculturalWomenIndicatorImporter.class);
    public static final String GENDER = "Gender";
    public static final String GROUP_TYPE = "Group type";
    public static final String GROUP_SUBTYPE = "Group subtype";

    @Autowired
    private AgriculturalWomenIndicatorRepository repository;

    @Autowired
    private AgriculturalWomenDatasetRepository datasetRepository;

    @Autowired
    private AgriculturalWomenGroupRepository awgRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private CropTypeRepository cropTypeRepository;

    @Autowired
    private AgeGroupRepository ageGroupRepository;

    @Autowired
    private MethodOfEnforcementRepository moeRepository;

    private Map<String, Category> genderMap;
    private Map<String, Category> groupMap;
    private Map<String, Category> groupTypeMap = new HashMap<>();

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

        groupMap = awgRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        groupMap.putAll(awgRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        groupTypeMap.putAll(ageGroupRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z)));
        groupTypeMap.putAll(ageGroupRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        groupTypeMap.putAll(cropTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z)));
        groupTypeMap.putAll(cropTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        groupTypeMap.putAll(moeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z)));
        groupTypeMap.putAll(moeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();
                //Extract data
                AgriculturalWomenIndicator data = new AgriculturalWomenIndicator();
                Double yearD = ImportUtils.getDoubleFromCell(row.getCell(0));
                if (yearD == null) {
                    throw new Exception(YEAR_IS_MISSING);
                }
                data.setYear(yearD.intValue());
                data.setGender((Gender) getCategory(row.getCell(1), genderMap, GENDER));
                data.setGroup((AgriculturalWomenGroup) getCategory(row.getCell(2), groupMap, GROUP_TYPE));
                data.setGroupType(getCategory(row.getCell(3), groupTypeMap, GROUP_SUBTYPE));
                data.setPercentage(ImportUtils.getDoubleFromCell(row.getCell(4)));
                data.setUtilizationPercentage(ImportUtils.getDoubleFromCell(row.getCell(5)));
                importResults.addDataInstance(data);

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
            datasetRepository.saveAndFlush((AgriculturalWomenDataset) dataset);
            importResults.getDataInstances().forEach(data -> {
                data.setDataset(dataset);
            });
            repository.saveAll(importResults.getDataInstances());
            repository.flush();
        }
    }
}
