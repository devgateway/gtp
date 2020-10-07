package org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.item.MenuItemModal;
import org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.item.MenuLeafModal;
import org.devgateway.toolkit.persistence.dao.menu.MenuGroup;
import org.devgateway.toolkit.persistence.dao.menu.MenuItem;
import org.devgateway.toolkit.persistence.dao.menu.MenuLeaf;

/**
 * @author Nadejda Mandrescu
 */
public class MenuGroupNode extends MenuItemNode {
    private static final long serialVersionUID = -1567792878463485706L;

    private IModel<MenuGroup> menuGroupModel;

    public MenuGroupNode(String id, IModel<MenuGroup> model, AbstractTree<MenuItem> tree) {
        super(id, (IModel) model, tree);
        this.menuGroupModel = model;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        MenuGroup fieldGroup = menuGroupModel.getObject();
        RepeatingView actions = new RepeatingView("action");

        ActionLink addField = new ActionLink("link", Buttons.Type.Success, "addLeaf") {
            private static final long serialVersionUID = 5667796958657085972L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                MenuLeaf menuLeaf = (MenuLeaf) addNew(new MenuLeaf());
                addNodeForm(target, new MenuLeafModal("modal", Model.of(menuLeaf)));
            }
        };
        actions.add(new WebMarkupContainer(actions.newChildId()).add(addField));

        if (fieldGroup.isRoot()) {
            ActionLink addGroup = new ActionLink("link", Buttons.Type.Warning, "addGroup") {
                private static final long serialVersionUID = -620968096016806284L;

                @Override
                public void onClick(final AjaxRequestTarget target) {
                    MenuGroup menuGroup = (MenuGroup) addNew(new MenuGroup());
                    addNodeForm(target, new MenuItemModal<>("modal", Model.of(menuGroup)));
                }
            };
            actions.add(new WebMarkupContainer(actions.newChildId()).add(addGroup));
        }
        add(actions);
    }

    private MenuItem addNew(final MenuItem menuItem) {
        final MenuGroup parent = menuGroupModel.getObject();
        menuItem.setIndex(parent.getItems().isEmpty() ? 1: parent.getItems().last().getIndex() + 1);
        parent.addItem(menuItem);
        return menuItem;
    }

    private abstract class ActionLink extends BootstrapAjaxLink<Void> {
        private static final long serialVersionUID = -8191436563953868084L;
        ActionLink(final String id, final Buttons.Type type, final String labelKey) {
            super(id, type);
            setIconType(FontAwesomeIconType.plus_circle)
                    .setSize(Buttons.Size.Mini)
                    .setLabel(new StringResourceModel(labelKey));
        }
    }

}

