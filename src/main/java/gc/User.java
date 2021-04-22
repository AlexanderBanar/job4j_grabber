package gc;

public class User {
    private int fieldInt;
    private String fieldStr;
    private static final long KB = 1000;
    private static final long MB = KB * KB;
    private static final Runtime ENVIRONMENT = Runtime.getRuntime();

    public User(int fieldInt, String fieldStr) {
        this.fieldInt = fieldInt;
        this.fieldStr = fieldStr;
    }

    public static void info() {
        final long freeMemory = ENVIRONMENT.freeMemory();
        final long totalMemory = ENVIRONMENT.totalMemory();
        final long maxMemory = ENVIRONMENT.maxMemory();
        System.out.printf("Free: %d%n", freeMemory / MB);
        System.out.printf("Total: %d%n", totalMemory / MB);
        System.out.printf("Max: %d%n", maxMemory / MB);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.printf("Removed %d %s%n", fieldInt, fieldStr);
    }

    public static void main(String[] args) {
        System.out.println("=== Environment state before users born ===");
        info();
        for (int i = 0; i < 1000; i++) {
            new User(i, String.format("String%d ", i));
        }
        System.out.println("=== Environment state after gc of users ===");
        info();
    }
}
