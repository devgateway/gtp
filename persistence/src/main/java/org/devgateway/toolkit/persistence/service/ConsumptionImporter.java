package org.devgateway.toolkit.persistence.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.ConsumptionDataset;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Consumption;
import org.devgateway.toolkit.persistence.dao.Department;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dao.categories.CropSubType;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.devgateway.toolkit.persistence.repository.ConsumptionDatasetRepository;
import org.devgateway.toolkit.persistence.repository.ConsumptionRepository;
import org.devgateway.toolkit.persistence.repository.DepartmentRepository;
import org.devgateway.toolkit.persistence.repository.category.CropSubTypeRepository;
import org.devgateway.toolkit.persistence.repository.category.CropTypeRepository;
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
@Service("consumptionImporter")
public class ConsumptionImporter extends AbstractImportService<Consumption> {

    private static final Logger logger = LoggerFactory.getLogger(ConsumptionImporter.class);

    @Autowired
    private ConsumptionRepository repository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ConsumptionDatasetRepository datasetRepository;

    @Autowired
    private CropTypeRepository cropTypeRepository;

    @Autowired
    private CropSubTypeRepository cropSubTypeRepository;

    private Map<String, Department> departments;

    private Map<String, Category> cropTypes;

    private Map<String, Category> cropSubTypes;

    @Override
    protected void generateDataInstanceFromSheet(Sheet sheet) {
        Iterator<Row> rowIterator = sheet.iterator();
        int rowNumber = 0;
        while (rowNumber < 1) {
            rowIterator.next();
            rowNumber++;
        }

        departments = departmentRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getName().toLowerCase(), z -> z));

        cropTypes = cropTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));

        cropSubTypes = cropSubTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z));

        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();
                //Extract data
                String departmentName = ImportUtils.getStringFromCell(row.getCell(1));
                Department department = getDepartment(departmentName);
                int householdSize = ImportUtils.getDoubleFromCell(row.getCell(4)).intValue();

                addData(row, department, "Rice", householdSize, 3, 2, 8);

                addData(row, department, "Millet", householdSize, null, 5, 9);

                addData(row, department, "Corn", householdSize, null, 6, 10);

                addData(row, department, "Sorghum", householdSize, null, 7, 11);

            } catch (Exception e) { //Improve exception handling
                logger.error("Error: " + e);
                importResults.setImportOkFlag(false);
                importResults.addError("At row " + rowNumber + " there were an error: " + e.getMessage());
            }
        }
    }

    private void addData(Row row, Department department, String crop, int householdSize, Integer cropSubTypePos,
                         int dailyPos, int weeklyPos) {
        Consumption data = new Consumption();
        data.setDepartment(department);
        data.setCropType((CropType) getCategory(crop, cropTypes, "Crop type"));
        if (cropSubTypePos != null) {
            String subType = ImportUtils.getStringFromCell(row.getCell(cropSubTypePos));
            if (StringUtils.isNotEmpty(subType)) {
                data.setCropSubType((CropSubType) getCategory(subType, cropSubTypes, "Crop subtype"));
            }
        }
        data.setHouseholdSize(householdSize);
        data.setDailyConsumption(ImportUtils.getDoubleFromCell(row.getCell(dailyPos)));
        data.setWeeklyConsumption(ImportUtils.getDoubleFromCell(row.getCell(weeklyPos)));
        importResults.addDataInstance(data);
    }

    @Override
    protected void processResults(final Dataset dataset) {
        if (importResults.isImportOkFlag()) {
            datasetRepository.saveAndFlush((ConsumptionDataset) dataset);
            importResults.getDataInstances().forEach(data -> {
                data.setDataset(dataset);
            });
            repository.saveAll(importResults.getDataInstances());
            repository.flush();
        }
    }


    private Department getDepartment(String departmentName) {
        Department department = null;
        if (StringUtils.isNotBlank(departmentName)) {
            department = departments.get(departmentName.toLowerCase());
        }
        if (department == null) {
            throw new RuntimeException("Could not find department named " + departmentName);
        }
        return department;
    }
}
