import java.io.File;

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));

        double[] c1 = { 6, 5 };
        int[] e1 = { 0, 3 };
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = { -2, -9 };
        int[] e2 = { 1, 4 };
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial p3 = new Polynomial(new File("polynomial1.txt"));
        Polynomial p4 = new Polynomial(new File("polynomial2.txt"));

        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));

        Polynomial m = p1.multiply(p2);
        System.out.println("m(0.1) = " + m.evaluate(0.1));

        System.out.println("p3(0.1) = " + p3.evaluate(0.1));
        System.out.println("p4(0.1) = " + p4.evaluate(0.1));

        if (s.hasRoot(1)) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }

        p1.saveToFile("polynomial3.txt");
    }
}
