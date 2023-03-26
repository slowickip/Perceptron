package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perceptron {
    private final List<Double> weights;
    private final double learningRate;
    private double teta;

    public Perceptron(int inputSize, double learningRate) {
        Random random = new Random();
        this.weights = new ArrayList<>();
        for (int i = 0; i < inputSize; i++) {
            this.weights.add(random.nextDouble());
        }
        this.teta = random.nextDouble();
        this.learningRate = learningRate;
    }

    public int predict(Observation observation) {
        double sum = 0;
        for (int i = 0; i < observation.arguments.size(); i++) {
            sum += observation.arguments.get(i) * weights.get(i);
        }
        sum += teta;
        return sum > 0 ? 1 : 0;
    }

    public void train(List<Observation> observations, int epochs, double maxError) {
        double error = Double.MAX_VALUE;
        int epochCtr = 1;
        while (epochCtr <= epochs && error >= maxError) {
            error = 0;
            for (Observation observation : observations) {
                int prediction = predict(observation);
                int target = observation.resultInt;
                double delta = target - prediction;
                for (int i = 0; i < weights.size(); i++) {
                    weights.set(i, weights.get(i) + learningRate * delta * observation.arguments.get(i));
                }
                teta += learningRate * delta;
                error += Math.pow(delta, 2);
            }
            error /= observations.size();
            epochCtr++;
        }
        System.out.println("Epochs: " + (epochCtr - 1));
    }
}
