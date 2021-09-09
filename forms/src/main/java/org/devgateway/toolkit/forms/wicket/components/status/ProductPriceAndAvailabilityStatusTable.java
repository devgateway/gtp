package org.devgateway.toolkit.forms.wicket.components.status;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.persistence.dao.categories.Category;
import org.devgateway.toolkit.persistence.status.ProductPriceAndAvailabilityProgress;
import org.devgateway.toolkit.persistence.status.ProductTypeYearStatus;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author Octavian Ciubotaru
 */
public class ProductPriceAndAvailabilityStatusTable extends AbstractStatusTable<ProductPriceAndAvailabilityProgress> {

    public ProductPriceAndAvailabilityStatusTable(String id, IModel<ProductPriceAndAvailabilityProgress> model) {
        super(id, model);

        add(new Label("productTypeLabel", new StringResourceModel("productType")));

        add(new WebMarkupContainer("status")
                .add(new Label("label", new StringResourceModel("status")))
                .add(new AttributeModifier("colspan", 12)));

        add(new ListView<Month>("months", Arrays.asList(Month.values())) {
            @Override
            protected void populateItem(ListItem<Month> item) {
                item.add(new Label("label",
                        item.getModel().map(m -> m.getDisplayName(TextStyle.SHORT, Locale.FRENCH))));
            }
        });

        add(new ListView<ProductTypeYearStatus>("rows",
                model.map(ProductPriceAndAvailabilityProgress::getProductTypeYearStatuses)) {

            @Override
            protected void populateItem(ListItem<ProductTypeYearStatus> item) {
                IModel<ProductTypeYearStatus> rp = item.getModel();

                item.add(new Label("productType",
                        rp.map(ProductTypeYearStatus::getProductType).map(Category::getLabel)));

                item.add(new ListView<Month>("statuses", Arrays.asList(Month.values())) {

                    @Override
                    protected void populateItem(ListItem<Month> item) {
                        item.add(new AttributeModifier("class",
                                item.getModel().map(m -> statusToCssClass(rp.getObject().getStatuses().get(m)))));
                    }
                });
            }
        });
    }
}
