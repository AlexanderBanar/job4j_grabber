package ocp;

public class ItUniversity extends University {
    @Override
    public void educate() {
        System.out.println("IT education");
    }

    private interface Universitable {
        void educate();
    }

    private static class GoodItUniversity implements Universitable {
        @Override
        public void educate() {
            System.out.println("Good IT education");
        }
    }
}
