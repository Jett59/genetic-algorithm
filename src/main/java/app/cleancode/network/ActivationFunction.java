package app.cleancode.network;

public interface ActivationFunction {
    double activate(double value);

    public static ActivationFunction DEFAULT = d -> d / (d + Math.copySign(1, d));
    public static ActivationFunction PASSTHROUGH = d -> d;
}
