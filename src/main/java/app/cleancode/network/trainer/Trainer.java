package app.cleancode.network.trainer;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.TreeSet;
import app.cleancode.network.ActivationFunction;
import app.cleancode.network.Network;
import app.cleancode.network.NetworkDescription;

public class Trainer<Input extends TrainingInput> {
    private static final int NETWORK_COUNT = 65536;

    private final NetworkDescription networkDescription;
    private final List<Input> dataset;
    private final TrainingScorer<Input> scorer;
    private final ActivationFunction activationFunction;
    private final TreeSet<ScoredNetwork> networks;

    public Trainer(NetworkDescription networkDescription, List<Input> dataset,
            TrainingScorer<Input> scorer, ActivationFunction activationFunction) {
        this.networkDescription = networkDescription;
        this.dataset = new ArrayList<>(dataset);
        this.scorer = scorer;
        this.activationFunction = activationFunction;
        this.networks = new TreeSet<>();
    }

    public void start() {
        for (int i = 0; i < NETWORK_COUNT; i++) {
            networks.add(scoreNetwork(networkDescription.generate()));
        }
    }

    public void train(double mutationRate, SplittableRandom rand) {
        List<ScoredNetwork> newNetworks = new ArrayList<>(networks.size());
        for (ScoredNetwork network : networks) {
            newNetworks.add(scoreNetwork(
                    network.network().mutate(mutationRate + rand.nextDouble() - 0.5, rand)));
            if (newNetworks.size() > NETWORK_COUNT / 2) {
                break;
            }
        }
        networks.addAll(newNetworks);
        while (networks.size() > NETWORK_COUNT) {
            networks.pollLast();
        }
    }

    private ScoredNetwork scoreNetwork(Network network) {
        double score = 0;
        for (Input input : dataset) {
            score += scorer.getScore(input,
                    network.apply(input.getNetworkInputs(), activationFunction));
        }
        score /= dataset.size();
        return new ScoredNetwork(score, network);
    }

    public Network getBestNetwork() {
        return networks.first().network();
    }

    public double getBestScore() {
        return networks.first().score();
    }
    
    public void addNetwork(Network network) {
        networks.add(scoreNetwork(network));
    }
}
