package com.flex.Weather;


import com.flex.Info;
import com.flex.OperationInfo;
import com.flex.Service;
import com.flex.ServiceExecutionResult;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.GregorianCalendar;

public class WeatherOfflineService extends Service implements WeatherService {

    private WeatherGenerator weatherGenerator;
    private WeatherUI view;
    private OperationInfo lastOperation;
    private ServiceExecutionResult executionResult;



    public WeatherOfflineService(WeatherUI io, WeatherGenerator generator) throws ParserConfigurationException, IOException, SAXException {
        view = io;
        weatherGenerator = generator;
    }

    public void findWeather(GregorianCalendar first, GregorianCalendar last) {
        try {
            view.showWeatherList(weatherGenerator.getWeatherByDate(first, last), first);
            lastOperation = new OperationInfo(getInfo());
            lastOperation.time = new GregorianCalendar();
            executionResult = ServiceExecutionResult.Success;
        } catch (Exception e) {
            view.showError(WeatherError.InvalidDates);
            executionResult = ServiceExecutionResult.Cancel;
        }
    }

    @Override
    public Info getInfo() {
        return view.getServiceInfo();
    }

    @Override
    public OperationInfo tabLastOperation() {
        return lastOperation;
    }

    @Override
    public ServiceExecutionResult run() {
        try {
            view.show();
        } catch (Exception e) {
            return ServiceExecutionResult.Error;
        }
        return executionResult;
    }
}
