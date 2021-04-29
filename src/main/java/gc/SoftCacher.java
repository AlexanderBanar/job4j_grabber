package gc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SoftCacher {
    private Map<String, SoftReference<String>> softCacheMap = new HashMap<>();

    public String getValue(String key) {
        SoftReference<String> softValue = null;
        if (this.softCacheMap != null) {
            softValue = this.softCacheMap.get(key);
        }
        return (softValue == null) ? readFile(key) : softValueGetter(softValue, key);
    }

    private String readFile(String key) {
        String value = "";
        try (BufferedReader br = new BufferedReader(
                new FileReader(key))) {
            value = br.lines().collect(Collectors.joining());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.softCacheMap.put(key, new SoftReference<>(value));
        return value;
    }

    private String softValueGetter(SoftReference<String> softValue, String key) {
        String value = softValue.get();
        if (value == null) {
            value = readFile(key);
        }
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
