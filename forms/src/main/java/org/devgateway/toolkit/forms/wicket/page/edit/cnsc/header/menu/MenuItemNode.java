package org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.devgateway.toolkit.forms.wicket.components.modal.ConfirmationModal;
import org.devgateway.toolkit.forms.wicket.page.edit.AbstractEditPage;
import org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.item.DeleteMenuItemConfirmationModal;
import org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.item.MenuItemModal;
import org.devgateway.toolkit.forms.wicket.page.edit.cnsc.header.menu.item.MenuLeafModal;
import org.devgateway.toolkit.persistence.dao.menu.MenuGroup;
import org.devgateway.toolkit.persistence.dao.menu.MenuItem;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Nadejda Mandrescu
 */
public class MenuItemNode extends Panel {
    private static final long serialVersionUID = -3699979021717663826L;

    protected final IModel<MenuItem> model;

    public MenuItemNode(String id, IModel<MenuItem> model, AbstractTree<MenuItem> tree) {
        super(id);
        this.model = model;

        Folder<MenuItem> folder = new Folder<MenuItem>("label", tree, model) {
            private static final long serialVersionUID = -7448886580898367043L;

            @Override
            protected IModel<String> newLabelModel(final IModel<MenuItem> model) {
                return new PropertyModel<>(model, "label");
            }

            @Override
            protected String getStyleClass() {
                if (MenuItemNode.this.model.getObject().isLeaf()) {
                    return MenuItemNode.this.getStyleClass();
                }
                return super.getStyleClass();
            }
        };
        folder.setOutputMarkupId(true);
        add(folder);

        add(new WebMarkupContainer("modal").setOutputMarkupId(true));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final BootstrapAjaxLink<Void> editNodeLink = new BootstrapAjaxLink<Void>("edit", Buttons.Type.Info) {
            private static final long serialVersionUID = 362137297549399036L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                final IModel nodeModel = model;
                final MenuItemModal nodeForm = model.getObject().isLeaf() ?
                        new MenuLeafModal("modal", nodeModel) : new MenuItemModal<MenuGroup>("modal", nodeModel);
                addNodeForm(target, nodeForm);
            }
        };
        editNodeLink.setIconType(FontAwesomeIconType.edit).setSize(Buttons.Size.Mini);
        if (model.getObject().isRoot()) {
            editNodeLink.setVisible(false);
        }
        add(editNodeLink);

        final BootstrapAjaxLink<Void> deleteNodeLink = new BootstrapAjaxLink<Void>("delete", Buttons.Type.Danger) {
            private static final long serialVersionUID = 362137297549399036L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                addNodeForm(target, new DeleteMenuItemConfirmationModal("modal", model));
            }
        };
        deleteNodeLink.setIconType(FontAwesomeIconType.trash).setSize(Buttons.Size.Mini);
        if (model.getObject().isRoot()) {
            deleteNodeLink.setVisible(false);
        }
        add(deleteNodeLink);

        addUpDownLinks();
    }

    protected void addUpDownLinks() {
        final MenuItem current = this.model.getObject();
        final Set<MenuItem> siblings = current.isRoot() ? null : current.getParent().getItems();
        final Iterator<MenuItem> orderedSiblingsIterator = siblings == null ? null : siblings.iterator();
        MenuItem prev = null;
        MenuItem next = null;

        while (orderedSiblingsIterator != null && orderedSiblingsIterator.hasNext() && next == null) {
            MenuItem sibling = orderedSiblingsIterator.next();
            int compareResult = sibling.compareTo(current);
            if (compareResult < 0) {
                prev = sibling;
            } else if (compareResult > 0) {
                next = sibling;
            }
        }

        addLink("moveUp", FontAwesomeIconType.arrow_up, prev);
        addLink("moveDown", FontAwesomeIconType.arrow_down, next);
    }

    protected void addLink(final String id, final FontAwesomeIconType iconType, final MenuItem swapField) {
        final MenuItem current = this.model.getObject();

        BootstrapAjaxLink<String> actionLink = new BootstrapAjaxLink<String>(id, Buttons.Type.Default) {
            private static final long serialVersionUID = -4116867667898390165L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                swap(current, swapField);
                MenuTree.refreshOn((AbstractEditPage) getPage(), target);
            }
        };
        actionLink.setIconType(iconType).setSize(Buttons.Size.Mini);
        actionLink.setVisible(swapField != null);
        add(actionLink);
    }

    protected void swap(final MenuItem mi1, final MenuItem mi2) {
        Integer tmpIdx = mi1.getIndex();
        mi1.setIndex(mi2.getIndex());
        mi2.setIndex(tmpIdx);

        mi1.getParent().updateItems(Arrays.asList(mi1, mi2));
    }

    protected String getStyleClass() {
        return "field-label glyphicon glyphicon-link";
    }

    protected void addNodeForm(final AjaxRequestTarget target, final ConfirmationModal nodeForm) {
        nodeForm.show(true);
        addOrReplace(nodeForm);
        target.add(nodeForm);
    }
}
