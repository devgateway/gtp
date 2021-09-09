package org.devgateway.toolkit.forms.wicket.components.status;

import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.persistence.status.DataEntryStatus;

/**
 * @author Octavian Ciubotaru
 */
public abstract class AbstractStatusTable<T> extends GenericPanel<T> {

    public AbstractStatusTable(String id, IModel<T> model) {
        super(id, model);
    }

    public static String statusToCssClass(DataEntryStatus status) {
        switch (status) {
            case NO_DATA: return "red";
            case DRAFT: return "yellow";
            case PUBLISHED: return "green";
            default: return "gray";
        }
    }
}
