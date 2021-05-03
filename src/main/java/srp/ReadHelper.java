package srp;

import java.io.File;

public interface ReadHelper {
    String contents(File file);
    boolean validation(String content, String pattern);
}
