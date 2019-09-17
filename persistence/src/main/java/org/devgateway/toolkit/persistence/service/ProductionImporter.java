package org.devgateway.toolkit.persistence.service;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Production;
import org.devgateway.toolkit.persistence.dao.ProductionDataset;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.repository.ProductionDatasetRepository;
import org.devgateway.toolkit.persistence.repository.ProductionRepository;
import org.devgateway.toolkit.persistence.repository.RegionRepository;
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
    private RegionRepository regionRepository;

    @Autowired
    private ProductionDatasetRepository datasetRepository;

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
                String regionName = ImportUtils.getStringFromCell(row.getCell(0));
                Region region = getRegion(regionName);

                addData(row, region, "Crop 1", new int[] {1, 2, 3}); //TODO replace with real crop

                addData(row, region, "Crop 2", new int[] {4, 5, 6});

                addData(row, region, "Crop 3", new int[] {7, 8, 9});

            } catch (Exception e) { //Improve exception handling
                logger.error("Error: " + e);
                importResults.setImportOkFlag(false);
                importResults.addError("At row " + rowNumber + " there were an error: " + e.getMessage());
            }
        }
    }

    private void addData(Row row, Region region, String s, int[] rows) {
        Production data = new Production();
        data.setRegion(region);
        data.setCrop(s);
        data.setSurface(ImportUtils.getDoubleFromCell(row.getCell(rows[0])));
        data.setProduction(ImportUtils.getDoubleFromCell(row.getCell(rows[1])));
        data.setYield(ImportUtils.getDoubleFromCell(row.getCell(rows[2])));
        importResults.addDataInstance(data);
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


    private Region getRegion(String regionName) {
        Region region = null;
        if (StringUtils.isNotBlank(regionName)) {
            region = regionRepository.findByName(regionName.toLowerCase());
        }
        if (region == null) {
            throw new RuntimeException("Could not find region named " + region);
        }
        return region;
    }
}
