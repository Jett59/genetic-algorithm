package app.cleancode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
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
        Trainer<Input> trainer = new Trainer<>(new NetworkDescription(new int[] {10, 20, 25, 30, 1}),
                inputs, scorer, ActivationFunction.DEFAULT);
        if (Files.exists(Paths.get("best.bin"))) {
            try (FileInputStream inputStream = new FileInputStream(new File("best.bin"));
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                trainer.addNetwork((Network)objectInputStream.readObject());
            }
        }
        trainer.start();
        SplittableRandom rand = new SplittableRandom();
        long startTime = System.nanoTime();
        for (int i = 0; i < 512; i++) {
            if (i % 64 == 0) {
                System.out.println(trainer.getBestScore());
            }
            trainer.train(20000, rand);
        }
        long totalTime = System.nanoTime() - startTime;
        System.out.println("Results:");
        System.out.printf("Trained for %.3fs\n", (double)totalTime / 1000000000d);
        System.out.println(trainer.getBestScore());
        Network bestNetwork = trainer.getBestNetwork();
        for (Input input : inputs) {
            double networkOutput =
                    bestNetwork.apply(input.getNetworkInputs(), ActivationFunction.DEFAULT).get(0);
            System.out.printf("%s: %f (score: %f)\n", input.text(), networkOutput,
                    scorer.getScore(input, List.of(networkOutput)));
        }
        try (FileOutputStream outputStream = new FileOutputStream(new File("best.bin"));
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(bestNetwork);
        }
        System.out.println("Try it!");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Text: ");
                String text = scanner.nextLine();
                if (text.length() > 10) {
                    System.out.println("Too long!");
                } else {
                    double correctnessProbability = bestNetwork
                            .apply(TextUtils.toNetworkInputs(text, 10), ActivationFunction.DEFAULT)
                            .get(0);
                    if (correctnessProbability > 0.5) {
                        System.out.printf("Probably (%f)\n", correctnessProbability);
                    } else {
                        System.out.printf("Probably not (%f)\n", correctnessProbability);
                    }
                }
            }
        }
    }
}
