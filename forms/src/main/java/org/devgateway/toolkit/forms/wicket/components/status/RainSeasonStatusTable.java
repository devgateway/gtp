package org.devgateway.toolkit.forms.wicket.components.status;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.persistence.dao.categories.PluviometricPost;
import org.devgateway.toolkit.persistence.status.PluviometricPostStatus;
import org.devgateway.toolkit.persistence.status.RainSeasonYearProgress;

/**
 * @author Octavian Ciubotaru
 */
public class RainSeasonStatusTable extends AbstractStatusTable<RainSeasonYearProgress> {

    public RainSeasonStatusTable(String id, IModel<RainSeasonYearProgress> model) {
        super(id, model);

        add(new Label("postLabel", new StringResourceModel("pluviometricPost")));
        add(new Label("statusLabel", new StringResourceModel("status")));

        add(new ListView<PluviometricPostStatus>("rows", model.map(RainSeasonYearProgress::getPostStatuses)) {

            @Override
            protected void populateItem(ListItem<PluviometricPostStatus> item) {
                IModel<PluviometricPostStatus> rsp = item.getModel();
                item.add(new Label("post", rsp.map(PluviometricPostStatus::getPost).map(PluviometricPost::getLabel)));
                item.add(new WebMarkupContainer("status")
                        .add(new AttributeModifier("class", rsp
                                .map(PluviometricPostStatus::getStatus)
                                .map(AbstractStatusTable::statusToCssClass))));
            }
        });
    }
}
