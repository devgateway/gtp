package org.devgateway.toolkit.forms.wicket.page.lists.indicator.disease;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.disease.EditDiseaseYearlySituationPage;
import org.devgateway.toolkit.forms.wicket.page.lists.AbstractExcelImportListPage;
import org.devgateway.toolkit.forms.wicket.page.lists.panel.DiseaseYearlySituationActionPanel;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseYearlySituationService;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_DISEASE_SITUATION_EDITOR)
@MountPath(value = "/disease-situations")
public class ListDiseaseYearlySituationPage extends AbstractExcelImportListPage<DiseaseYearlySituation> {
    private static final long serialVersionUID = -506591198551207742L;

    @SpringBean
    private DiseaseYearlySituationService diseaseYearlySituationService;

    public ListDiseaseYearlySituationPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = diseaseYearlySituationService;
        this.editPageClass = EditDiseaseYearlySituationPage.class;

        diseaseYearlySituationService.generate();

        columns.add(new PropertyColumn<>(new StringResourceModel("year"), "year", "year"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        dataProvider.setSort("year", SortOrder.ASCENDING);
        editPageLink.setVisible(false);
    }

    @Override
    public Panel getActionPanel(String id, IModel<DiseaseYearlySituation> model) {
        return new DiseaseYearlySituationActionPanel(id, model);
    }
}
