package org.devgateway.toolkit.forms.wicket.providers;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.stream.IntStream;

import org.devgateway.toolkit.persistence.dao.HydrologicalYear;
import org.wicketstuff.select2.ChoiceProvider;
import org.wicketstuff.select2.Response;

/**
 * @author Octavian Ciubotaru
 */
public class HydrologicalYearRangeChoiceProvider extends ChoiceProvider<HydrologicalYear> {

    private final int min;
    private final int max;

    public HydrologicalYearRangeChoiceProvider(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String getDisplayValue(HydrologicalYear object) {
        return object.toString();
    }

    @Override
    public String getIdValue(HydrologicalYear object) {
        return object.getYear().toString();
    }

    @Override
    public void query(String term, int page, Response<HydrologicalYear> response) {
        IntStream.rangeClosed(min, max)
                .mapToObj(HydrologicalYear::new)
                .filter(y -> term == null || y.toString().contains(term))
                .forEach(response::add);
        response.setHasMore(false);
    }

    @Override
    public Collection<HydrologicalYear> toChoices(Collection<String> ids) {
        return ids.stream().map(Integer::parseInt)
                .map(HydrologicalYear::new)
                .collect(toList());
    }
}
