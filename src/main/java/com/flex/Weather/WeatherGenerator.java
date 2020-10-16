package com.flex.Weather;


import com.flex.DataSequence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.GregorianCalendar;

public class WeatherGenerator {
    ArrayList<Weather> weathers = new ArrayList<Weather>();
    public WeatherGenerator(DataSequence<Weather>... weatherDataSequences) throws SQLException {

        for (DataSequence<Weather> weatherParser:
                weatherDataSequences) {
            Weather weather;
            while ((weather = weatherParser.NextElement()) != null) {
                if (!weathers.contains(weather))
                    weathers.add(weather);
            }
        }
    }
    public Weather[] getWeatherByDate(GregorianCalendar first, GregorianCalendar last)
    {
        Random random = new Random();
        if(last.before(first))
            throw new IllegalArgumentException("Second date after first");
        int days = last.get(Calendar.DAY_OF_YEAR) - first.get(Calendar.DAY_OF_YEAR);
        Weather[] weathers = new Weather[days];
        for (int i = 0; i < days; i++) {
            weathers[i] = new Weather();
            int seed = first.get(Calendar.DAY_OF_YEAR) + first.get(Calendar.YEAR) + i;
            random.setSeed(seed);
            weathers[i].description = this.weathers.get(random.nextInt(this.weathers.size())).description;
            weathers[i].wind = random.nextInt(10);
            weathers[i].temperature = random.nextInt(20);
            weathers[i].humidity = random.nextInt(100);

        }
        return weathers;
    }
}
