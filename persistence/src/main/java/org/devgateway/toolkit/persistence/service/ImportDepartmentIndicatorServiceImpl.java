package org.devgateway.toolkit.persistence.service;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.devgateway.toolkit.persistence.dao.DepartmentIndicator;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.Department;
import org.devgateway.toolkit.persistence.dao.DepartmentStat;
import org.devgateway.toolkit.persistence.repository.DepartmentIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.DepartmentRepository;
import org.devgateway.toolkit.persistence.repository.DepartmentStatRepository;
import org.devgateway.toolkit.persistence.util.ImportResults;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional
public class ImportDepartmentIndicatorServiceImpl implements ImportDepartmentIndicatorService {

    private static final Logger logger = LoggerFactory.getLogger(ImportDepartmentIndicatorServiceImpl.class);

    private ImportResults<DepartmentStat> importResults;
    private Map<String, Department> departmentMap;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentIndicatorRepository departmentIndicatorRepository;

    @Autowired
    private DepartmentStatRepository departmentStatRepository;

    @Override
    public ImportResults processFile(final DepartmentIndicator dataset) {
        logger.debug("processing imported file");
        importResults = new ImportResults<>();
        ZipSecureFile.setMinInflateRatio(0.009);
        try {
            FileMetadata file = dataset.getFileMetadata().iterator().next();
            Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(file.getContent().getBytes()));
            Sheet sheet = wb.getSheetAt(0);
            generateDataInstanceFromSheet(dataset, sheet);
            processResults(dataset);
        } catch (Exception e) {
            logger.error("error: " + e);
            importResults.setImportOkFlag(false);
            importResults.addError("File extension not supported");
        }
        return importResults;
    }

    protected void generateDataInstanceFromSheet(final DepartmentIndicator dataset, final Sheet sheet) {
        Iterator<Row> rowIterator = sheet.iterator();
        int rowNumber = 0;
        while (rowNumber < 1) {
            rowIterator.next();
            rowNumber++;
        }
        departmentMap = departmentRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));

        Double maxValue = Double.MIN_VALUE;
        Double minValue = Double.MAX_VALUE;

        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();
                if (row.getCell(0) != null) {
                    //Extract data
                    DepartmentStat data = new DepartmentStat();
                    String depName = ImportUtils.getStringFromCell(row.getCell(0));
                    if (depName != null && departmentMap.get(depName.toLowerCase()) != null) {
                        data.setDepartment(departmentMap.get(depName.toLowerCase()));
                    } else {
                        throw new RuntimeException("Department name is not specified or not valid");
                    }
                    Double value = ImportUtils.getDoubleFromCell(row.getCell(1));
                    data.setValue(value);
                    if (value > maxValue) {
                        maxValue = value;
                    }
                    if (value < minValue) {
                        minValue = value;
                    }
                    importResults.addDataInstance(data);
                }
            } catch (Exception e) { //Improve exception handling
                logger.error("Error: " + e);
                importResults.setImportOkFlag(false);
                importResults.addError("At row " + rowNumber + " there was an error: " + e.getMessage());
            }
        }
        dataset.setMaxValue(maxValue);
        dataset.setMinValue(minValue);
    }

    protected void processResults(final DepartmentIndicator dataset) {
        if (importResults.isImportOkFlag()) {
            departmentIndicatorRepository.saveAndFlush(dataset);
            importResults.getDataInstances().forEach(data -> {
                data.setDepartmentIndicator(dataset);
            });
            departmentStatRepository.saveAll(importResults.getDataInstances());
            departmentStatRepository.flush();
        }
    }
}
