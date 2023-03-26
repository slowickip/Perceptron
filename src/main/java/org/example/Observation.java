package org.example;

import java.util.*;

public class Observation {
    List<Double> arguments = new ArrayList<>();
    String result;
    int resultInt;

    public Observation(String[] values) {
        for (String value : values) {
            arguments.add(Double.parseDouble(value));
        }
        this.result = "";
    }

    public Observation(String[] values, Map<String,Integer> mapping) {
        for (int i = 0; i < values.length-1; i++) {
            arguments.add(Double.parseDouble(values[i]));
        }
        result = values[values.length-1];
        resultInt = mapping.get(result);
    }
}
