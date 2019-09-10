package org.devgateway.toolkit.persistence.service;

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

import java.util.Iterator;
import java.util.List;

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
                Market event = new Market();
                event.setRegion(ImportUtils.getStringFromCell(row.getCell(0)));
                event.setDepartment(ImportUtils.getStringFromCell(row.getCell(1)));
                event.setMarket(ImportUtils.getStringFromCell(row.getCell(2)));
                event.setDate(ImportUtils.getDateFromCell(row.getCell(3)));
                event.setMilletQuantity(ImportUtils.getDoubleFromCell(row.getCell(4)));
                event.setMilletSellPrice(ImportUtils.getDoubleFromCell(row.getCell(5)));
                event.setMilletDetailBuyPrice(ImportUtils.getDoubleFromCell(row.getCell(6)));
                event.setMilletWholesaleBuyPrice(ImportUtils.getDoubleFromCell(row.getCell(7)));
                event.setCornQuantity(ImportUtils.getDoubleFromCell(row.getCell(8)));
                event.setCornSellPrice(ImportUtils.getDoubleFromCell(row.getCell(9)));
                event.setCornDetailBuyPrice(ImportUtils.getDoubleFromCell(row.getCell(10)));
                event.setCornWholesaleBuyPrice(ImportUtils.getDoubleFromCell(row.getCell(11)));

                importResults.addDataInstance(event);
            } catch (Exception e) { //Improve exception handling
                logger.error("Error: " + e);
                importResults.setImportOkFlag(false);
                importResults.addError("At row "+ rowNumber + " there were an error: " + e.getMessage());
            }
        }
    }

    @Override
    protected void processResults(final Dataset dataset) {
        if (importResults.isImportOkFlag()) {
            importResults.getDataInstances().forEach(data -> {
                data.setDataset(dataset);
            });
            repository.saveAll((List<Market>)(List<?>) importResults.getDataInstances());
            repository.flush();
        }
    }
}
