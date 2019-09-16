package org.devgateway.toolkit.forms.wicket.components.pivottable;

import java.util.List;

/**
 * Javascript options to pass directly to pivottable.js library.
 *
 * @author Octavian Ciubotaru
 */
public class PivotUIOpts {

    private List<String> rows;
    private List<String> cols;
    private List<String> vals;
    private List<String> hiddenFromAggregators;
    private List<String> hiddenFromDragDrop;
    private String aggregatorName;
    private String rendererName;

    public PivotUIOpts(List<String> rows, List<String> cols, List<String> vals,
            List<String> hiddenFromAggregators, List<String> hiddenFromDragDrop, String aggregatorName,
            String rendererName) {
        this.rows = rows;
        this.cols = cols;
        this.vals = vals;
        this.hiddenFromAggregators = hiddenFromAggregators;
        this.hiddenFromDragDrop = hiddenFromDragDrop;
        this.aggregatorName = aggregatorName;
        this.rendererName = rendererName;
    }

    public List<String> getRows() {
        return rows;
    }

    public List<String> getCols() {
        return cols;
    }

    public List<String> getVals() {
        return vals;
    }

    public List<String> getHiddenFromAggregators() {
        return hiddenFromAggregators;
    }

    public List<String> getHiddenFromDragDrop() {
        return hiddenFromDragDrop;
    }

    public String getAggregatorName() {
        return aggregatorName;
    }

    public String getRendererName() {
        return rendererName;
    }
}
