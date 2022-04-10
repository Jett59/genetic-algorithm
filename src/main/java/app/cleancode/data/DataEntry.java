package app.cleancode.data;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.cleancode.Input;

public class DataEntry {
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Input> dataset = new ArrayList<>(List.of(
                objectMapper.readValue(Files.readAllBytes(Paths.get("data.json")), Input[].class)));
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.print(">");
                String command = scanner.nextLine();
                switch (command) {
                    case "put": {
                        System.out.print("Text: ");
                        String text = scanner.nextLine();
                        System.out.print("Correct? ");
                        boolean correct = Boolean.parseBoolean(scanner.nextLine());
                        dataset.add(new Input(text, correct));
                        break;
                    }
                    case "ls": {
                        System.out.println(dataset.stream().map(Input::text)
                                .collect(Collectors.joining("\n")));
                        break;
                    }
                    case "exit": {
                        running = false;
                        break;
                    }
                    default:
                        System.out.println("What?");
                }
            }
        }
        objectMapper.writeValue(new File("data.json"), dataset);
    }
}
