
package calculator;

public class CalculatorService {

    public double add(double a, double b) { return a + b; }
    public double sub(double a, double b) { return a - b; }
    public double mul(double a, double b) { return a * b; }

    public double div(double a, double b) {
        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
        return a / b;
    }

    public double squareRoot(double a) {
        return Math.sqrt(a);
    }

    public double power(double a, double b) {
        return Math.pow(a, b);
    }

    public double celsiusToFahrenheit(double c) {
        return (c * 9 / 5) + 32;
    }

    public double fahrenheitToCelsius(double f) {
        return (f - 32) * 5 / 9;
    }

    public double inrToUsd(double inr) {
        return inr / 83; // approx rate
    }

    public double usdToInr(double usd) {
        return usd * 83;
    }
}
