package com.flex.Weather;

import com.flex.DBContext;
import com.flex.DataSequence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WeatherContext extends DBContext implements DataSequence<Weather> {
    private Object[] predictions;
    private int counter = 0;

    public WeatherContext(String url) throws SQLException {
        super.Connect(url);
        ResultSet query = super.Query("select temperature, humidity, wind, direction, description from weathers;");
        ArrayList<Weather> list = new ArrayList<>();
        while (query.next()) {
            Weather weather = new Weather();
            weather.description = query.getString("description");
            weather.windDirection = query.getInt("direction");
            weather.wind = query.getInt("wind");
            weather.humidity = query.getInt("humidity");
            weather.temperature = query.getInt("temperature");
            list.add(weather);
        }
        close();
        predictions = list.stream().distinct().toArray();
    }

    @Override
    public Weather NextElement() {
        if(counter < predictions.length)
            return (Weather)predictions[counter++];
        return null;
    }

}
