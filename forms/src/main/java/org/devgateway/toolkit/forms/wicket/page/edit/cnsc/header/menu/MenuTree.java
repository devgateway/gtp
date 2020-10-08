package org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.theme.WindowsTheme;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.SetModel;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.persistence.dao.menu.MenuGroup;
import org.devgateway.toolkit.persistence.dao.menu.MenuItem;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author Nadejda Mandrescu
 */
public class MenuTree extends NestedTree<MenuItem> {
    private static final long serialVersionUID = -7482291376884338059L;
    public static final String MENU_TREE_ID = "menuTree";

    private final Behavior theme = new WindowsTheme();

    public MenuTree(IModel<MenuGroup> rootMenuModel) {
        this(MENU_TREE_ID, rootMenuModel);
    }

    public MenuTree(String id, IModel<MenuGroup> rootMenuModel) {
        super(
                id,
                new MenuItemTreeProvider(rootMenuModel),
                new SetModel<>(new HashSet<>(Collections.singletonList(rootMenuModel.getObject()))));

        add(new Behavior() {
            private static final long serialVersionUID = 5292218718621149936L;

            @Override
            public void onComponentTag(final Component component, final ComponentTag tag) {
                theme.onComponentTag(component, tag);
            }

            @Override
            public void renderHead(final Component component, final IHeaderResponse response) {
                theme.renderHead(component, response);
            }
        });
    }

    @Override
    protected Component newContentComponent(String id, IModel<MenuItem> itemModel) {
        if (itemModel.getObject().isLeaf()) {
            return new MenuItemNode(id, itemModel, this);
        }
        return new MenuGroupNode(id, (IModel) itemModel, this);
    }

    public static void refreshOn(AbstractEditPage page, AjaxRequestTarget target) {
        target.add(page.getEditForm().get(MenuTree.MENU_TREE_ID));
    }
}
