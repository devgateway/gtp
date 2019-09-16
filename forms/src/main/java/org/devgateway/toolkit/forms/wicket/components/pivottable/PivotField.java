package org.devgateway.toolkit.forms.wicket.components.pivottable;

/**
 * @author Octavian Ciubotaru
 */
public class PivotField {

    private String name;

    private boolean hideInAggregators;

    private boolean hideInDragAndDrop;

    public PivotField(String name, boolean hideInAggregators, boolean hideInDragAndDrop) {
        this.name = name;
        this.hideInAggregators = hideInAggregators;
        this.hideInDragAndDrop = hideInDragAndDrop;
    }

    public String getName() {
        return name;
    }

    public boolean isHideInAggregators() {
        return hideInAggregators;
    }

    public boolean isHideInDragAndDrop() {
        return hideInDragAndDrop;
    }
}
