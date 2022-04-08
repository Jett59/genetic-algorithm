package app.cleancode.util;

import java.util.List;
import java.util.stream.IntStream;

public class TextUtils {
    public static List<Double> toNetworkInputs(String txt, int inputCount) {
        if (txt.length() > inputCount) {
            throw new IllegalArgumentException("Too many inputs");
        }
        List<Double> inputs = IntStream.range(0, inputCount)
                .map(i -> i < txt.length() ? txt.charAt(inputCount) : ' ')
                .map(Character::toLowerCase).map(c -> {
                    if (Character.isDigit(c)) {
                        return c - '0';
                    } else if (c >= 'a' && c <= 'z') {
                        return c - 'a' + 10;
                    } else if (c == ' ') {
                        return 1 + 10 + 26;
                    } else {
                        throw new IllegalArgumentException(
                                String.format("Illegal character %c", c));
                    }
                }).mapToDouble(i -> (1 / 40) * i).boxed().toList();
        return inputs;
    }
}
