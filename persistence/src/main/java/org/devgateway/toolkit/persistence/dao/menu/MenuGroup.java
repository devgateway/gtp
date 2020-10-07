package org.devgateway.toolkit.persistence.dao.menu;

import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nadejda Mandrescu
 */
@Entity
@Audited
public class MenuGroup extends MenuItem {
    private static final long serialVersionUID = -1474855092658473647L;

    public static final String ROOT = "ROOT";

    public MenuGroup() {
    }

    public MenuGroup(String name, String label) {
        this.name = name;
        this.label = label;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
    private List<MenuItem> items = new ArrayList<>();

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public void addItem(MenuItem item) {
        this.items.add(item);
        item.setParent(this);
    }
}
