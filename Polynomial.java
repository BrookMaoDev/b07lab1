public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        coefficients = new double[] { 0 };
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other) {
        int longerArrayLength = Math.max(coefficients.length, other.coefficients.length);
        double[] newCoefficients = new double[longerArrayLength];

        for (int i = 0; i < coefficients.length; i++) {
            newCoefficients[i] += coefficients[i];
        }

        for (int i = 0; i < other.coefficients.length; i++) {
            newCoefficients[i] += other.coefficients[i];
        }

        Polynomial result = new Polynomial(newCoefficients);
        return result;
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }

        return result;
    }

    public boolean hasRoot(double x) {
        final double EPSILON = .0001;
        return Math.abs(evaluate(x)) < EPSILON;
    }
}
