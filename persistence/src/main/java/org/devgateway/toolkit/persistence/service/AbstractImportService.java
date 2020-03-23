package org.devgateway.toolkit.persistence.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.devgateway.toolkit.persistence.dao.ipar.Data;
import org.devgateway.toolkit.persistence.dao.ipar.Dataset;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.util.ImportResults;
import org.devgateway.toolkit.persistence.util.ImportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.Map;

/**
 * Created by Daniel Oliva
 */
public abstract class AbstractImportService<T extends Data> implements ImportService {

    public static final String YEAR_IS_MISSING = "Unknown Year";
    public static final String DATE_IS_MISSING = "Unknown Date";
    public static final String CROP_TYPE = "Crop type";
    public static final String CROP_SUBTYPE = "Crop subtype";

    private static final Logger logger = LoggerFactory.getLogger(AbstractImportService.class);

    protected ImportResults<T> importResults;

    @Override
    public ImportResults processFile(final Dataset dataset) {
        logger.debug("processing imported file");
        importResults = new ImportResults<>();
        ZipSecureFile.setMinInflateRatio(0.009);
        try {
            FileMetadata file = dataset.getFileMetadata().iterator().next();
            Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(file.getContent().getBytes()));
            Sheet sheet = wb.getSheetAt(0);
            generateDataInstanceFromSheet(sheet);
            processResults(dataset);
        } catch (Exception e) {
            logger.error("error: " + e);
            importResults.setImportOkFlag(false);
            importResults.addError("File extension not supported");
        }
        return importResults;
    }

    protected abstract void generateDataInstanceFromSheet(final Sheet sheet);

    protected abstract void processResults(final Dataset dataset);

    protected Category getCategory(Cell cell, Map<String, Category> map, String categoryName) {
        if (cell != null) {
            String label = ImportUtils.getStringFromCell(cell);
            return getCategory(label, map, categoryName);
        } else {
            throw new RuntimeException(categoryName + " is not specified");
        }
    }

    protected Category getCategory(String label, Map<String, Category> map, String categoryName) {
        if (StringUtils.isBlank(label)) {
            throw new RuntimeException(categoryName + " is not specified");
        }
        Category cat = map.get(label.toLowerCase());
        if (cat == null) {
            throw new RuntimeException("Unknown " + categoryName.toLowerCase() + " " + label);
        }
        return cat;
    }

    protected Integer getIntegerValue(Cell cell, String categoryName) {
        if (cell != null) {
            Long val = ImportUtils.getLongFromCell(cell);
            if (val != null) {
                return val.intValue();
            } else {
                throw new RuntimeException(categoryName + " couldn't be read");
            }
        } else {
            throw new RuntimeException(categoryName + " is not specified");
        }

    }

}
