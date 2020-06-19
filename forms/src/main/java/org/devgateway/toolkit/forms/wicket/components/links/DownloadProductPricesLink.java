package org.devgateway.toolkit.forms.wicket.components.links;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.persistence.dao.indicator.ProductYearlyPrices;
import org.devgateway.toolkit.persistence.service.indicator.ProductYearlyPricesService;

/**
 * @author Octavian Ciubotaru
 */
public class DownloadProductPricesLink extends AbstractGeneratedExcelDownloadLink<ProductYearlyPrices> {

    @SpringBean
    private ProductYearlyPricesService productYearlyPricesService;

    public DownloadProductPricesLink(String id, IModel<ProductYearlyPrices> model) {
        super(id, model);
    }

    @Override
    protected String getFileName() {
        ProductYearlyPrices entity = getModelObject();
        return String.format("%s - %s.xlsx", entity.getProductType().getLabel(),
                entity.getYear());
    }

    @Override
    protected boolean isEmpty() {
        return getModelObject().getPricesSize() == null
                ? getModelObject().getPrices().isEmpty()
                : getModelObject().getPricesSize() == 0L;
    }

    @Override
    protected void generate(OutputStream outputStream) throws IOException {
        productYearlyPricesService.export(getModelObject(), outputStream);
    }
}
