package org.devgateway.toolkit.forms.wicket.page.edit.location;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.devgateway.toolkit.forms.WebConstants;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractExistingEntityEditPage;
import org.devgateway.toolkit.forms.wicket.page.lists.category.ListLocalitiesPage;
import org.devgateway.toolkit.persistence.dao.GenericPersistable;

import java.io.Serializable;

/**
 * @author Nadejda Mandrescu
 */
public abstract class EditAbstractLocation<T extends GenericPersistable & Serializable>
        extends AbstractExistingEntityEditPage<T> {

    public EditAbstractLocation(PageParameters parameters) {
        super(parameters);

        this.listPageClass = ListLocalitiesPage.class;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final TextFieldBootstrapFormComponent<String> name = new TextFieldBootstrapFormComponent<>("name");
        name.required();
        name.getField().add(WebConstants.StringValidators.MAXIMUM_LENGTH_VALIDATOR_STD_DEFAULT_TEXT);
        editForm.add(name);

        this.deleteButton.setVisible(false);
    }
}
