package org.devgateway.toolkit.web.rest.controller.export;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.web.rest.controller.filter.DefaultFilterPagingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "/data/indicator")
public class ExcelExportController {

    @Autowired
    private ExcelGenerator excelGenerator;

    @ApiOperation(value = "Export releases in Excel format.")
    @RequestMapping(value = "/excelExport", method = RequestMethod.GET)
    public void excelExportGet(@ModelAttribute @Valid final DefaultFilterPagingRequest filter,
                            final HttpServletResponse response) throws IOException {
        getResponse(filter, response);

    }

    @ApiOperation(value = "Export releases in Excel format.")
    @RequestMapping(value = "/excelExport", method = RequestMethod.POST)
    public void excelExport(@RequestBody @Valid final DefaultFilterPagingRequest filter,
                            final HttpServletResponse response) throws IOException {
        getResponse(filter, response);

    }

    private void getResponse(@Valid @RequestBody DefaultFilterPagingRequest filter, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + "excel-export.xlsx");
        response.getOutputStream().write(excelGenerator.getExcelDownload(filter));
    }
}
