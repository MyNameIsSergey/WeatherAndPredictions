package com.flex.Weather;


import com.flex.Info;
import com.flex.OperationInfo;
import com.flex.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.GregorianCalendar;

public class WeatherOfflineService extends Service implements WeatherService {

    private WeatherGenerator weatherGenerator;
    private WeatherUI view;
    private OperationInfo lastOperation;



    public WeatherOfflineService(WeatherUI io, WeatherGenerator generator) throws ParserConfigurationException, IOException, SAXException {
        view = io;

        weatherGenerator = generator;
        info = new Info("Погода", "Прогноз погоды");
    }

    public void findWeather(GregorianCalendar first, GregorianCalendar last) {
        try {
            view.showWeatherList(weatherGenerator.getWeatherByDate(first, last), first);
            lastOperation = new OperationInfo(getInfo());
            lastOperation.time = new GregorianCalendar();
        } catch (Exception e) {
            view.showError(WeatherError.InvalidDates);
        }
    }

    @Override
    public OperationInfo tabLastOperation() {
        return lastOperation;
    }

    @Override
    public void run() {
        view.show();
    }
}
