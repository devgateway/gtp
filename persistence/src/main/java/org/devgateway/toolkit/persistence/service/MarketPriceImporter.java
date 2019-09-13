package org.devgateway.toolkit.persistence.service;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Market;
import org.devgateway.toolkit.persistence.dao.MarketPrice;
import org.devgateway.toolkit.persistence.repository.MarketPriceRepository;
import org.devgateway.toolkit.persistence.repository.MarketRepository;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel Oliva
 */
@Service("marketPriceImporter")
public class MarketPriceImporter extends AbstractImportService<MarketPrice> {

    private static final Logger logger = LoggerFactory.getLogger(MarketPriceImporter.class);

    @Autowired
    private MarketPriceRepository repository;

    @Autowired
    private MarketRepository marketRepository;

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
                    MarketPrice data = new MarketPrice();
                    String departmentName = ImportUtils.getStringFromCell(row.getCell(1));
                    String marketName = ImportUtils.getStringFromCell(row.getCell(2));
                    data.setMarket(getMarket(departmentName, marketName));
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

    /**
     * Market names are unique within a department.
     * TODO create market if not found?
     */
    private Market getMarket(String departmentName, String marketName) {
        Market market = null;
        if (StringUtils.isNotBlank(departmentName) && StringUtils.isNotBlank(marketName)) {
            market = marketRepository.findByName(departmentName.toLowerCase(), marketName.toLowerCase());
        }
        if (market == null) {
            throw new RuntimeException("Could not find market named " + marketName + " in "
                    + departmentName + " department");
        }
        return market;
    }

    @Override
    protected void processResults(final Dataset dataset) {
        if (importResults.isImportOkFlag()) {
            importResults.getDataInstances().forEach(data -> data.setDataset(dataset));
            repository.saveAll(importResults.getDataInstances());
            repository.flush();
        }
    }
}
