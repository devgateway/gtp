package org.devgateway.toolkit.forms.wicket.components.status;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.persistence.dao.DBConstants;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.MonthDecadal;
import org.devgateway.toolkit.persistence.status.RainfallMapProgress;

import java.time.Month;

/**
 * @author Octavian Ciubotaru
 */
public class RainfallMapStatusTable extends AbstractStatusTable<RainfallMapProgress> {

    public RainfallMapStatusTable(String id, IModel<RainfallMapProgress> model) {
        super(id, model);

        add(new WebMarkupContainer("status")
                .add(new Label("label", new StringResourceModel("status")))
                .add(new AttributeModifier("colspan", DBConstants.MONTH_DECADALS.size())));

        add(new ListView<Month>("months", DBConstants.MONTHS) {
            @Override
            protected void populateItem(ListItem<Month> item) {
                item.add(new AttributeModifier("colspan", Decadal.values().length));
                item.add(new Label("label", item.getModel()));
            }
        });

        add(new ListView<MonthDecadal>("decadals", DBConstants.MONTH_DECADALS) {
            @Override
            protected void populateItem(ListItem<MonthDecadal> item) {
                item.add(new Label("label",
                        item.getModel().map(MonthDecadal::getDecadal).map(Decadal::getValue)));
            }
        });

        add(new ListView<MonthDecadal>("statuses", DBConstants.MONTH_DECADALS) {

            @Override
            protected void populateItem(ListItem<MonthDecadal> item) {
                item.add(new AttributeModifier("class",
                        item.getModel().map(md -> AbstractStatusTable.statusToCssClass(model.getObject().get(md)))));
            }
        });
    }
}
