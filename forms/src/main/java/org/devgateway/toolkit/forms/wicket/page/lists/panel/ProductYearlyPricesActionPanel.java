package org.devgateway.toolkit.forms.wicket.page.lists.panel;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.forms.wicket.components.links.AbstractGeneratedExcelDownloadLink;
import org.devgateway.toolkit.forms.wicket.components.links.DownloadProductPricesLink;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.service.category.ProductService;

/**
 * @author Octavian Ciubotaru
 */
public class ProductYearlyPricesActionPanel extends AbstractExcelListActionPanel<ProductYearlyPrices> {
    private static final long serialVersionUID = -3673571930691850613L;

    @SpringBean
    private ProductService productService;

    public ProductYearlyPricesActionPanel(String id, IModel<ProductYearlyPrices> model,
            Class<? extends Page> uploadPageClass) {
        super(id, model, uploadPageClass);
    }

    @Override
    protected AbstractGeneratedExcelDownloadLink<?> getDownloadButton(String id) {
        DownloadProductPricesLink link = new DownloadProductPricesLink(id, getModel());
        link.setVisibilityAllowed(productService.existsByProductType(getModelObject().getProductType()));
        return link;
    }
}
