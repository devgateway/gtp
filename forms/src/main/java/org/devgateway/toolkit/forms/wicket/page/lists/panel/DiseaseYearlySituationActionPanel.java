package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.links.AbstractGeneratedExcelDownloadLink;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadDiseaseSituationLink;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.disease.EditDiseaseYearlySituationPage;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseYearlySituationService;

/**
 * @author Nadejda Mandrescu
 */
public class DiseaseYearlySituationActionPanel extends AbstractExcelListActionPanel<DiseaseYearlySituation> {
    private static final long serialVersionUID = 1035048302180439076L;

    @SpringBean
    private DiseaseYearlySituationService diseaseYearlySituationService;

    public DiseaseYearlySituationActionPanel(String id, IModel<DiseaseYearlySituation> model) {
        super(id, model, EditDiseaseYearlySituationPage.class);
    }

    @Override
    protected AbstractGeneratedExcelDownloadLink<?> getDownloadButton(String id) {
        DownloadDiseaseSituationLink link = new DownloadDiseaseSituationLink(id, getModel());
        link.setVisibilityAllowed(diseaseYearlySituationService.existsByYear(getModelObject().getYear()));
        return link;
    }
}
