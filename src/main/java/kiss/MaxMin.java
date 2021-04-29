package kiss;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class MaxMin {
    public <T> T max(List<T> value, Comparator<T> comparator) {
        Predicate<Integer> predicateForMax = T1 -> T1 > 0;
        return extremumSearch(value, comparator, predicateForMax);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        Predicate<Integer> predicateForMin = T1 -> T1 < 0;
        return extremumSearch(value, comparator, predicateForMin);
    }

    private <T> T extremumSearch(List<T> value, Comparator<T> comparator, Predicate<Integer> predicate) {
        T extremum = value.get(0);
        for (int i = 1; i < value.size(); i++) {
            if (predicate.test(comparator.compare(value.get(i), extremum))) {
                extremum = value.get(i);
            }
        }
        return extremum;
    }
}