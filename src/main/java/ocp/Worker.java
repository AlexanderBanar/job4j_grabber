package ocp;

public class Worker {
    public void dig() {
        System.out.println("digging...");
    }

    public void carry() {
        System.out.println("carrying...");
    }

    private interface Workable {
        void work();
    }

    private static class GoodWorker implements Workable {
        @Override
        public void work() {
            System.out.println("digging");
        }
    }
}
