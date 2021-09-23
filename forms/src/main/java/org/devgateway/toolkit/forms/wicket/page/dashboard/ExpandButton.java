package org.devgateway.toolkit.forms.wicket.page.dashboard;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.components.TooltipBehavior;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.devgateway.toolkit.persistence.dao.categories.Organization;
import org.devgateway.toolkit.persistence.dao.indicator.IndicatorMetadata;
import org.devgateway.toolkit.persistence.service.indicator.IndicatorMetadataService;
import org.devgateway.toolkit.persistence.status.DatasetProgress;
import org.devgateway.toolkit.persistence.status.ProgressSummary;

import java.util.Locale;

/**
 * @author Octavian Ciubotaru
 */
public class ExpandButton extends BootstrapAjaxButton {

    @SpringBean
    private IndicatorMetadataService indicatorMetadataService;

    public ExpandButton(String componentId,
            IModel<DatasetProgress> model) {
        super(componentId, Buttons.Type.Link);

        LoadableDetachableModel<IndicatorMetadata> metadataModel = LoadableDetachableModel.of(
                () -> indicatorMetadataService.findByType(model.getObject().getIndicatorType()));

        setLabel(metadataModel.map(IndicatorMetadata::getName));

        add(new Label("source", metadataModel
                .map(IndicatorMetadata::getOrganization)
                .map(Organization::getLabel)));

        IModel<ProgressSummary> p = model.map(DatasetProgress::getSummary);

        add(newProgressSection("published", p.map(ProgressSummary::getPublishedPerc)));
        add(newProgressSection("draft", p.map(ProgressSummary::getDraftPerc)));
        add(newProgressSection("notStarted", p.map(ProgressSummary::getNoDataPerc)));
    }

    private Component newProgressSection(String id, IModel<Float> percentage) {
        WebMarkupContainer section = new WebMarkupContainer(id);
        section.add(new TooltipBehavior(percentage.map(p -> String.format("%.0f%%", 100 * p))));
        section.add(new AttributeModifier("style",
                percentage.map(p -> String.format(Locale.US, "width: %.2f%%;", 100.0f * p))));
        return section;
    }
}
