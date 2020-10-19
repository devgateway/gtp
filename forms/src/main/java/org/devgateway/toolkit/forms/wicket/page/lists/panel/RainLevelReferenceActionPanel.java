package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.forms.wicket.components.links.AbstractGeneratedExcelDownloadLink;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadRainLevelReferenceLink;
import org.devgateway.toolkit.forms.wicket.page.edit.reference.rainfall.EditRainLevelReferenceImportPage;
import org.devgateway.toolkit.forms.wicket.page.edit.reference.rainfall.EditRainLevelReferencePage;
import org.devgateway.toolkit.persistence.dao.reference.RainLevelReference;

/**
 * @author Nadejda Mandrescu
 */
public class RainLevelReferenceActionPanel extends AbstractExcelListActionPanel<RainLevelReference> {
    private static final long serialVersionUID = -3175222875715341142L;

    public RainLevelReferenceActionPanel(String id, IModel<RainLevelReference> model) {
        super(id, model, EditRainLevelReferenceImportPage.class, EditRainLevelReferencePage.class);
    }

    @Override
    protected AbstractGeneratedExcelDownloadLink<?> getDownloadButton(String id) {
        return new DownloadRainLevelReferenceLink(id, getModel(), getModelObject().isEmpty());
    }
}
