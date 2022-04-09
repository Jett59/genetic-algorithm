package app.cleancode;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import app.cleancode.network.trainer.TrainingInput;
import app.cleancode.util.TextUtils;

public record Input(String text, boolean correct, List<Double> networkInputs)
        implements TrainingInput {

    @JsonCreator
    public Input(@JsonProperty("text") String text, @JsonProperty("correct") boolean correct) {
        this(text, correct, TextUtils.toNetworkInputs(text, 10));
    }

    @Override
    public List<Double> getNetworkInputs() {
        return networkInputs;
    }

}
