package org.devgateway.toolkit.persistence.util;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

/**
 * @author Octavian Ciubotaru
 */
public final class JPAUtil {

    private JPAUtil() {
    }

    /**
     * Update destination sorted set with items from source collection.
     * Existing items are updated with <tt>dstItemUpdater</tt>. Items that are not present in target set are added
     * with <tt>dstAdder</tt>. Items from destination set that are not present in source collection are removed.
     *
     * @param srcCol source collection
     * @param dstSet destination set
     * @param dstAdder method used to add new items to destination set
     * @param dstItemUpdater method used to update an existing item from destination set
     * @param <T> item type
     */
    public static <T> void mergeSortedSet(Collection<T> srcCol, SortedSet<T> dstSet,
            Consumer<T> dstAdder, BiConsumer<T, T> dstItemUpdater) {

        SortedMap<T, T> newItemsMap = srcCol.stream()
                .collect(toMap(identity(), identity(), throwingMerger(), TreeMap::new));

        Iterator<T> iterator = dstSet.iterator();
        while (iterator.hasNext()) {
            T oldItem = iterator.next();
            T newItem = newItemsMap.remove(oldItem);
            if (newItem != null) {
                dstItemUpdater.accept(oldItem, newItem);
            } else {
                iterator.remove();
            }
        }

        newItemsMap.keySet().forEach(dstAdder);
    }

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }
}
