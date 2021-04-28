package gc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SoftCacher {
    private Map<String, String> cacheMap = new HashMap<>();
    private SoftReference<Map<String, String>> softCacheMap = new SoftReference<>(cacheMap);

    public String getValue(String key) {
        Map<String, String> cacheMap = this.softCacheMap.get();
        String value = "";
        if (cacheMap != null) {
            value = cacheMap.get(key);
        }
        return (value == null) ? readFile(key) : value;
    }

    private String readFile(String key) {
        String value = "";
        try (BufferedReader br = new BufferedReader(
                new FileReader(key))) {
            value = br.lines().collect(Collectors.joining());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.cacheMap.put(key, value);
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        SoftCacher sc = new SoftCacher();
        String valueOfNames = sc.getValue("Names.txt");
        String valueOfAddress = sc.getValue("Address.txt");
        System.out.println(valueOfNames);
        System.out.println(valueOfAddress);
        System.gc();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("-----------------------------");
        System.out.println(valueOfNames);
        System.out.println(valueOfAddress);
    }
}
