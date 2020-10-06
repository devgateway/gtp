package org.devgateway.toolkit.persistence.dao.cnsc.menu;

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
public class CNSCMenuGroup extends CNSCMenuItem {
    private static final long serialVersionUID = -1474855092658473647L;

    public static final String ROOT = "ROOT";

    public CNSCMenuGroup() {
    }

    public CNSCMenuGroup(String name, String label) {
        this.name = name;
        this.label = label;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
    private List<CNSCMenuItem> items = new ArrayList<>();

    public List<CNSCMenuItem> getItems() {
        return items;
    }

    public void setItems(List<CNSCMenuItem> items) {
        this.items = items;
    }

    public void addItem(CNSCMenuItem item) {
        this.items.add(item);
        item.setParent(this);
    }
}
