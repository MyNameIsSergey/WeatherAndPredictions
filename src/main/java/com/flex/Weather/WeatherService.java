package com.flex.Weather;

import com.flex.Info;

import java.util.GregorianCalendar;

public interface WeatherService {
    Info getInfo();
    void findWeather(GregorianCalendar first, GregorianCalendar last);
}
