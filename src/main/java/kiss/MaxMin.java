package kiss;

import java.util.Comparator;
import java.util.List;

public class MaxMin {
    public <T> T max(List<T> value, Comparator<T> comparator) {
        return sort(value, comparator).get(value.size() - 1);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return sort(value, comparator).get(0);
    }

    private <T> List<T> sort(List<T> value, Comparator<T> comparator) {
        value.sort(comparator);
        return value;
    }
}
