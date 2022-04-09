package app.cleancode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.SplittableRandom;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.cleancode.network.ActivationFunction;
import app.cleancode.network.Network;
import app.cleancode.network.NetworkDescription;
import app.cleancode.network.trainer.Trainer;
import app.cleancode.util.TextUtils;

public class Entrypoint {
    public static void main(String[] args) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Input> inputs = List.of(
                objectMapper.readValue(Files.readAllBytes(Paths.get("data.json")), Input[].class));
        Scorer scorer = new Scorer();
        Trainer<Input> trainer = new Trainer<>(new NetworkDescription(new int[] {10, 10, 1}),
                inputs, scorer, ActivationFunction.DEFAULT);
        trainer.start();
        SplittableRandom rand = new SplittableRandom();
        for (int i = 0; i < 1000; i++) {
            System.out.println(trainer.getBestScore());
                trainer.train(trainer.getBestScore() * 2, rand);
        }
        System.out.println("Results:");
        System.out.println(trainer.getBestScore());
        Network bestNetwork = trainer.getBestNetwork();
        for (Input input : inputs) {
            double networkOutput =
                    bestNetwork.apply(input.getNetworkInputs(), ActivationFunction.DEFAULT).get(0);
            System.out.printf("%s: %f (score: %f)\n", input.text(), networkOutput,
                    scorer.getScore(input, List.of(networkOutput)));
        }
    }
}
