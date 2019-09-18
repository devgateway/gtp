package org.devgateway.toolkit.persistence.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.AgriculturalWomenIndicator;
import org.devgateway.toolkit.persistence.dao.AgriculturalWomenDataset;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Department;
import org.devgateway.toolkit.persistence.dao.categories.AgeGroup;
import org.devgateway.toolkit.persistence.dao.categories.AgriculturalWomenGroup;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.devgateway.toolkit.persistence.dao.categories.Gender;
import org.devgateway.toolkit.persistence.repository.AgriculturalWomenDatasetRepository;
import org.devgateway.toolkit.persistence.repository.AgriculturalWomenIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.DepartmentRepository;
import org.devgateway.toolkit.persistence.repository.category.AgeGroupRepository;
import org.devgateway.toolkit.persistence.repository.category.AgriculturalWomenGroupRepository;
import org.devgateway.toolkit.persistence.repository.category.GenderRepository;
import org.devgateway.toolkit.persistence.repository.category.MethodOfEnforcementRepository;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private AgeGroupRepository ageGroupRepository;

    @Autowired
    private MethodOfEnforcementRepository moeRepository;

    private Map<String, Gender> genderMap;

    private Map<String, AgeGroup> ageGroupMap;

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
        ageGroupMap = ageGroupRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();
                //Extract data
                AgriculturalWomenIndicator data = new AgriculturalWomenIndicator();
                //TODO

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
}
