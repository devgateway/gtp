package org.devgateway.toolkit.forms.wicket.components;

import de.agilecoders.wicket.core.markup.html.bootstrap.components.TooltipConfig;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Nadejda Mandrescu
 */
public class LabelWithTooltip extends Panel {
    private static final long serialVersionUID = -5934337348243904306L;

    private TooltipConfig.OpenTrigger configWithTrigger = TooltipConfig.OpenTrigger.hover;

    public LabelWithTooltip(final String id) {
        this(id, id);
    }

    public LabelWithTooltip(final String id, final String fieldId) {
        super(id);

        add(new Label("label", new StringResourceModel(fieldId + ".label")));

        TooltipLabel tooltipLabel = new TooltipLabel("tooltip", fieldId);
        tooltipLabel.setConfigWithTrigger(configWithTrigger);
        add(tooltipLabel);
    }

}

