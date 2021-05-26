package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.forms.wicket.components.links.AbstractGeneratedExcelDownloadLink;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadYearlyRainfallLink;
import org.devgateway.toolkit.persistence.dao.indicator.YearlyRainfall;

/**
 * @author Nadejda Mandrescu
 */
public class YearlyRainfallActionPanel extends AbstractExcelListActionPanel<YearlyRainfall> {
    private static final long serialVersionUID = -6487340544643324488L;

    public YearlyRainfallActionPanel(String id, IModel<YearlyRainfall> model, Class<? extends Page> uploadPageClass,
            Class<? extends Page> editPageClass) {
        super(id, model, uploadPageClass, editPageClass);
    }

    @Override
    protected AbstractGeneratedExcelDownloadLink<?> getDownloadButton(String id) {
        return new DownloadYearlyRainfallLink(id, getModel(), getModelObject().isEmpty());
    }
}
