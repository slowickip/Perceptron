package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static Map<String, Integer> values = new HashMap<>();
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Please provide 2 arguments");
        }

        List<Observation> trainingData = getDataFromFile(args[1]);
        Perceptron perceptron = new Perceptron(trainingData.get(0).arguments.size(), 0.01);
        perceptron.train(trainingData, 10000000, 0.00);

        boolean exit = false;

        while (!exit) {
            System.out.println();
            System.out.println(" -- Menu -- ");
            System.out.println("1. Własne dane");
            System.out.println("2. Dane z pliku");
            System.out.println("3. Wyjście");

            String input = new Scanner(System.in).nextLine();

            switch (input) {
                case "1" -> OwnData(perceptron);
                case "2" -> FileData(args[1], perceptron);
                case "3" -> exit = true;
                default -> System.out.println("Niepoprawny wybór");
            }
        }
    }

    private static void FileData(String path, Perceptron perceptron) {
        int successCounter = 0;
        List<Observation> testData = getDataFromFile(path);

        for (Observation observation : testData) {
            if (perceptron.predict(observation) == observation.resultInt) {
                successCounter++;
            }
        }
        System.out.println("Poprawnie zaklasyfikowano " + successCounter + " z " + testData.size() + " obserwacji");
    }

    private static void OwnData(Perceptron perceptron) {
        System.out.println("Podaj dane wejściowe");
        String input = new Scanner(System.in).nextLine();
        String[] split = input.split(",");
        Observation observation = new Observation(split);
        for (Map.Entry<String, Integer> entry : values.entrySet()) {
            if (entry.getValue() == perceptron.predict(observation)) {
                System.out.println("Klasyfikacja: " + entry.getKey());
            }
        }
    }

    private static List<Observation> getDataFromFile(String pathName) {
        List<Observation> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(pathName));) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                String[] split = s.split(",");
                if(!values.containsKey(split[split.length-1])) {
                    values.put(split[split.length-1], values.size());
                }
                records.add(new Observation(split, values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }
}