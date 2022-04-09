package app.cleancode.network;

public interface ActivationFunction {
    double activate(double value);
    
    public static ActivationFunction DEFAULT = d->Math.abs(d) / (Math.abs(d) + 1);
    public static ActivationFunction PASSTHROUGH = d->d;
}
