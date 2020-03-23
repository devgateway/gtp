package org.devgateway.toolkit.persistence.service.ipar;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.ipar.Department;
import org.devgateway.toolkit.persistence.dao.ipar.Production;
import org.devgateway.toolkit.persistence.dao.ipar.ProductionDataset;
import org.devgateway.toolkit.persistence.dao.ipar.categories.CropType;
import org.devgateway.toolkit.persistence.repository.ipar.DepartmentRepository;
import org.devgateway.toolkit.persistence.repository.ipar.ProductionDatasetRepository;
import org.devgateway.toolkit.persistence.repository.ipar.ProductionRepository;
import org.devgateway.toolkit.persistence.repository.ipar.category.CropTypeRepository;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel Oliva
 */
@Service("productionImporter")
public class ProductionImporter extends AbstractImportService<Production> {

    private static final Logger logger = LoggerFactory.getLogger(ProductionImporter.class);

    @Autowired
    private ProductionRepository repository;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Autowired
    private ProductionDatasetRepository datasetRepository;

    @Autowired
    private CropTypeRepository cropTypeRepository;

    private Map<String, CropType> cropTypes;

    private Map<String, Department> departments;

    private Pattern campaignPattern = Pattern.compile("(\\d{4})/\\d{4}");

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
        cropTypes.putAll(cropTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();

                //Extract data
                String departmentName = ImportUtils.getStringFromCell(row.getCell(0));

                Production data = new Production();
                data.setDepartment(getDepartment(departmentName));
                data.setYear(getCampaignYear(row.getCell(1)));
                data.setCropType(getCropType(row.getCell(2)));
                data.setSurface(ImportUtils.getDoubleFromCell(row.getCell(3)));
                data.setProduction(ImportUtils.getDoubleFromCell(row.getCell(4)));
                data.setYield(ImportUtils.getDoubleFromCell(row.getCell(5)));

                importResults.addDataInstance(data);

            } catch (Exception e) { //Improve exception handling
                logger.error("Error: " + e);
                importResults.setImportOkFlag(false);
                importResults.addError("At row " + rowNumber + " there was an error: " + e.getMessage());
            }
        }
    }

    private CropType getCropType(Cell cell) {
        String cropName = ImportUtils.getStringFromCell(cell);
        if (StringUtils.isBlank(cropName)) {
            throw new RuntimeException("Crop type is not specified");
        }
        CropType cropType = cropTypes.get(cropName.toLowerCase());
        if (cropType == null) {
            throw new RuntimeException("Unknown crop type " + cropName);
        }
        return cropType;
    }

    private Integer getCampaignYear(Cell cell) {
        String campaign = ImportUtils.getStringFromCell(cell);
        Matcher matcher = campaignPattern.matcher(campaign);
        if (!matcher.matches()) {
            throw new RuntimeException("Invalid campaign " + campaign + ". Example campaign '2018/2019'.");
        }
        return Integer.parseInt(matcher.group(1));
    }

    @Override
    protected void processResults(final Dataset dataset) {
        if (importResults.isImportOkFlag()) {
            datasetRepository.saveAndFlush((ProductionDataset) dataset);
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
