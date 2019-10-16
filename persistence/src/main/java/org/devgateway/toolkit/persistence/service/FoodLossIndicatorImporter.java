package org.devgateway.toolkit.persistence.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.FoodLossDataset;
import org.devgateway.toolkit.persistence.dao.FoodLossIndicator;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.categories.CropType;
import org.devgateway.toolkit.persistence.dao.categories.LossType;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.repository.FoodLossDatasetRepository;
import org.devgateway.toolkit.persistence.repository.FoodLossIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.category.CropTypeRepository;
import org.devgateway.toolkit.persistence.repository.category.LossTypeRepository;
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
@Service("foodLossIndicatorImporter")
public class FoodLossIndicatorImporter extends AbstractImportService<FoodLossIndicator> {

    private static final Logger logger = LoggerFactory.getLogger(FoodLossIndicatorImporter.class);

    @Autowired
    private FoodLossIndicatorRepository repository;

    @Autowired
    private FoodLossDatasetRepository datasetRepository;

    @Autowired
    private LossTypeRepository lossTypeRepository;

    @Autowired
    private CropTypeRepository cropTypeRepository;

    private Map<String, Category> lossTypes;
    private Map<String, Category> cropTypes;

    @Override
    protected void generateDataInstanceFromSheet(Sheet sheet) {
        Iterator<Row> rowIterator = sheet.iterator();
        int rowNumber = 0;
        while (rowNumber < 1) {
            rowIterator.next();
            rowNumber++;
        }

        cropTypes = cropTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        cropTypes.putAll(cropTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        lossTypes = lossTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        lossTypes.putAll(lossTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));


        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();
                //Extract data
                FoodLossIndicator data = new FoodLossIndicator();
                data.setYear(ImportUtils.getDoubleFromCell(row.getCell(0)).intValue());
                data.setCropType((CropType) getCategory(row.getCell(1), cropTypes, "Crop"));
                data.setLossType((LossType) getCategory(row.getCell(2), lossTypes, "Loss type"));
                data.setAvgPercentage(ImportUtils.getDoubleFromCell(row.getCell(3)));
                data.setAvgKilograms(ImportUtils.getDoubleFromCell(row.getCell(4)));
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
            datasetRepository.saveAndFlush((FoodLossDataset) dataset);
            importResults.getDataInstances().forEach(data -> {
                data.setDataset(dataset);
            });
            repository.saveAll(importResults.getDataInstances());
            repository.flush();
        }
    }
}
