package org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.item;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.devgateway.toolkit.forms.wicket.components.modal.ConfirmationModal;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.MenuTree;
import org.devgateway.toolkit.persistence.dao.menu.MenuItem;

/**
 * @author Nadejda Mandrescu
 */
public class DeleteMenuItemConfirmationModal extends ConfirmationModal<MenuItem> {
    private static final long serialVersionUID = 7641994953695035578L;

    public DeleteMenuItemConfirmationModal(String markupId, IModel<MenuItem> model) {
        super(markupId, model);

        submitButton.setType(Buttons.Type.Danger);
        submitButton.setIconType(FontAwesomeIconType.trash);
    }

    @Override
    protected void onSubmit(final AjaxRequestTarget target) {
        MenuItem menuItem = getModelObject();
        menuItem.getParent().getItems().remove(menuItem);
        MenuTree.refreshOn((AbstractEditPage) getPage(), target);
    }
}
