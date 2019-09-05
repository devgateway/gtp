package org.devgateway.toolkit.persistence.service;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.devgateway.toolkit.persistence.dao.Dataset;
import org.devgateway.toolkit.persistence.dao.Event;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.util.ImportResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * Created by Daniel Oliva
 */
public abstract class AbstractImportService implements ImportService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractImportService.class);

    protected ImportResults<Event> importResults;

    @Override
    public ImportResults processFile(final Dataset dataset) {
        logger.debug("processing imported file");
        importResults = new ImportResults();
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
            importResults.addError("File is empty or corrupted");
        }
        return importResults;
    }

    protected abstract void generateDataInstanceFromSheet(final Sheet sheet);

    protected abstract void processResults(final Dataset dataset);

}
