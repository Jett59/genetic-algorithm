package app.cleancode;

import java.util.List;
import app.cleancode.network.trainer.TrainingScorer;

public class Scorer implements TrainingScorer<Input> {

    @Override
    public double getScore(Input input, List<Double> output) {
        double networkOutput = output.get(0);
        if (input.correct()) {
            return -networkOutput + 1;
        }else {
            return networkOutput;
        }
    }

}
