package app.cleancode.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;

public class Layer implements Serializable {
    private static final long serialVersionUID = -2767616583671736945L;

    private final List<Neuron> neurons;

    public Layer(List<Neuron> neurons) {
        this.neurons = Collections.unmodifiableList(new ArrayList<>(neurons));
    }

    public int getNeuronCount() {
        return neurons.size();
    }

    public Neuron getNeuron(int index) {
        return neurons.get(index);
    }

    public void apply(ActivationFunction activationFunction, Layer nextLayer) {
        int neuronCount = neurons.size();
        for (int i = 0; i < neuronCount; i++) {
            Neuron neuron = neurons.get(i);
            neuron.activate(activationFunction);
            int weightCount = neuron.getWeightCount();
            for (int j = 0; j < weightCount; j++) {
                nextLayer.neurons.get(j).addValue(neuron.getWeightedValue(j));
            }
        }
    }

    public void clear() {
        for (Neuron neuron : neurons) {
            neuron.clear();
        }
    }

    public Layer mutate(double rate, SplittableRandom rand) {
        List<Neuron> newNeurons = new ArrayList<>(neurons);
        int neuronIndex = rand.nextInt(neurons.size());
        Neuron neuron = neurons.get(neuronIndex);
        newNeurons.set(neuronIndex, neuron.mutate(rate, rand));
        return new Layer(newNeurons);
    }
}
