package com.flex.Prediction;

import com.flex.DBContext;
import com.flex.DataSequence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PredicationContext extends DBContext implements DataSequence<Prediction> {
    private Object[] predictions;
    private int counter = 0;
    public PredicationContext(String url) throws SQLException {
        super.Connect(url);
        ResultSet query = super.Query("SELECT sign, text FROM predications;");
        ArrayList<Prediction> list = new ArrayList<>();
        while (query.next()) {
            list.add(new Prediction(query.getString("sign"), query.getString("text")));
        }
        predictions = list.stream().distinct().toArray();
        close();
    }

    @Override
    public Prediction NextElement() {
        if(counter < predictions.length)
            return (Prediction)predictions[counter++];
        return null;
    }
}