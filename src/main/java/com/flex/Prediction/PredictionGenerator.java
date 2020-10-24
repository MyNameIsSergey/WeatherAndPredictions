package com.flex.Prediction;

import com.flex.DataSequence;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PredictionGenerator {
    private Map<String, List<Prediction>> predictions = new HashMap<>();

    public PredictionGenerator(DataSequence<Prediction>... dataSequences) {
        ExecutorService executor = Executors.newWorkStealingPool(dataSequences.length);

        Arrays.stream(dataSequences).forEach(sequence -> {
            executor.execute(() -> addPredictionsSafe(sequence));
        });
        try {
            executor.wait();
        } catch (Exception e) {
        }
    }

    public Prediction getPrediction(String sign, GregorianCalendar calendar) {

        List<Prediction> predictions = getPredictionsBySign(sign);
        if (predictions == null)
            return null;
        Prediction prediction = predictions.get((new Random(calendar.get(Calendar.DAY_OF_YEAR))).nextInt(predictions.size()));
        prediction.date = calendar;
        return prediction;
    }

    void addPredictionsSafe(DataSequence<Prediction> predictionsSource) {
        try {
            addPredictions(predictionsSource);
        } catch (Exception e) {
        }
    }

    private void addPredictions(DataSequence<Prediction> predictionsSource) throws SQLException {
        Prediction prediction;
        while ((prediction = predictionsSource.NextElement()) != null) {
            List<Prediction> predictions = getPredictionsBySign(prediction.sign);
            if (predictions != null) {
                if (!predictions.contains(prediction))
                    predictions.add(prediction);
            } else {
                predictions = new ArrayList<>();
                predictions.add(prediction);
                this.predictions.put(prediction.sign, predictions);
            }
        }
    }

    private List<Prediction> getPredictionsBySign(String sign) {
        return predictions.get(sign);
    }
}