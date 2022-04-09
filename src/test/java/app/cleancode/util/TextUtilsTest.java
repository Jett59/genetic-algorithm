package app.cleancode.util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TextUtilsTest {
@Test
public void toNetworkInputsTest() throws Exception {
    String text1 = "0123456789";
    List<Double> networkInputs1 = TextUtils.toNetworkInputs(text1, 10);
    String text2 = "abcdefghij";
    List<Double> networkInputs2 = TextUtils.toNetworkInputs(text2, 10);
    assertNotEquals(networkInputs1, networkInputs2);
}
}
