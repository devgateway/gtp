package org.devgateway.toolkit.persistence.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.ConsumptionDataset;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Consumption;
import org.devgateway.toolkit.persistence.dao.Department;
import org.devgateway.toolkit.persistence.repository.ConsumptionDatasetRepository;
import org.devgateway.toolkit.persistence.repository.ConsumptionRepository;
import org.devgateway.toolkit.persistence.repository.DepartmentRepository;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

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

    @Override
    protected void generateDataInstanceFromSheet(Sheet sheet) {
        Iterator<Row> rowIterator = sheet.iterator();
        int rowNumber = 0;
        while (rowNumber < 1) {
            rowIterator.next();
            rowNumber++;
        }
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

                addData(row, department, "Sorgho", householdSize, null, 7, 11);

            } catch (Exception e) { //Improve exception handling
                logger.error("Error: " + e);
                importResults.setImportOkFlag(false);
                importResults.addError("At row " + rowNumber + " there were an error: " + e.getMessage());
            }
        }
    }

    private void addData(Row row, Department department, String crop, int householdSize, Integer cropTypePos,
                         int dailyPos, int weeklyPos) {
        Consumption data = new Consumption();
        data.setDepartment(department);
        data.setCrop(crop);
        if (cropTypePos != null) {
            data.setCropType(ImportUtils.getStringFromCell(row.getCell(cropTypePos)));
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
            department = departmentRepository.findByName(departmentName.toLowerCase());
        }
        if (department == null) {
            throw new RuntimeException("Could not find department named " + department);
        }
        return department;
    }
}
