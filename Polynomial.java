import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[] { 0 };
        exponents = new int[] { 0 };
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) {
        try {
            Scanner sc = new Scanner(file);
            String polyString = sc.nextLine();
            sc.close();

            String[] polySplit = polyString.split("\\+|-");

            if (polySplit[0].equals("")) {
                String[] newPolySplit = new String[polySplit.length - 1];

                for (int i = 0; i < polySplit.length - 1; i++) {
                    newPolySplit[i] = polySplit[i + 1];
                }

                polySplit = newPolySplit;
            }

            boolean[] signs = new boolean[polySplit.length]; // true is positive, false is negative
            int populated = 0;

            if (polyString.charAt(0) != '-' && polyString.charAt(0) != '+') {
                // First term is implicitly positive, but needs to be set
                signs[0] = true;
                populated = 1;
            }

            for (int i = 0; i < polyString.length(); i++) {
                if (polyString.charAt(i) == '+') {
                    signs[populated] = true;
                    populated++;
                } else if (polyString.charAt(i) == '-') {
                    signs[populated] = false;
                    populated++;
                }
            }

            double[] coefficients = new double[polySplit.length];
            int[] exponents = new int[polySplit.length];

            for (int i = 0; i < polySplit.length; i++) {
                String[] termString = polySplit[i].split("x");

                coefficients[i] = Double.parseDouble(termString[0]);

                if (termString.length > 1) {
                    if (termString[1] == "") {
                        exponents[i] = 1;
                    } else {
                        exponents[i] = Integer.parseInt(termString[1]);
                    }
                }

                if (signs[i] == false) {
                    coefficients[i] *= -1;
                }
            }

            this.coefficients = coefficients;
            this.exponents = exponents;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int contains(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }

    private int newCoefficientsLength(Polynomial other) {
        int extension = 0;

        for (int i = 0; i < other.exponents.length; i++) {
            if (contains(exponents, other.exponents[i]) == -1) {
                extension++;
            }
        }

        return exponents.length + extension;
    }

    public Polynomial add(Polynomial other) {
        int newArrayLength = newCoefficientsLength(other);
        double[] newCoefficients = new double[newArrayLength];
        int[] newExponents = new int[newArrayLength];

        for (int i = 0; i < exponents.length; i++) {
            newCoefficients[i] = coefficients[i];
            newExponents[i] = exponents[i];
        }

        int offset = exponents.length;

        for (int i = 0; i < other.coefficients.length; i++) {
            int index = contains(newExponents, other.exponents[i]);

            if (index == -1) {
                newCoefficients[offset] = other.coefficients[i];
                newExponents[offset] = other.exponents[i];
                offset++;
            } else {
                newCoefficients[index] += other.coefficients[i];
            }
        }

        Polynomial result = new Polynomial(newCoefficients, newExponents);
        return result;
    }

    public Polynomial scale(Polynomial poly, double scaler, int exponent) {
        double[] newCoefficients = new double[poly.exponents.length];
        int[] newExponents = new int[poly.exponents.length];

        for (int i = 0; i < poly.exponents.length; i++) {
            newCoefficients[i] = poly.coefficients[i] * scaler;
            newExponents[i] = poly.exponents[i] + exponent;
        }

        return new Polynomial(newCoefficients, newExponents);
    }

    public Polynomial multiply(Polynomial other) {
        Polynomial result;

        if (exponents.length >= 1) {
            result = scale(other, coefficients[0], exponents[0]);
        } else {
            return new Polynomial();
        }

        for (int i = 1; i < exponents.length; i++) {
            Polynomial subresult = scale(other, coefficients[i], exponents[i]);
            result = result.add(subresult);
        }

        return result;
    }

    public double evaluate(double x) {
        double result = 0;

        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }

        return result;
    }

    public boolean hasRoot(double x) {
        final double EPSILON = .0001;
        return Math.abs(evaluate(x)) < EPSILON;
    }

    public void saveToFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName, false);
            String output = "";

            for (int i = 0; i < coefficients.length; i++) {
                if (coefficients[i] > 0 && i > 0) {
                    output += "+";
                }

                output += String.valueOf(coefficients[i]);

                if (exponents[i] != 0) {
                    output += "x";
                    output += String.valueOf(exponents[i]);
                }
            }

            writer.write(output);
            writer.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
