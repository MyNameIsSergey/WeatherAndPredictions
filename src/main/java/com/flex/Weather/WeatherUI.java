package com.flex.Weather;

import com.flex.Info;

import java.util.GregorianCalendar;

public interface WeatherUI {
    void show();
    void showError(WeatherError error);
    void showWeatherList(Weather[] weathers, GregorianCalendar date);
    Info getServiceInfo();
}
