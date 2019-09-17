package org.devgateway.toolkit.forms.wicket.components.pivottable;

import java.util.List;

/**
 * <p>Used to configure analysis tool with some initial preselected rows/cols/aggregator and values.</p>
 *
 * <p>Also provides necessary information to generate derived fields.</p>
 *
 * @author Octavian Ciubotaru
 */
public interface DatasetAnalysisConfigurer {

    /**
     * Initial fields for grouping on columns.
     */
    List<String> getCols();

    /**
     * Initial fields for grouping on rows.
     */
    List<String> getRows();

    /**
     * Initial fields used as parameter to aggregation function.
     */
    List<String> getVals();

    /**
     * Initial aggregation function.
     */
    String getAggregatorName();

    /**
     * Any extra fields. Usually those that were derived.
     */
    List<PivotField> getExtraFields();

    /**
     * Any options to pass to javascript part to be able to compute derived fields.
     */
    Object getExtraOpts(String language);
}
