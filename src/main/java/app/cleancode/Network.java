package app.cleancode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Network implements Serializable {
    private static final long serialVersionUID = 3033103393920025182L;

    private final ArrayList<Layer> layers;

    public Network(List<Layer> layers) {
        this.layers = new ArrayList<>(layers);
    }

    public List<Double> apply(List<Double> inputs, ActivationFunction activationFunction) {
        Layer firstLayer = layers.get(0);
        if (inputs.size() != firstLayer.getNeuronCount()) {
            throw new IllegalArgumentException(
                    String.format("Incorrect number of inputs: expected %d, got %d",
                            firstLayer.getNeuronCount(), inputs.size()));
        }
        int inputSize = inputs.size();
        for (int i = 0; i < inputSize; i++) {
            firstLayer.getNeuron(i).addValue(inputs.get(i));
        }
        int layerCount = layers.size();
        for (int i = 0; i < layerCount - 1; i++) {
            Layer layer = layers.get(i);
            Layer nextLayer = layers.get(i + 1);
            layer.apply(activationFunction, nextLayer);
        }
        Layer lastLayer = layers.get(layerCount - 1);
        List<Double> outputs = new ArrayList<>();
        int outputSize = lastLayer.getNeuronCount();
        for (int i = 0; i < outputSize; i++) {
            outputs.add(lastLayer.getNeuron(i).getValue());
        }
        return outputs;
    }

    public void clear() {
        for (Layer layer : layers) {
            layer.clear();
        }
    }

}
