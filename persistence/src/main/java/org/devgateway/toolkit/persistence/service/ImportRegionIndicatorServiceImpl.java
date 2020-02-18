package org.devgateway.toolkit.persistence.service;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.dao.RegionIndicator;
import org.devgateway.toolkit.persistence.dao.RegionStat;
import org.devgateway.toolkit.persistence.repository.RegionIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.RegionRepository;
import org.devgateway.toolkit.persistence.repository.RegionStatRepository;
import org.devgateway.toolkit.persistence.util.ImportResults;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "servicesCache")
@Transactional
public class ImportRegionIndicatorServiceImpl implements ImportRegionIndicatorService {

    private static final Logger logger = LoggerFactory.getLogger(ImportRegionIndicatorServiceImpl.class);

    private ImportResults<RegionStat> importResults;
    private Map<String, Region> regionMap;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RegionIndicatorRepository regionIndicatorRepository;

    @Autowired
    private RegionStatRepository regionStatRepository;

    @Override
    public ImportResults processFile(final RegionIndicator dataset) {
        logger.debug("processing imported file");
        importResults = new ImportResults<>();
        ZipSecureFile.setMinInflateRatio(0.009);
        try {
            FileMetadata file = dataset.getFileMetadata().iterator().next();
            Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(file.getContent().getBytes()));
            Sheet sheet = wb.getSheetAt(0);
            generateDataInstanceFromSheet(dataset, sheet);
            processResults(dataset);
        } catch (Exception e) {
            logger.error("error: " + e);
            importResults.setImportOkFlag(false);
            importResults.addError("File extension not supported");
        }
        return importResults;
    }

    protected void generateDataInstanceFromSheet(final RegionIndicator dataset, final Sheet sheet) {
        Iterator<Row> rowIterator = sheet.iterator();
        int rowNumber = 0;
        while (rowNumber < 1) {
            rowIterator.next();
            rowNumber++;
        }
        regionMap = regionRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));

        Double maxValue = Double.MIN_VALUE;
        Double minValue = Double.MAX_VALUE;

        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();
                if (row.getCell(0) != null) {
                    //Extract data
                    RegionStat data = new RegionStat();
                    String regionName = ImportUtils.getStringFromCell(row.getCell(0));
                    if (regionName != null && regionMap.get(regionName.toLowerCase()) != null) {
                        data.setRegion(regionMap.get(regionName.toLowerCase()));
                    } else {
                        throw new RuntimeException("Region name is not specified or not valid");
                    }
                    Double value = ImportUtils.getDoubleFromCell(row.getCell(1));
                    data.setValue(value);
                    if (value > maxValue) {
                        maxValue = value;
                    }
                    if (value < minValue) {
                        minValue = value;
                    }
                    importResults.addDataInstance(data);
                }
            } catch (Exception e) { //Improve exception handling
                logger.error("Error: " + e);
                importResults.setImportOkFlag(false);
                importResults.addError("At row " + rowNumber + " there was an error: " + e.getMessage());
            }
        }
        dataset.setMaxValue(maxValue);
        dataset.setMinValue(minValue);
    }

    protected void processResults(final RegionIndicator dataset) {
        if (importResults.isImportOkFlag()) {
            regionIndicatorRepository.saveAndFlush(dataset);
            importResults.getDataInstances().forEach(data -> {
                data.setRegionIndicator(dataset);
            });
            regionStatRepository.saveAll(importResults.getDataInstances());
            regionStatRepository.flush();
        }
    }
}
