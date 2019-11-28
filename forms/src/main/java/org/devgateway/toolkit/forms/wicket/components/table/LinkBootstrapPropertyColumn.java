package org.devgateway.toolkit.forms.wicket.components.table;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.persistence.dao.FileMetadata;
import org.devgateway.toolkit.persistence.dao.Fileable;

/**
 * Created by Daniel Oliva
 */
public class LinkBootstrapPropertyColumn extends PropertyColumn {


    public static final String FILES_DOWNLOAD_LINK = "/files/download/";
    public static final String NOT_AVAILABLE = "N/A";

    public LinkBootstrapPropertyColumn(final IModel displayModel, final String propertyExpression) {
        super(displayModel, propertyExpression);
    }

    @Override
    public void populateItem(final Item item, final String componentId, final IModel rowModel) {
        Fileable fileable = (Fileable) rowModel.getObject();
        if (fileable.getFiles() != null && fileable.getFiles().iterator().hasNext()) {
            FileMetadata file = fileable.getFiles().iterator().next();
            item.add(new Component[] {
                    new ExternalLink(componentId, FILES_DOWNLOAD_LINK + file.getId(), file.getName())
            });
        } else {
            item.add(new Component[] {
                    new Label(componentId, NOT_AVAILABLE)
            });
        }
        item.add(new AttributeAppender("class", "link-column"));
    }
}
