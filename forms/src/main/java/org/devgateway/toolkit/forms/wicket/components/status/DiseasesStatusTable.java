package org.devgateway.toolkit.forms.wicket.components.status;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.persistence.status.DiseasesProgress;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author Octavian Ciubotaru
 */
public class DiseasesStatusTable extends AbstractStatusTable<DiseasesProgress> {

    public DiseasesStatusTable(String id, IModel<DiseasesProgress> model) {
        super(id, model);

        add(new WebMarkupContainer("status")
                .add(new Label("label", new StringResourceModel("status")))
                .add(new AttributeModifier("colspan", Month.values().length)));

        add(new ListView<Month>("months", Arrays.asList(Month.values())) {
            @Override
            protected void populateItem(ListItem<Month> item) {
                item.add(new Label("label",
                        item.getModel().map(m -> m.getDisplayName(TextStyle.SHORT, Locale.FRENCH))));
            }
        });

        add(new ListView<Month>("statuses", Arrays.asList(Month.values())) {
            @Override
            protected void populateItem(ListItem<Month> item) {
                item.add(new AttributeModifier("class",
                        model.map(p -> statusToCssClass(p.getStatus(item.getModelObject())))));
            }
        });
    }
}
