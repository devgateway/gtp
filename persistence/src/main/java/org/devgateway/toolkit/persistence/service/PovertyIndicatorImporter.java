package org.devgateway.toolkit.persistence.service;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.PovertyDataset;
import org.devgateway.toolkit.persistence.dao.PovertyIndicator;
import org.devgateway.toolkit.persistence.dao.Region;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.dao.categories.Gender;
import org.devgateway.toolkit.persistence.dao.categories.LocationType;
import org.devgateway.toolkit.persistence.dao.categories.ProfessionalActivity;
import org.devgateway.toolkit.persistence.repository.PovertyDatasetRepository;
import org.devgateway.toolkit.persistence.repository.PovertyIndicatorRepository;
import org.devgateway.toolkit.persistence.repository.category.GenderRepository;
import org.devgateway.toolkit.persistence.repository.category.LocationTypeRepository;
import org.devgateway.toolkit.persistence.repository.category.ProfessionalActivityRepository;
import org.devgateway.toolkit.persistence.service.category.RegionService;
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
    private RegionService regionService;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private ProfessionalActivityRepository profActRepository;

    @Autowired
    private LocationTypeRepository locTypeRepository;

    @Autowired
    private PovertyDatasetRepository datasetRepository;

    private Map<String, Category> genderMap;
    private Map<String, Category> profActMap;
    private Map<String, Category> locTypeMap;

    @Override
    protected void generateDataInstanceFromSheet(Sheet sheet) {
        Iterator<Row> rowIterator = sheet.iterator();
        int rowNumber = 0;
        while (rowNumber < 1) {
            rowIterator.next();
            rowNumber++;
        }
        genderMap = genderRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        genderMap.putAll(genderRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        profActMap = profActRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        profActMap.putAll(profActRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));

        locTypeMap = locTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabel().toLowerCase(), z -> z));
        locTypeMap.putAll(locTypeRepository.findAll().stream()
                .collect(Collectors.toMap(c -> c.getLabelFr().toLowerCase(), z -> z)));


        while (rowIterator.hasNext()) {
            try {
                rowNumber++;
                Row row = rowIterator.next();
                //Extract data
                String regionName = ImportUtils.getStringFromCell(row.getCell(1));
                Region region = getRegion(regionName);
                PovertyIndicator data = new PovertyIndicator();
                data.setYear(ImportUtils.getDoubleFromCell(row.getCell(0)).intValue());
                data.setRegion(region);
                data.setLocationType((LocationType) getCategory(row.getCell(2), locTypeMap, "Location Type"));
                data.setGender((Gender) getCategory(row.getCell(3), genderMap, "Gender"));
                data.setAge(ImportUtils.getLongFromCell(row.getCell(4)).intValue());
                data.setProfessionalActivity((ProfessionalActivity) getCategory(row.getCell(5), profActMap,
                        "Professional activity"));
                data.setPovertyScore(ImportUtils.getDoubleFromCell(row.getCell(6)));


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
            datasetRepository.saveAndFlush((PovertyDataset) dataset);
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
            region = regionService.findByName(regionName.toLowerCase());
        }
        if (region == null) {
            throw new RuntimeException("Could not find region named " + region);
        }
        return region;
    }
}
