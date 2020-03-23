package org.devgateway.toolkit.web.rest.controller.export.ipar;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.devgateway.toolkit.web.rest.controller.filter.ipar.IndicatorFilterPagingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static org.devgateway.toolkit.persistence.util.Constants.LANG_FR;

@RestController
@RequestMapping(value = "/data/indicator")
@CrossOrigin
public class ExcelExportController {

    public static final String EXCEL_EXPORT_XLSX_EN = "data-export.xlsx";
    public static final String EXCEL_EXPORT_XLSX_FR = "exportation-de-donnees.xlsx";

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
        String filename = StringUtils.isNotEmpty(filter.getLang()) && filter.getLang().equalsIgnoreCase(LANG_FR)
                ? EXCEL_EXPORT_XLSX_FR : EXCEL_EXPORT_XLSX_EN;
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.getOutputStream().write(excelGenerator.getExcelDownload(filter, sheet));
    }
}
