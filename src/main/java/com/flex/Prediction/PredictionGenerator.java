package com.flex.Prediction;

import com.flex.DataSequence;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class PredictionGenerator {
    private ArrayList<ArrayList<Prediction>> predictions = new ArrayList<>();

    public PredictionGenerator(DataSequence<Prediction>... dataSequences) throws IOException, ParserConfigurationException, SAXException, SQLException {

        Prediction prediction;
        for (DataSequence<Prediction> parser :
                dataSequences) {

            while ((prediction = parser.NextElement()) != null) {
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
    }

    private ArrayList<Prediction> findPredictionsBySign(String sign) {
        AtomicReference<ArrayList<Prediction>> predictions = new AtomicReference<>();
        this.predictions.forEach((p) -> {
            if (p.get(0).sign.compareTo(sign) == 0) predictions.set(p);
        });
        return predictions.get();
    }

    public Prediction getPrediction(String sign, GregorianCalendar calendar) {

        ArrayList<Prediction> predictions = findPredictionsBySign(sign);
        if (predictions == null)
            return null;
        Prediction prediction = predictions.get((new Random(calendar.get(Calendar.DAY_OF_YEAR))).nextInt(predictions.size()));
        prediction.date = calendar;
        return prediction;
    }
}









