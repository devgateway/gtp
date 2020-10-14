package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadDecadalRainfallMapLink;
import org.devgateway.toolkit.forms.wicket.page.edit.indicator.rainfallMap.EditDecadalRainfallMapPage;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfallMap;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallMapActionPanel extends AbstractImportExportActionPanel<DecadalRainfallMap> {
    private static final long serialVersionUID = 7619428057400205489L;

    public DecadalRainfallMapActionPanel(String id, IModel<DecadalRainfallMap> model) {
        super(id, model, EditDecadalRainfallMapPage.class);

        this.downloadEmpty = false;
    }

    @Override
    protected BootstrapAjaxLink<?> getBootstrapDownloadButton(String id) {
        return new DownloadDecadalRainfallMapLink(id, getModel());
    }

}
