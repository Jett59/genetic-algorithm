package app.cleancode.network;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public record NetworkDescription(int[] layerSizes) {
    public Network generate() {
        List<Layer> layers = new ArrayList<>();
        int layerCount = layerSizes.length;
        for (int i = 0; i < layerCount - 1; i++) {
            int layerSize = layerSizes[i];
            int nextLayerSize = layerSizes[i + 1];
            layers.add(generateLayer(layerSize, nextLayerSize));
        }
        layers.add(generateLayer(layerSizes[layerCount - 1], 0));
        return new Network(layers);
    }

    private Layer generateLayer(int layerSize, int nextLayerSize) {
        List<Neuron> neurons = new ArrayList<>();
        for (int i = 0; i < layerSize; i++) {
            neurons.add(generateNeuron(nextLayerSize));
        }
        return new Layer(neurons);
    }

    private Neuron generateNeuron(int weightCount) {
        List<Double> weights =
                DoubleStream.generate(Math::random).limit(weightCount).boxed().toList();
        return new Neuron(weights);
    }
}
