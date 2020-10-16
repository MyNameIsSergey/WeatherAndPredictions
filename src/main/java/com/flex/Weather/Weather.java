package com.flex.Weather;

public class Weather {
    public String description;

    public int temperature;
    public int humidity;
    public int wind;
    public int windDirection;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Weather))
            return false;
        Weather weather = (Weather)obj;
        return (description.regionMatches(false, 0, weather.description, 0, weather.description.length()))
                && temperature == weather.temperature
                && humidity == weather.humidity
                && wind == weather.wind
                && windDirection == weather.windDirection;
    }
}
