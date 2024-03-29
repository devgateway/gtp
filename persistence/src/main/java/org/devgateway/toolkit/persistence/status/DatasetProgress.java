package org.devgateway.toolkit.persistence.status;

import org.devgateway.toolkit.persistence.dao.IndicatorType;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public abstract class DatasetProgress implements Serializable {

    private final IndicatorType indicatorType;

    public DatasetProgress(IndicatorType indicatorType) {
        this.indicatorType = indicatorType;
    }

    public IndicatorType getIndicatorType() {
        return indicatorType;
    }

    protected abstract Stream<DataEntryStatus> statusStream();

    public final ProgressSummary getSummary() {
        Map<DataEntryStatus, Long> collect = statusStream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return new ProgressSummary(
                collect.getOrDefault(DataEntryStatus.NO_DATA, 0L).intValue(),
                collect.getOrDefault(DataEntryStatus.DRAFT, 0L).intValue(),
                collect.getOrDefault(DataEntryStatus.PUBLISHED, 0L).intValue());
    }
}
