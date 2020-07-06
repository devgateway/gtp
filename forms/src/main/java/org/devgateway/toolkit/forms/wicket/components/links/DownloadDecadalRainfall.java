package org.devgateway.toolkit.forms.wicket.components.links;

import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;
import org.devgateway.toolkit.persistence.dto.MonthDTO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Nadejda Mandrescu
 */
public class DownloadDecadalRainfall extends AbstractGeneratedExcelDownloadLink<DecadalRainfall> {
    private static final long serialVersionUID = -1753110031235443491L;

    public DownloadDecadalRainfall(String id, IModel<DecadalRainfall> model, Boolean template) {
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
        // TODO
        return null;
    }

    @Override
    protected void generate(DecadalRainfall object, OutputStream outputStream) throws IOException {
        // TODO
    }
}
