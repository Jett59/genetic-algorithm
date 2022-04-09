package app.cleancode.network.trainer;

import app.cleancode.network.Network;

public record ScoredNetwork(double score, Network network) implements Comparable<ScoredNetwork> {

    @Override
    public int compareTo(ScoredNetwork other) {
        return Double.compare(score, other.score);
    }
}
