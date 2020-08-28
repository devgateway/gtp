package org.devgateway.toolkit.forms.wicket.page.edit.indicator.disease;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.security.SecurityConstants;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadDiseaseSituationLink;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractExcelImportPage;
import org.devgateway.toolkit.forms.wicket.page.lists.indicator.disease.ListDiseaseYearlySituationPage;
import org.devgateway.toolkit.persistence.dao.indicator.DiseaseYearlySituation;
import org.devgateway.toolkit.persistence.service.category.LivestockDiseaseService;
import org.devgateway.toolkit.persistence.service.indicator.ReaderException;
import org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseQuantityReader;
import org.devgateway.toolkit.persistence.service.indicator.disease.DiseaseYearlySituationService;
import org.devgateway.toolkit.persistence.service.location.RegionService;
import org.devgateway.toolkit.persistence.util.JPAUtil;
import org.wicketstuff.annotation.mount.MountPath;

import java.io.InputStream;

/**
 * @author Nadejda Mandrescu
 */
@AuthorizeInstantiation(SecurityConstants.Roles.ROLE_DISEASE_SITUATION_EDITOR)
@MountPath(value = "/disease-situation-upload")
public class EditDiseaseYearlySituationPage extends AbstractExcelImportPage<DiseaseYearlySituation> {
    private static final long serialVersionUID = 6842611917574974151L;

    @SpringBean
    private DiseaseYearlySituationService diseaseYearlySituationService;

    @SpringBean
    private RegionService regionService;

    @SpringBean
    private LivestockDiseaseService livestockDiseaseService;

    public EditDiseaseYearlySituationPage(PageParameters parameters) {
        super(parameters);

        this.jpaService = diseaseYearlySituationService;
        setListPage(ListDiseaseYearlySituationPage.class);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        pageTitle.setDefaultModel(new StringResourceModel("page.title", this, editForm.getModel()));

        deleteButton.setVisible(false);
    }

    @Override
    protected BootstrapAjaxLink<?> getDownloadButton(String id, boolean template) {
        return new DownloadDiseaseSituationLink(id, editForm.getModel(), template);
    }

    @Override
    protected void importData(InputStream inputStream) throws ReaderException {
        DiseaseYearlySituation oldSituation = editForm.getModelObject();
        DiseaseQuantityReader reader = new DiseaseQuantityReader(regionService.findAll(),
                livestockDiseaseService.findAll(), oldSituation.getYear());
        DiseaseYearlySituation newSituation = reader.read(inputStream);
        JPAUtil.mergeSortedSet(newSituation.getQuantities(), oldSituation.getQuantities(),
                oldSituation::addDiseaseQuantity,
                (oldItem, newItem) -> oldItem.setQuantity(newItem.getQuantity()));
    }
}
