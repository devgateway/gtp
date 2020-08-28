package org.devgateway.toolkit.forms.wicket.components.links;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseYearlySituationService;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Nadejda Mandrescu
 */
public class DownloadDiseaseSituationLink extends AbstractGeneratedExcelDownloadLink<DiseaseYearlySituation> {
    private static final long serialVersionUID = 6291445625094429715L;

    @SpringBean
    private DiseaseYearlySituationService diseaseYearlySituationService;

    public DownloadDiseaseSituationLink(String id, IModel<DiseaseYearlySituation> model) {
        this(id, model, null);
    }

    public DownloadDiseaseSituationLink(String id, IModel<DiseaseYearlySituation> model, Boolean template) {
        super(id, model, template);
    }

    @Override
    protected String getFileName() {
        return String.format("Situation zoo sanitaire - %s.xlsx", getModelObject().getYear());
    }

    @Override
    protected DiseaseYearlySituation getTemplateObject() {
        return diseaseYearlySituationService.getExample(getModelObject().getYear());
    }

    @Override
    protected void generate(DiseaseYearlySituation object, OutputStream outputStream) throws IOException {
        diseaseYearlySituationService.export(object, outputStream);
    }
}
