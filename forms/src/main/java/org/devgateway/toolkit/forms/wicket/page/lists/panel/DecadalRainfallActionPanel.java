package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.forms.wicket.components.links.AbstractGeneratedExcelDownloadLink;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadDecadalRainfall;
import org.devgateway.toolkit.persistence.dao.indicator.DecadalRainfall;

/**
 * @author Nadejda Mandrescu
 */
public class DecadalRainfallActionPanel extends AbstractExcelListActionPanel<DecadalRainfall> {
    private static final long serialVersionUID = -6487340544643324488L;

    public DecadalRainfallActionPanel(String id, IModel<DecadalRainfall> model, Class<? extends Page> uploadPageClass,
            Class<? extends Page> editPageClass) {
        super(id, model, uploadPageClass, editPageClass);
    }

    @Override
    protected AbstractGeneratedExcelDownloadLink<?> getDownloadButton(String id) {
        return new DownloadDecadalRainfall(id, getModel(), false);
    }
}
