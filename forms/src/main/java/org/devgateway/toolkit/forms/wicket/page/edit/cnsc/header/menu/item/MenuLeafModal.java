package org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.item;

import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.devgateway.toolkit.forms.models.ResettablePropertyModel;
import org.devgateway.toolkit.forms.wicket.components.form.TextFieldBootstrapFormComponent;
import org.devgateway.toolkit.persistence.dao.menu.MenuLeaf;

/**
 * @author Nadejda Mandrescu
 */
public class MenuLeafModal extends MenuItemModal<MenuLeaf> {
    private static final long serialVersionUID = -3904088424814752892L;

    public MenuLeafModal(String id, IModel<MenuLeaf> itemModel) {
        super(id, itemModel);

        TextFieldBootstrapFormComponent<String> url = new TextFieldBootstrapFormComponent<>("url",
                new ResettablePropertyModel<>(itemModel, "url"));
        url.getField().add(StringValidator.maximumLength(MenuLeaf.URL_MAX_LENGTH));
        url.required();
        url.getField().add(new UrlValidator());
        form.add(url);
    }
}
