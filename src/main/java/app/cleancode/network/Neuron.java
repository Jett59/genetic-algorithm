package app.cleancode.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;

public class Neuron implements Serializable {
    private static final long serialVersionUID = -1973959092332235548L;

    private final List<Double> weights;
    private transient double value;

    public Neuron(List<Double> weights) {
        this.weights = Collections.unmodifiableList(new ArrayList<>(weights));
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
    
    public Neuron mutate(double rate, SplittableRandom rand) {
        if (weights.size() > 0) {
        List<Double> newWeights = new ArrayList<>(weights);
        int weight = rand.nextInt(weights.size());
        double change = (Math.random() - 0.5) * rate;
        newWeights.set(weight, weights.get(weight) + change);
        return new Neuron(newWeights);
        }else {
            return this;
        }
    }
}
