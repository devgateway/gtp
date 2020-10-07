package org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu;

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.devgateway.toolkit.persistence.dao.menu.MenuGroup;
import org.devgateway.toolkit.persistence.dao.menu.MenuItem;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author Nadejda Mandrescu
 */
public class MenuItemTreeProvider implements ITreeProvider<MenuItem> {
    private static final long serialVersionUID = -3231123034102190747L;

    private IModel<MenuGroup> menuRoot;

    public MenuItemTreeProvider(final IModel<MenuGroup> menuRoot) {
        this.menuRoot = menuRoot;
    }

    @Override
    public Iterator<? extends MenuItem> getRoots() {
        return Arrays.asList(menuRoot.getObject()).listIterator();
    }

    @Override
    public boolean hasChildren(MenuItem menuItem) {
        return !menuItem.isLeaf();
    }

    @Override
    public Iterator<? extends MenuItem> getChildren(MenuItem menuItem) {
        return menuItem.isLeaf() ?
                Collections.emptyIterator() : ((MenuGroup) menuItem).getItems().iterator();
    }

    @Override
    public IModel<MenuItem> model(MenuItem menuItem) {
        return Model.of(menuItem);
    }

    @Override
    public void detach() {
    }
}
