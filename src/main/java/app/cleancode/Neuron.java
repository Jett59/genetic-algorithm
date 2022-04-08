package app.cleancode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Neuron implements Serializable {
    private static final long serialVersionUID = -1973959092332235548L;

    private final ArrayList<Double> weights;
    private transient double value;

    public Neuron(List<Double> weights) {
        this.weights = new ArrayList<>(weights);
    }

    public void addValue(double addition) {
        value += addition;
    }

    public int getWeightCount() {
        return weights.size();
    }

    public double getWeight(int index) {
        return weights.get(index);
    }

    public void activate(ActivationFunction activationFunction) {
        value = activationFunction.activate(value);
    }

    public double getWeightedValue(int weightIndex) {
        return value * weights.get(weightIndex);
    }

    public double getValue() {
        return value;
    }

    public void clear() {
        value = 0;
    }
}
