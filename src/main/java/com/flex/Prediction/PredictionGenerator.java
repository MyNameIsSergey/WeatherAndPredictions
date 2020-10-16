package com.flex.Prediction;

import com.flex.DataSequence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class PredictionGenerator {
    private ArrayList<ArrayList<Prediction>> predictions = new ArrayList<>();

    public PredictionGenerator(DataSequence<Prediction>... dataSequences) throws SQLException {
        ExecutorService executor = Executors.newWorkStealingPool(dataSequences.length);

        for (DataSequence<Prediction> sequence : dataSequences) {
            executor.execute(() -> addPredictionsSafe(sequence));
        }
        try {
            executor.wait();
        } catch (Exception e) { }
    }
    public Prediction getPrediction(String sign, GregorianCalendar calendar) {

        ArrayList<Prediction> predictions = findPredictionsBySign(sign);
        if (predictions == null)
            return null;
        Prediction prediction = predictions.get((new Random(calendar.get(Calendar.DAY_OF_YEAR))).nextInt(predictions.size()));
        prediction.date = calendar;
        return prediction;
    }
    void addPredictionsSafe(DataSequence<Prediction> predictionsSource) {
        try {
            addPredictions(predictionsSource);
        } catch (Exception e) { }
    }
    private void addPredictions(DataSequence<Prediction> predictionsSource) throws SQLException {
        Prediction prediction;
        while ((prediction = predictionsSource.NextElement()) != null) {
            ArrayList<Prediction> predictions = findPredictionsBySign(prediction.sign);
            if (predictions != null) {
                if (!predictions.contains(prediction))
                    predictions.add(prediction);
            } else {
                predictions = new ArrayList<>();
                predictions.add(prediction);
                this.predictions.add(predictions);
            }
        }
    }
    private ArrayList<Prediction> findPredictionsBySign(String sign) {
        AtomicReference<ArrayList<Prediction>> predictions = new AtomicReference<>();
        this.predictions.forEach((p) -> {
            if (p.get(0).sign.compareTo(sign) == 0) predictions.set(p);
        });
        return predictions.get();
    }
}