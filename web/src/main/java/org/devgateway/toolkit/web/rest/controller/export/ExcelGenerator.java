package org.devgateway.toolkit.web.rest.controller.export;

import org.apache.poi.ss.usermodel.Workbook;
import org.devgateway.toolkit.persistence.dto.AgricultureOrientationIndexDTO;
import org.devgateway.toolkit.persistence.dto.ExcelFilterDTO;
import org.devgateway.toolkit.persistence.dto.ExcelInfo;
import org.devgateway.toolkit.persistence.excel.ExcelFile;
import org.devgateway.toolkit.persistence.excel.ExcelFileData;
import org.devgateway.toolkit.persistence.service.AOIIndicatorService;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterPagingRequest;
import org.devgateway.toolkit.web.rest.controller.filter.AOIFilterState;
import org.devgateway.toolkit.web.rest.controller.filter.DefaultFilterPagingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(keyGenerator = "genericKeyGenerator", cacheNames = "excelExportCache")
public class ExcelGenerator {

    @Autowired
    private AOIIndicatorService aoiIndicatorService;

    public byte[] getExcelDownload(final DefaultFilterPagingRequest req) throws IOException {

        AOIFilterPagingRequest aoiReq = new AOIFilterPagingRequest(req);
        AOIFilterState filterState = new AOIFilterState(aoiReq);
        List<AgricultureOrientationIndexDTO> aoi = aoiIndicatorService.findAll(filterState.getSpecification())
                .stream().map(data -> new AgricultureOrientationIndexDTO(data)).collect(Collectors.toList());
        ExcelFilterDTO filters = new ExcelFilterHelper(aoiReq);
        ExcelInfo<AgricultureOrientationIndexDTO> info = new ExcelInfo("AOI Indicator", "Some intro",
                filters, aoi, null);

        ExcelFile file = new ExcelFileData(info);
        Workbook workbook = file.createWorkbook();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        byte[] bytes = baos.toByteArray();

        return bytes;
    }
}
