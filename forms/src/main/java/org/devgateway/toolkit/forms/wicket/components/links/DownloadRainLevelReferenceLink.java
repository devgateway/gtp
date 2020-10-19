package org.devgateway.toolkit.forms.wicket.components.links;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;
import org.devgateway.toolkit.persistence.service.reference.rainfall.RainLevelReferenceService;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Nadejda Mandrescu
 */
public class DownloadRainLevelReferenceLink extends AbstractGeneratedExcelDownloadLink<RainLevelReference> {
    private static final long serialVersionUID = 7535572979016892017L;

    @SpringBean
    private RainLevelReferenceService rainLevelReferenceService;

    public DownloadRainLevelReferenceLink(String id, IModel<RainLevelReference> model, Boolean template) {
        super(id, model, template);
    }

    @Override
    protected String getFileName() {
        RainLevelReference rainReference = getModelObject();
        return String.format("Années de référence de pluie %s - %s.xlsx",
                rainReference.getReferenceYearStart(),
                rainReference.getReferenceYearEnd());
    }

    @Override
    protected RainLevelReference getTemplateObject() {
        RainLevelReference rr = getModelObject();
        return rainLevelReferenceService.getExample(rr.getReferenceYearStart(), rr.getReferenceYearEnd());
    }

    @Override
    protected void generate(RainLevelReference object, OutputStream outputStream) throws IOException {
        rainLevelReferenceService.export(object, outputStream);
    }
}
