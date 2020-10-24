package com.flex.Weather;


import com.flex.DataSequence;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherGenerator {
    ArrayList<Weather> weathers = new ArrayList<Weather>();

    public WeatherGenerator(DataSequence<Weather>... weatherDataSequences) throws SQLException {
        ExecutorService executor = Executors.newWorkStealingPool(weatherDataSequences.length);

        Arrays.stream(weatherDataSequences).forEach(dataSequence -> {
            executor.execute(() -> addWeathers(dataSequence));
        });
        try {
            executor.wait();
        } catch (Exception e) {
        }
    }

    public Weather[] getWeatherByDate(GregorianCalendar first, GregorianCalendar last) {
        if (last.before(first))
            throw new IllegalArgumentException("Second date after first");
        int days = last.get(Calendar.DAY_OF_YEAR) - first.get(Calendar.DAY_OF_YEAR);
        Weather[] weathers = new Weather[days];
        int startSeed = first.get(Calendar.DAY_OF_YEAR) + first.get(Calendar.YEAR);
        randomizeWeather(weathers, startSeed);
        return weathers;
    }

    private Weather[] randomizeWeather(Weather[] weathers, int startSeed) {
        Random random = new Random();
        for (int i = 0; i < weathers.length; i++) {
            random.setSeed(startSeed++);
            weathers[i] = new Weather();
            weathers[i].description = this.weathers.get(random.nextInt(this.weathers.size())).description;
            weathers[i].wind = random.nextInt(10);
            weathers[i].temperature = random.nextInt(20);
            weathers[i].humidity = random.nextInt(100);
        }
        return weathers;
    }

    private void addWeathers(DataSequence<Weather> weathers) {
        Weather weather;
        try {
            while ((weather = weathers.NextElement()) != null) {
                if (!this.weathers.contains(weather))
                    this.weathers.add(weather);
            }
        } catch (SQLException exception) {
        }
    }
}
