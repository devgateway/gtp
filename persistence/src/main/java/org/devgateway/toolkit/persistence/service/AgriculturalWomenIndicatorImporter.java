package org.devgateway.toolkit.persistence.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.dao.AgriculturalWomenDataset;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Department;
import org.devgateway.toolkit.persistence.dao.categories.AgeGroup;
import org.devgateway.toolkit.persistence.dao.categories.AgriculturalWomenGroup;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.devgateway.toolkit.persistence.dao.categories.Gender;
import org.devgateway.toolkit.persistence.repository.AgriculturalWomenDatasetRepository;
import org.devgateway.toolkit.persistence.repository.AgriculturalWomenIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.DepartmentRepository;
import org.devgateway.toolkit.persistence.repository.category.AgeGroupRepository;
import org.devgateway.toolkit.persistence.repository.category.AgriculturalWomenGroupRepository;
import org.devgateway.toolkit.persistence.repository.category.CropTypeRepository;
import org.devgateway.toolkit.persistence.repository.category.GenderRepository;
import org.devgateway.toolkit.persistence.repository.category.MethodOfEnforcementRepository;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Daniel Oliva
 */
@Service("agriculturalWomenIndicatorImporter")
public class AgriculturalWomenIndicatorImporter extends AbstractImportService<AgriculturalWomenIndicator> {

    private static final Logger logger = LoggerFactory.getLogger(AgriculturalWomenIndicatorImporter.class);

    @Autowired
    private AgriculturalWomenIndicatorRepository repository;

    @Autowired
    private DepartmentRepository departmentRepository;

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
        groupMap = awgRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        groupTypeMap.putAll(ageGroupRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z)));
        groupTypeMap.putAll(cropTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));
        groupTypeMap.putAll(moeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();
                //Extract data
                AgriculturalWomenIndicator data = new AgriculturalWomenIndicator();
                data.setYear(ImportUtils.getDoubleFromCell(row.getCell(0)).intValue());
                data.setGender((Gender) getCategory(row.getCell(1), genderMap, "Gender"));
                data.setGroup((AgriculturalWomenGroup) getCategory(row.getCell(2), groupMap, "Group type"));
                data.setGroupType(getCategory(row.getCell(3), groupTypeMap, "Group type"));
                data.setPercentage(ImportUtils.getDoubleFromCell(row.getCell(4)));
                data.setUtilizationPercentage(ImportUtils.getDoubleFromCell(row.getCell(5)));
                importResults.addDataInstance(data);

            } catch (Exception e) { //Improve exception handling
                logger.error("Error: " + e);
                importResults.setImportOkFlag(false);
                importResults.addError("At row " + rowNumber + " there were an error: " + e.getMessage());
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

    private Category getCategory(Cell cell, Map<String, Category> map, String categoryName) {
        String label = ImportUtils.getStringFromCell(cell);
        if (StringUtils.isBlank(label)) {
            throw new RuntimeException(categoryName + " is not specified");
        }
        Category cat = map.get(label.toLowerCase());
        if (cat == null) {
            throw new RuntimeException("Unknown " + categoryName.toLowerCase() + " " + label);
        }
        return cat;
    }
}
