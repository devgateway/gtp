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

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Daniel Oliva
 */
@Service("marketImporter")
public class MarketImporter extends AbstractImportService {

    private static final Logger logger = LoggerFactory.getLogger(MarketImporter.class);
    public static final int[] MILLET_IDXS = {4, 5, 6, 7};
    public static final String MILLET_LABEL = "millet";
    public static final int[] CORN_IDXS = {8, 9, 10, 11};
    public static final String CORN_LABEL = "corn";

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
                String region = ImportUtils.getStringFromCell(row.getCell(0));
                String department = ImportUtils.getStringFromCell(row.getCell(1));
                String market = ImportUtils.getStringFromCell(row.getCell(2));
                Date date = ImportUtils.getDateFromCell(row.getCell(3));

                //Millet
                Market milletData = getMarketData(region, department, market, date, MILLET_LABEL, row, MILLET_IDXS);
                if (milletData.isValid()) {
                    importResults.addDataInstance(milletData);
                }

                //Corn
                Market cornData = getMarketData(region, department, market, date, CORN_LABEL, row, CORN_IDXS);
                if (cornData.isValid()) {
                    importResults.addDataInstance(cornData);
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

    private Market getMarketData(String region, String department, String market, Date date, String crop, Row row,
                                 int[] cropIdxs) {
        Market data = new Market(region, department, market, date, crop);
        data.setQuantity(ImportUtils.getDoubleFromCell(row.getCell(cropIdxs[0])));
        data.setSellPrice(ImportUtils.getDoubleFromCell(row.getCell(cropIdxs[1])));
        data.setDetailBuyPrice(ImportUtils.getDoubleFromCell(row.getCell(cropIdxs[2])));
        data.setWholesaleBuyPrice(ImportUtils.getDoubleFromCell(row.getCell(cropIdxs[3])));
        return data;
    }
}
