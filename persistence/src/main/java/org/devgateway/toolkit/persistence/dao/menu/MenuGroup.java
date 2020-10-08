package org.devgateway.toolkit.persistence.dao.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Nadejda Mandrescu
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @NotEmpty
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent", fetch = FetchType.EAGER)
    @OrderBy("index")
    @BatchSize(size = 100)
    private SortedSet<MenuItem> items = new TreeSet<>();

    public SortedSet<MenuItem> getItems() {
        return items;
    }

    public void setItems(SortedSet<MenuItem> items) {
        this.items = items;
    }

    public void addItem(MenuItem item) {
        this.items.add(item);
        item.setParent(this);
    }

    public void updateItems(Collection<MenuItem> menuItems) {
        this.items.removeAll(menuItems);
        this.items.addAll(menuItems);
    }

    @JsonIgnore
    @Override
    public boolean isLeaf() {
        return false;
    }
}
