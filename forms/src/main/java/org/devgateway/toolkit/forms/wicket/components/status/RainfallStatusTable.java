package org.devgateway.toolkit.forms.wicket.components.status;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.persistence.dao.DBConstants;
import org.devgateway.toolkit.persistence.status.RainfallYearProgress;

import java.time.Month;

/**
 * @author Octavian Ciubotaru
 */
public class RainfallStatusTable extends AbstractStatusTable<RainfallYearProgress> {

    public RainfallStatusTable(String id, IModel<RainfallYearProgress> model) {
        super(id, model);

        add(new WebMarkupContainer("status")
                .add(new Label("label", new StringResourceModel("status")))
                .add(new AttributeModifier("colspan", DBConstants.MONTHS.size())));

        add(new ListView<Month>("months", DBConstants.MONTHS) {
            @Override
            protected void populateItem(ListItem<Month> item) {
                item.add(new Label("label", item.getModel()));
            }
        });

        add(new ListView<Month>("cols", DBConstants.MONTHS) {

            @Override
            protected void populateItem(ListItem<Month> item) {
                item.add(new AttributeModifier("class",
                        item.getModel().map(m -> AbstractStatusTable.statusToCssClass(model.getObject().get(m)))));
            }
        });
    }
}
