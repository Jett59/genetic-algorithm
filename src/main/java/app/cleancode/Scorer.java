package app.cleancode;

import java.util.List;
import app.cleancode.network.trainer.TrainingScorer;

public class Scorer implements TrainingScorer<Input> {

    @Override
    public double getScore(Input input, List<Double> output) {
        double score = 0;
        for (int i = 0; i < input.numbers().size(); i++) {
            score += Math.pow(input.numbers().get(i) - output.get(i), 2);
        }
        return score;
    }

}
