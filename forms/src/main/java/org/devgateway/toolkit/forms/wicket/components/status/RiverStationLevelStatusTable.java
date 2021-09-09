package org.devgateway.toolkit.forms.wicket.components.status;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.devgateway.toolkit.persistence.dao.categories.RiverStation;
import org.devgateway.toolkit.persistence.status.RiverStationStatus;
import org.devgateway.toolkit.persistence.status.RiverStationsYearProgress;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * @author Octavian Ciubotaru
 */
public class RiverStationLevelStatusTable extends AbstractStatusTable<RiverStationsYearProgress> {

    public RiverStationLevelStatusTable(String id, IModel<RiverStationsYearProgress> model,
            IModel<Integer> yearModel) {
        super(id, model);

        add(new Label("riverStationLabel", new StringResourceModel("riverStation")));

        add(new WebMarkupContainer("status")
                .add(new Label("label", new StringResourceModel("status")))
                .add(new AttributeModifier("colspan", HydrologicalYear.HYDROLOGICAL_MONTHS.size())));

        add(new WebMarkupContainer("year1")
                .add(new Label("label", yearModel))
                .add(new AttributeModifier("colspan", 12 - HydrologicalYear.HYDROLOGICAL_FIRST_MONTH.getValue())));

        add(new WebMarkupContainer("year2")
                .add(new Label("label", yearModel.map(y -> y + 1)))
                .add(new AttributeModifier("colspan", HydrologicalYear.HYDROLOGICAL_FIRST_MONTH.getValue())));

        add(new ListView<Month>("months", HydrologicalYear.HYDROLOGICAL_MONTHS) {
            @Override
            protected void populateItem(ListItem<Month> item) {
                item.add(new Label("label",
                        item.getModel().map(m -> m.getDisplayName(TextStyle.SHORT, Locale.FRENCH))));
            }
        });

        add(new ListView<RiverStationStatus>("rows", model.map(RiverStationsYearProgress::getRiverStationStatuses)) {

            @Override
            protected void populateItem(ListItem<RiverStationStatus> item) {
                IModel<RiverStationStatus> rp = item.getModel();

                item.add(new Label("riverStation",
                        rp.map(RiverStationStatus::getStation).map(RiverStation::getName)));

                item.add(new ListView<Month>("statuses", HydrologicalYear.HYDROLOGICAL_MONTHS) {

                    @Override
                    protected void populateItem(ListItem<Month> item) {
                        item.add(new AttributeModifier("class",
                                item.getModel().map(m -> statusToCssClass(rp.getObject().get(m)))));
                    }
                });
            }
        });
    }
}
