package org.devgateway.toolkit.persistence.service;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Production;
import org.devgateway.toolkit.persistence.repository.ProductionRepository;
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
                Production event = new Production();
                event.setRegion(ImportUtils.getStringFromCell(row.getCell(0)));

                event.setCrop1Surface(ImportUtils.getDoubleFromCell(row.getCell(1)));
                event.setCrop1Production(ImportUtils.getDoubleFromCell(row.getCell(2)));
                event.setCrop1Yield(ImportUtils.getDoubleFromCell(row.getCell(3)));

                event.setCrop2Surface(ImportUtils.getDoubleFromCell(row.getCell(4)));
                event.setCrop2Production(ImportUtils.getDoubleFromCell(row.getCell(5)));
                event.setCrop2Yield(ImportUtils.getDoubleFromCell(row.getCell(6)));

                event.setCrop3Surface(ImportUtils.getDoubleFromCell(row.getCell(7)));
                event.setCrop3Production(ImportUtils.getDoubleFromCell(row.getCell(8)));
                event.setCrop3Yield(ImportUtils.getDoubleFromCell(row.getCell(9)));

                importResults.addDataInstance(event);
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
            importResults.getDataInstances().forEach(data -> {
                data.setDataset(dataset);
            });
            repository.saveAll(importResults.getDataInstances());
            repository.flush();
        }
    }
}
