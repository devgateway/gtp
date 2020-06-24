package org.devgateway.toolkit.persistence.service;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.devgateway.toolkit.persistence.service.indicator.SearchableCollection;

/**
 * @author Octavian Ciubotaru
 */
public final class ServiceUtil {

    private ServiceUtil() {
    }

    public static boolean exists(List<Pair<Long, String>> names, String name, Long exceptId) {
        SearchableCollection<Pair<Long, String>> col = new SearchableCollection<>(names, Pair::getValue);

        Pair<Long, String> pair = col.get(name);

        return pair != null && (exceptId == null || !exceptId.equals(pair.getKey()));
    }
}
