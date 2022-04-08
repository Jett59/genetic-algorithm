package app.cleancode.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class NetworkTest {

    @Test
    public void serializationTest() throws Exception {
        Network originalNetwork = new NetworkDescription(new int[] {2, 3, 2}).generate();
        List<Double> inputs = IntStream.range(0, 2).mapToObj(Double::valueOf).toList();
        List<Double> originalOutputs = originalNetwork.apply(inputs, ActivationFunction.DEFAULT);
        byte[] serializedNetwork = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(originalNetwork);
            serializedNetwork = outputStream.toByteArray();
        }
        Network deserializedNetwork = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(serializedNetwork);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            deserializedNetwork = (Network) objectInputStream.readObject();
        }
        List<Double> deserializedNetworkOutputs =
                deserializedNetwork.apply(inputs, ActivationFunction.DEFAULT);
        assertEquals(originalOutputs, deserializedNetworkOutputs);
    }

}
