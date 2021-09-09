package org.devgateway.toolkit.persistence.status;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Octavian Ciubotaru
 */
public abstract class DatasetProgress implements Serializable {

    private final String indicator;

    private final String source;

    public DatasetProgress(String indicator, String source) {
        this.indicator = indicator;
        this.source = source;
    }

    public String getIndicator() {
        return indicator;
    }

    public String getSource() {
        return source;
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
