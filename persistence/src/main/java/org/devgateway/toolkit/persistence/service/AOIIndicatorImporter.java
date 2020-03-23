package org.devgateway.toolkit.persistence.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.ipar.AgricultureOrientationIndexDataset;
import org.devgateway.toolkit.persistence.dao.ipar.AgricultureOrientationIndexIndicator;
import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dao.ipar.categories.IndexType;
import org.devgateway.toolkit.persistence.repository.AOIDatasetRepository;
import org.devgateway.toolkit.persistence.repository.AOIIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.category.IndexTypeRepository;
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
@Service("aoiIndicatorImporter")
public class AOIIndicatorImporter extends AbstractImportService<AgricultureOrientationIndexIndicator> {

    private static final Logger logger = LoggerFactory.getLogger(AOIIndicatorImporter.class);
    public static final String INDEX_NAME = "Index Name";

    @Autowired
    private AOIIndicatorRepository repository;

    @Autowired
    private AOIDatasetRepository datasetRepository;

    @Autowired
    private IndexTypeRepository indexTypeRepository;

    private Map<String, Category> indexTypeMap;

    @Override
    protected void generateDataInstanceFromSheet(Sheet sheet) {
        Iterator<Row> rowIterator = sheet.iterator();
        int rowNumber = 0;
        while (rowNumber < 1) {
            rowIterator.next();
            rowNumber++;
        }
        indexTypeMap = indexTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        //indexTypeMap.putAll(indexTypeRepository.findAll().stream()
        //        .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();
                if (row.getCell(0) != null) {
                    //Extract data
                    AgricultureOrientationIndexIndicator data = new AgricultureOrientationIndexIndicator();
                    data.setIndexType((IndexType) getCategory(row.getCell(0), indexTypeMap, INDEX_NAME));
                    Double yearD = ImportUtils.getDoubleFromCell(row.getCell(1));
                    if (yearD == null) {
                        throw new Exception(YEAR_IS_MISSING);
                    }
                    data.setYear(yearD.intValue());
                    data.setBudgetedExpenditures(ImportUtils.getDoubleFromCell(row.getCell(2)));
                    data.setDisbursedExpenditures(ImportUtils.getDoubleFromCell(row.getCell(3)));
                    data.setSubsidies(ImportUtils.getDoubleFromCell(row.getCell(4)));
                    data.setVariableType(ImportUtils.getStringFromCell(row.getCell(5)));
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
            datasetRepository.saveAndFlush((AgricultureOrientationIndexDataset) dataset);
            importResults.getDataInstances().forEach(data -> {
                data.setDataset(dataset);
            });
            repository.saveAll(importResults.getDataInstances());
            repository.flush();
        }
    }
}
