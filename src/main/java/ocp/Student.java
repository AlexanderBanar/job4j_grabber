package ocp;

public class Student {
    University university;

    private interface Universitable {
    }

    private static class GoodUniversity implements Universitable {
    }

    private static class GoodStudent {
        GoodUniversity goodUniversity;
    }
}
