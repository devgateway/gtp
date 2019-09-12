package org.devgateway.toolkit.persistence.service;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.repository.MarketRepository;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel Oliva
 */
@Service("marketImporter")
public class MarketImporter extends AbstractImportService {

    private static final Logger logger = LoggerFactory.getLogger(MarketImporter.class);

    @Autowired
    private MarketRepository repository;

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
                if (!ImportUtils.getBooleanFromCell(row.getCell(9))) {
                    Market data = new Market();
                    data.setRegion(ImportUtils.getStringFromCell(row.getCell(0)));
                    data.setDepartment(ImportUtils.getStringFromCell(row.getCell(1)));
                    data.setMarket(ImportUtils.getStringFromCell(row.getCell(2)));
                    data.setDate(ImportUtils.getLocalDateFromCell(row.getCell(3)));
                    data.setCrop(ImportUtils.getStringFromCell(row.getCell(4)));
                    data.setQuantity(ImportUtils.getDoubleFromCell(row.getCell(5)));
                    data.setSellPrice(ImportUtils.getDoubleFromCell(row.getCell(6)));
                    data.setDetailBuyPrice(ImportUtils.getDoubleFromCell(row.getCell(7)));
                    data.setWholesaleBuyPrice(ImportUtils.getDoubleFromCell(row.getCell(8)));
                    importResults.addDataInstance(data);
                }

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
            repository.saveAll((List<Market>) (List<?>) importResults.getDataInstances());
            repository.flush();
        }
    }
}
