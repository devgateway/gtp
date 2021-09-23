package org.devgateway.toolkit.forms.wicket.components.status;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.status.GTPAnnualReportProgress;
import org.devgateway.toolkit.persistence.status.DepartmentStatus;

/**
 * @author Octavian Ciubotaru
 */
public class AnnualGTPBulletinStatusTable extends AbstractStatusTable<GTPAnnualReportProgress> {

    public AnnualGTPBulletinStatusTable(String id, IModel<GTPAnnualReportProgress> model) {
        super(id, model);

        add(new Label("locationLabel", new StringResourceModel("location")));
        add(new Label("statusLabel", new StringResourceModel("status")));

        add(new ListView<DepartmentStatus>("rows", model.map(GTPAnnualReportProgress::getDepartmentStatuses)) {

            @Override
            protected void populateItem(ListItem<DepartmentStatus> item) {
                IModel<DepartmentStatus> rp = item.getModel();

                item.add(new Label("location",
                        rp.map(DepartmentStatus::getDepartment).map(Department::getName)));

                item.add(new WebMarkupContainer("status")
                        .add(new AttributeModifier("class", rp.map(p -> statusToCssClass(p.getStatus())))));
            }
        });
    }
}
