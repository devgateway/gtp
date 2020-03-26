package org.devgateway.toolkit.forms.wicket.components.navigation;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonList;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.DropDownButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.core.util.Components;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

/**
 * @author idobre
 * @since 2/9/15
 *
 * Dropdown sub-menu for bootstrap 3 - please be aware that this is working much better for bigger lists
 */

public abstract class DropDownSubMenu extends DropDownButton {
    private static final long serialVersionUID = 2233352448742071270L;

    public DropDownSubMenu(IModel<String> model) {
        super(ButtonList.getButtonMarkupId(), model);
    }

    public DropDownSubMenu(IModel<String> model, IModel<IconType> iconTypeModel) {
        super(ButtonList.getButtonMarkupId(), model, iconTypeModel);
    }

    protected String createCssClassName() {
        return "dropdown-submenu pull-left";
    }

    protected void onComponentTag(ComponentTag tag) {
        if (!Components.hasTagName(tag, new String[]{"li"})) {
            tag.setName("li");
        }

        super.onComponentTag(tag);
    }

    protected void addButtonBehavior(IModel<Buttons.Type> buttonType, IModel<Buttons.Size> buttonSize) {
    }
}
