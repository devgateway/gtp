package org.devgateway.toolkit.forms.wicket.components.status;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.persistence.dao.Decadal;
import org.devgateway.toolkit.persistence.dao.MonthDecadal;
import org.devgateway.toolkit.persistence.dao.indicator.GTPBulletin;
import org.devgateway.toolkit.persistence.dao.location.Department;
import org.devgateway.toolkit.persistence.status.DepartmentMonthDecadalStatus;
import org.devgateway.toolkit.persistence.status.GTPBulletinProgress;

import java.time.Month;

/**
 * @author Octavian Ciubotaru
 */
public class GTPBulletinStatusTable extends AbstractStatusTable<GTPBulletinProgress> {

    public GTPBulletinStatusTable(String id, IModel<GTPBulletinProgress> model) {
        super(id, model);

        add(new Label("locationLabel", new StringResourceModel("location")));

        add(new WebMarkupContainer("status")
                .add(new Label("label", new StringResourceModel("status")))
                .add(new AttributeModifier("colspan", GTPBulletin.MONTH_DECADALS.size())));

        add(new ListView<Month>("months", GTPBulletin.MONTHS) {
            @Override
            protected void populateItem(ListItem<Month> item) {
                item.add(new AttributeModifier("colspan", Decadal.values().length));
                item.add(new Label("label", item.getModel()));
            }
        });

        add(new ListView<MonthDecadal>("decadals", GTPBulletin.MONTH_DECADALS) {
            @Override
            protected void populateItem(ListItem<MonthDecadal> item) {
                item.add(new Label("label", item.getModel().map(d -> d.getDecadal().getValue())));
            }
        });

        add(new ListView<DepartmentMonthDecadalStatus>("rows", model.map(GTPBulletinProgress::getDepartmentStatuses)) {

            @Override
            protected void populateItem(ListItem<DepartmentMonthDecadalStatus> item) {
                IModel<DepartmentMonthDecadalStatus> rp = item.getModel();

                item.add(new Label("location",
                        rp.map(DepartmentMonthDecadalStatus::getDepartment).map(Department::getName)));

                item.add(new ListView<MonthDecadal>("statuses", GTPBulletin.MONTH_DECADALS) {

                    @Override
                    protected void populateItem(ListItem<MonthDecadal> item) {
                        item.add(new AttributeModifier("class",
                                item.getModel().map(md -> statusToCssClass(rp.getObject().getStatuses().get(md)))));
                    }
                });
            }
        });
    }
}
