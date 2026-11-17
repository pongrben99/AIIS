package com.aldisued.iot.monitoring.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MeasurementCalculatorService {


    /**
     * Example:
     * <p>
     * Input values: [10.0, 12.0, 20.0]
     * Deviation: 0.2 (20%)
     * Average = (10 + 12 + 20) / 3 = 14.0
     * Acceptable range = 14.0 - 20% * 14.0, 14.0 + 20% * 14.0] = [11.2, 16.8]
     * Filtered result: [12.0] → only these values remain outside the acceptable range and should be excluded.
     */
    public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
        // TODO: Task 9
        //here should handle all the edgecases like list length etc...
        Objects.requireNonNull(values, "Valueslist is null.");
        Objects.requireNonNull(deviation, "Deviation is null.");

        if (deviation < 0.0 || deviation > 1.0) {
            throw new IllegalArgumentException("Deviation is smaller or bigger than the expected range [0.0, 1.0].");
        }

        //in business case we can check for null values or infinity values rn its not included in the stream filtering
        double avg = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        //eg on avg 14 and 0.2 deviation the num is 2.8
        double margin = Math.abs(avg) * deviation;

        // min val for the filtering
        double min = avg - margin;

        //max val for the filtering
        double max = avg + margin;

        // still need a check of null values or infinity etc.. any other business case.
        return values.stream()
                .filter(val -> val >= min && val <= max)
                .toList();
    }

}
