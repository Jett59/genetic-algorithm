package app.cleancode;

import java.util.List;
import app.cleancode.network.trainer.TrainingInput;

public record Input(List<Double> numbers) implements TrainingInput {

    @Override
    public List<Double> getNetworkInputs() {
        return numbers;
    }

}
