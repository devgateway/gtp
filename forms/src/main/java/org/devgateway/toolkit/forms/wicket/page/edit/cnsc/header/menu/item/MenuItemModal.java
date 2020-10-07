package org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.item;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.devgateway.toolkit.forms.models.ResettablePropertyModel;
import org.devgateway.toolkit.forms.wicket.components.form.GenericBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.components.form.visitors.GenericBootstrapFormComponentResetVisitor;
import org.devgateway.toolkit.forms.wicket.components.modal.ConfirmationModal;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.MenuTree;
import org.devgateway.toolkit.persistence.dao.menu.MenuItem;

/**
 * @author Nadejda Mandrescu
 */
public class MenuItemModal<T extends MenuItem> extends ConfirmationModal<T> {
    private static final long serialVersionUID = 682284298134838964L;

    protected final boolean isAdd;

    public MenuItemModal(String id, IModel<T> itemModel) {
        super(id, itemModel);

        description.setVisible(false);

        TextFieldBootstrapFormComponent<String> label = new TextFieldBootstrapFormComponent<>(
                "label",
                new StringResourceModel("label", this),
                new ResettablePropertyModel<>(itemModel, "label")
        );
        label.required();
        label.getField().add(new UniqueLabelValidator());
        form.add(label);

        this.isAdd = itemModel.getObject().getLabel() == null;
    }

    @Override
    protected void onSubmit(final AjaxRequestTarget target) {
        MenuTree.refreshOn((AbstractEditPage) getPage(), target);
    }

    @Override
    protected void onCancel(final AjaxRequestTarget target) {
        if (this.isAdd) {
            this.getModel().getObject().getParent().getItems().remove(this.getModel().getObject());
        } else {
            form.visitChildren(GenericBootstrapFormComponent.class,
                    new GenericBootstrapFormComponentResetVisitor());
        }
        MenuTree.refreshOn((AbstractEditPage) getPage(), target);
    }

    private class UniqueLabelValidator implements IValidator<String> {
        private static final long serialVersionUID = 3541263447315148738L;

        @Override
        public void validate(final IValidatable<String> validatable) {
            final String label = validatable.getValue();
            final MenuItem current = MenuItemModal.this.getModel().getObject();

            if (label != null && current.getParent().getItems().stream()
                    .anyMatch(mi -> !mi.equals(current) && mi.getLabel().equals(label))) {
                validatable.error(new ValidationError(this));
            }
        }
    }

}
