package org.devgateway.toolkit.persistence.service;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.dao.PovertyIndicatorDataset;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.repository.PovertyIndicatorDatasetRepository;
import org.devgateway.toolkit.persistence.repository.PovertyIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.RegionRepository;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel Oliva
 */
@Service("povertyIndicatorImporter")
public class PovertyIndicatorImporter extends AbstractImportService<PovertyIndicator> {

    private static final Logger logger = LoggerFactory.getLogger(PovertyIndicatorImporter.class);

    @Autowired
    private PovertyIndicatorRepository repository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private PovertyIndicatorDatasetRepository datasetRepository;

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
                PovertyIndicator event = new PovertyIndicator();
                event.setRegion(region);
                event.setLocationType(ImportUtils.getStringFromCell(row.getCell(1)));
                event.setGender(ImportUtils.getStringFromCell(row.getCell(2)));
                event.setAge(ImportUtils.getLongFromCell(row.getCell(3)).intValue());
                event.setProfessionalActivity(ImportUtils.getStringFromCell(row.getCell(4)));
                event.setPovertyScore(ImportUtils.getDoubleFromCell(row.getCell(5)));


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
            datasetRepository.saveAndFlush((PovertyIndicatorDataset) dataset);
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
