package srp;

import java.io.File;
import java.sql.Connection;
import java.util.List;

public interface DBworker<T, R> {
    List<T> get(Connection cn);
    void save(List<T> list, File file);
    List<R> reformat(List<T> list);
}
