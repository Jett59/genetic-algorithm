package app.cleancode.network;

public interface ActivationFunction {
    double activate(double value);
    
    public static ActivationFunction DEFAULT = d->d / (Math.abs(d) + 1);
}
