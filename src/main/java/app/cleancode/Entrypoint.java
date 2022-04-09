package app.cleancode;

import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import app.cleancode.network.ActivationFunction;
import app.cleancode.network.NetworkDescription;
import app.cleancode.network.trainer.Trainer;

public class Entrypoint {
    public static void main(String[] args) throws Throwable {
        List<Input> inputs =
                Stream.generate(() -> DoubleStream.generate(Math::random).limit(3).boxed().toList())
                        .limit(100).map(Input::new).toList();
        Trainer<Input> trainer = new Trainer<>(new NetworkDescription(new int[] {3, 1, 3}), inputs,
                new Scorer(), ActivationFunction.PASSTHROUGH);
        trainer.start();
        SplittableRandom rand = new SplittableRandom();
        for (int i = 0; i < 1000; i++) {
            System.out.println(trainer.getBestScore());
            trainer.train(trainer.getBestScore() * 2, rand);
        }
        System.out.println(trainer.getBestScore());
        System.out.println(trainer.getBestNetwork().apply(List.of(0.5d, 0.75d, 1d),
                ActivationFunction.DEFAULT));
    }
}
