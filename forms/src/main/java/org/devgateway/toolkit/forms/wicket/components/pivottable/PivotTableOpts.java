package org.devgateway.toolkit.forms.wicket.components.pivottable;

import java.util.List;
import java.util.Map;

/**
 * Used to pass options from {@link PivotTable} Wicket component to javascript.
 */
public class PivotTableOpts {

    private String elementId;
    private String dataset;
    private String dataUrl;
    private String language;

    /**
     * Field labels present in dataset.
     */
    private Map<String, String> fields;

    /**
     * Field labels for derived fields.
     */
    private Map<String, String> extraFields;

    private PivotUIOpts pivotUIOpts;

    private List<String> aggregatorNames;
    private Map<String, String> renderers;

    private List<String> mthNames;
    private List<String> dayNames;

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public Map<String, String> getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(Map<String, String> extraFields) {
        this.extraFields = extraFields;
    }

    public PivotUIOpts getPivotUIOpts() {
        return pivotUIOpts;
    }

    public void setPivotUIOpts(PivotUIOpts pivotUIOpts) {
        this.pivotUIOpts = pivotUIOpts;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getAggregatorNames() {
        return aggregatorNames;
    }

    public void setAggregatorNames(List<String> aggregatorNames) {
        this.aggregatorNames = aggregatorNames;
    }

    public Map<String, String> getRenderers() {
        return renderers;
    }

    public void setRenderers(Map<String, String> renderers) {
        this.renderers = renderers;
    }

    public List<String> getMthNames() {
        return mthNames;
    }

    public void setMthNames(List<String> mthNames) {
        this.mthNames = mthNames;
    }

    public List<String> getDayNames() {
        return dayNames;
    }

    public void setDayNames(List<String> dayNames) {
        this.dayNames = dayNames;
    }
}
