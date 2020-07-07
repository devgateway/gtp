package org.devgateway.toolkit.forms.wicket.components.links;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dto.MonthDTO;
import org.devgateway.toolkit.persistence.service.indicator.DecadalRainfallService;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Nadejda Mandrescu
 */
public class DownloadDecadalRainfallLink extends AbstractGeneratedExcelDownloadLink<DecadalRainfall> {
    private static final long serialVersionUID = -1753110031235443491L;

    @SpringBean
    private DecadalRainfallService decadalRainfallService;

    public DownloadDecadalRainfallLink(String id, IModel<DecadalRainfall> model, Boolean template) {
        super(id, model, template);
    }

    @Override
    protected String getFileName() {
        DecadalRainfall entity = getModelObject();
        return String.format("Cumul de plui - %s.%s.%s.xlsx",
                entity.getYear(),
                MonthDTO.of(entity.getMonth()).toString(),
                entity.getDecadal().getValue());
    }

    @Override
    protected DecadalRainfall getTemplateObject() {
        DecadalRainfall dr = getModelObject();
        return decadalRainfallService.getExample(dr.getYear(), dr.getMonth(), dr.getDecadal());
    }

    @Override
    protected void generate(DecadalRainfall decadalRainfall, OutputStream outputStream) throws IOException {
        decadalRainfallService.export(decadalRainfall, outputStream);
    }
}
