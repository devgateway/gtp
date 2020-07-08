package org.devgateway.toolkit.persistence.service.indicator;

import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.strip;
import static org.apache.commons.lang3.StringUtils.stripAccents;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author Octavian Ciubotaru
 */
public class SearchableCollection<T> {

    private static final Pattern SEP = Pattern.compile("\\s?[–-]\\s?");

    private final Map<String, T> elements;

    public SearchableCollection(Collection<T> col, Function<T, String> nameFn) {
        elements = col.stream().collect(toMap(e -> normalize(nameFn.apply(e)), Function.identity(), (l, r) -> l));
    }

    public static String normalize(String value) {
        String normalized = stripAccents(normalizeSpace(strip(value.toLowerCase())));
        return SEP.matcher(normalized).replaceAll("-");
    }

    public T get(String name) {
        return elements.get(normalize(name));
    }

    public Collection<T> originalValues() {
        return elements.values();
    }
}
