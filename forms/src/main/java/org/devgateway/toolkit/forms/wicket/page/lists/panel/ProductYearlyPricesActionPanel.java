package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.forms.wicket.components.links.AbstractGeneratedExcelDownloadLink;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadProductPricesLink;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;

/**
 * @author Octavian Ciubotaru
 */
public class ProductYearlyPricesActionPanel extends AbstractExcelListActionPanel<ProductYearlyPrices> {

    public ProductYearlyPricesActionPanel(String id, IModel<ProductYearlyPrices> model,
            Class<? extends Page> editPageClass) {
        super(id, model, editPageClass);
    }

    @Override
    protected AbstractGeneratedExcelDownloadLink<?> getDownloadButton(String id) {
        return new DownloadProductPricesLink(id, getModel());
    }

    @Override
    protected boolean isEmpty() {
        return getModelObject().getPricesSize() == 0L || getModelObject().getQuantitiesSize() == 0L;
    }
}
