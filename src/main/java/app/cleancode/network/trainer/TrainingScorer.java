package app.cleancode.network.trainer;

import java.util.List;

public interface TrainingScorer<T extends TrainingInput> {
    /**
     * Computes the score for a network. Lower scores mean the model is more accurate.
     * 
     * @param input
     * @param output
     * @return the score (where lower scores are better)
     */
    double getScore(T input, List<Double> output);
}
