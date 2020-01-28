package org.devgateway.toolkit.forms.wicket.components.table;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.persistence.dao.Linkable;

/**
 * Created by Daniel Oliva
 */
public class DirectLinkBootstrapPropertyColumn extends PropertyColumn {

    public static final String NOT_AVAILABLE = "N/A";

    public DirectLinkBootstrapPropertyColumn(final IModel displayModel, final String propertyExpression) {
        super(displayModel, propertyExpression);
    }

    @Override
    public void populateItem(final Item item, final String componentId, final IModel rowModel) {
        Linkable linkable = (Linkable) rowModel.getObject();
        String link = linkable.getLink();
        if (StringUtils.isNotBlank(link)) {
            String linkToDisplay = link;
            if (link.length() > 30) {
                linkToDisplay = link.substring(0, 29) + "...";
            } 
            item.add(new Component[] {
                    new ExternalLink(componentId, link, linkToDisplay)
            });
        } else {
            item.add(new Component[] {
                    new Label(componentId, NOT_AVAILABLE)
            });
        }
        item.add(new AttributeAppender("class", "link-column"));
    }
}
