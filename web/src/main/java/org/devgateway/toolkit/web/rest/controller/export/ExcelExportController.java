package org.devgateway.toolkit.web.rest.controller.export;

import io.swagger.annotations.ApiOperation;
import org.devgateway.toolkit.web.rest.controller.filter.IndicatorFilterPagingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "/data/indicator")
@CrossOrigin
public class ExcelExportController {

    @Autowired
    private ExcelGenerator excelGenerator;

    @ApiOperation(value = "Export datasets in Excel format.")
    @RequestMapping(value = "/excelExport", method = RequestMethod.GET)
    public void excelExportGet(@ModelAttribute @Valid final IndicatorFilterPagingRequest filter,
                            final HttpServletResponse response) throws IOException {
        getResponse(filter, response, ExcelGenerator.Indicators.ALL);
    }

    @ApiOperation(value = "Export datasets in Excel format.")
    @RequestMapping(value = "/excelExport", method = RequestMethod.POST)
    public void excelExport(@RequestBody @Valid final IndicatorFilterPagingRequest filter,
                            final HttpServletResponse response) throws IOException {
        getResponse(filter, response, ExcelGenerator.Indicators.ALL);
    }

    @ApiOperation(value = "Export poverty datasets in Excel format.")
    @RequestMapping(value = "/excelExport/poverty", method = RequestMethod.POST)
    public void excelExportPoverty(@RequestBody @Valid final IndicatorFilterPagingRequest filter,
                            final HttpServletResponse response) throws IOException {
        getResponse(filter, response, ExcelGenerator.Indicators.POVERTY);
    }

    @ApiOperation(value = "Export poverty datasets in Excel format.")
    @RequestMapping(value = "/excelExport/poverty", method = RequestMethod.GET)
    public void excelExportPovertyGet(@ModelAttribute @Valid final IndicatorFilterPagingRequest filter,
                               final HttpServletResponse response) throws IOException {
        getResponse(filter, response, ExcelGenerator.Indicators.POVERTY);
    }

    @ApiOperation(value = "Export women datasets in Excel format.")
    @RequestMapping(value = "/excelExport/women", method = RequestMethod.POST)
    public void excelExportWomen(@RequestBody @Valid final IndicatorFilterPagingRequest filter,
                                   final HttpServletResponse response) throws IOException {
        getResponse(filter, response, ExcelGenerator.Indicators.WOMEN);
    }

    @ApiOperation(value = "Export women datasets in Excel format.")
    @RequestMapping(value = "/excelExport/women", method = RequestMethod.GET)
    public void excelExportWomenGet(@ModelAttribute @Valid final IndicatorFilterPagingRequest filter,
                                      final HttpServletResponse response) throws IOException {
        getResponse(filter, response, ExcelGenerator.Indicators.WOMEN);
    }

    @ApiOperation(value = "Export AOI datasets in Excel format.")
    @RequestMapping(value = "/excelExport/aoi", method = RequestMethod.POST)
    public void excelExportAOI(@RequestBody @Valid final IndicatorFilterPagingRequest filter,
                                 final HttpServletResponse response) throws IOException {
        getResponse(filter, response, ExcelGenerator.Indicators.AOI);
    }

    @ApiOperation(value = "Export AOI datasets in Excel format.")
    @RequestMapping(value = "/excelExport/aoi", method = RequestMethod.GET)
    public void excelExportAOIGet(@ModelAttribute @Valid final IndicatorFilterPagingRequest filter,
                                    final HttpServletResponse response) throws IOException {
        getResponse(filter, response, ExcelGenerator.Indicators.AOI);
    }

    @ApiOperation(value = "Export food loss datasets in Excel format.")
    @RequestMapping(value = "/excelExport/foodLoss", method = RequestMethod.POST)
    public void excelExportFoodLoss(@RequestBody @Valid final IndicatorFilterPagingRequest filter,
                                 final HttpServletResponse response) throws IOException {
        getResponse(filter, response, ExcelGenerator.Indicators.FOODLOSS);
    }

    @ApiOperation(value = "Export food loss datasets in Excel format.")
    @RequestMapping(value = "/excelExport/foodLoss", method = RequestMethod.GET)
    public void excelExportFoodLossGet(@ModelAttribute @Valid final IndicatorFilterPagingRequest filter,
                                    final HttpServletResponse response) throws IOException {
        getResponse(filter, response, ExcelGenerator.Indicators.FOODLOSS);
    }
    private void getResponse(@Valid @RequestBody IndicatorFilterPagingRequest filter, HttpServletResponse response,
                             ExcelGenerator.Indicators sheet) throws IOException {
         HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("xcel-export.xlsx", "xcel-export.xlsx");
        response.getOutputStream().write(excelGenerator.getExcelDownload(filter, sheet));

    }
}
