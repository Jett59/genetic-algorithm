package app.cleancode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.IntStream;
import app.cleancode.network.ActivationFunction;
import app.cleancode.network.Network;
import app.cleancode.network.NetworkDescription;

public class Entrypoint {
    public static void main(String[] args) throws Throwable {
        NetworkDescription networkDescription = new NetworkDescription(new int[] {5, 10, 10, 5});
        Network network = networkDescription.generate();
        List<Double> inputs = IntStream.range(0, 5).mapToObj(Double::valueOf).toList();
        List<Double> outputs = network.apply(inputs, ActivationFunction.DEFAULT);
        System.out.println(outputs);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(network);
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        objectOutputStream.close();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Network readNetwork = (Network) objectInputStream.readObject();
        inputStream.close();
        objectInputStream.close();
        List<Double> readNetworkOutputs = readNetwork.apply(inputs, ActivationFunction.DEFAULT);
        System.out.println(readNetworkOutputs);
        System.out.println(outputs.equals(readNetworkOutputs));
    }
}
