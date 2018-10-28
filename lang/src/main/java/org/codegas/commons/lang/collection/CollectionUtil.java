package org.codegas.commons.lang.collection;

import java.util.*;

public final class CollectionUtil {

    private CollectionUtil() {

    }

    public static <T> List<T> reverse(Collection<? extends T> collection) {
        List<T> reverse = new ArrayList<>(collection);
        Collections.reverse(reverse);
        return reverse;
    }

    public static <T> List<T> concat(Collection<? extends T> collection1, Collection<? extends T> collection2) {
        List<T> concat = new ArrayList<>(collection1.size() + collection2.size());
        concat.addAll(collection1);
        concat.addAll(collection2);
        return concat;
    }

    public static <T extends Comparable<T>> List<T> sort(Collection<? extends T> collection) {
        return sort(collection, Comparable::compareTo);
    }

    public static <T> List<T> sort(Collection<? extends T> collection, Comparator<? super T> comparator) {
        List<T> sorted = new ArrayList<>(collection);
        sorted.sort(comparator);
        return sorted;
    }

    public static <T> List<List<T>> permute(Collection<? extends T> list) {
        List<List<T>> permutations = new ArrayList<>();
        permute(new ArrayList<>(list), 0, permutations);
        return permutations.isEmpty() ? Collections.singletonList(new ArrayList<>()) : permutations;
    }

    private static <T> void permute(List<T> list, int permutationIndex, Collection<List<T>> permutations) {
        for (int index = permutationIndex; index < list.size(); index++) {
            Collections.swap(list, index, permutationIndex);
            permute(list, permutationIndex + 1, permutations);
            Collections.swap(list, permutationIndex, index);
        }
        if (permutationIndex == list.size() - 1) {
            permutations.add(new ArrayList<>(list));
        }
    }
}
