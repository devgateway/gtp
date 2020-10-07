package org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.item;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.modal.ConfirmationModal;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.MenuTree;
import org.devgateway.toolkit.persistence.dao.menu.MenuItem;

/**
 * @author Nadejda Mandrescu
 */
public class MenuItemModal<T extends MenuItem> extends ConfirmationModal<T> {
    private static final long serialVersionUID = 682284298134838964L;

    public MenuItemModal(String id, IModel<T> itemModel) {
        super(id, itemModel);

        description.setVisible(false);
        form.add(new TextFieldBootstrapFormComponent<T>(
                "label",
                new StringResourceModel("label", this),
                new PropertyModel<>(itemModel, "label")
        ).required());
    }

    @Override
    protected void onSubmit(final AjaxRequestTarget target) {
        MenuTree.refreshOn((AbstractEditPage) getPage(), target);
    }

}
