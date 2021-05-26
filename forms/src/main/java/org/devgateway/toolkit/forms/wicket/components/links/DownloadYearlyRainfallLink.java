package org.devgateway.toolkit.forms.wicket.components.links;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;
import org.devgateway.toolkit.persistence.service.indicator.rainfall.YearlyRainfallService;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Nadejda Mandrescu
 */
public class DownloadYearlyRainfallLink extends AbstractGeneratedExcelDownloadLink<YearlyRainfall> {
    private static final long serialVersionUID = -1753110031235443491L;

    @SpringBean
    private YearlyRainfallService yearlyRainfallService;

    public DownloadYearlyRainfallLink(String id, IModel<YearlyRainfall> model, Boolean template) {
        super(id, model, template);
    }

    @Override
    protected String getFileName() {
        YearlyRainfall entity = getModelObject();
        return String.format("Cumul de plui - %s.xlsx", entity.getYear());
    }

    @Override
    protected YearlyRainfall getTemplateObject() {
        YearlyRainfall yr = getModelObject();
        return yearlyRainfallService.getExample(yr.getYear());
    }

    @Override
    protected void generate(YearlyRainfall yearlyRainfall, OutputStream outputStream) throws IOException {
        yearlyRainfallService.export(yearlyRainfall, outputStream);
    }
}
