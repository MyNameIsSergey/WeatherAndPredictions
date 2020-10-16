package com.flex.Weather;

import com.flex.Info;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class WeatherConsoleUI implements WeatherUI {
    WeatherOfflineService service;

    public void setService(WeatherOfflineService service) {
        this.service = service;
    }

    public void show()
    {
        Info info = service.getInfo();
        System.out.println(info.name);
        System.out.println(info.description);
        service.findWeather(new GregorianCalendar(), inputDate());
    }

    private GregorianCalendar inputDate()
    {
        Scanner s = new Scanner(System.in);
        try {
            System.out.println("Введите дату (год, месяц, день): ");
            GregorianCalendar date = new GregorianCalendar(s.nextInt(), s.nextInt(), s.nextInt());
            return date;
        } catch (Exception e)
        {
            return inputDate();
        }

    }

    public void showError(WeatherError error)
    {
        String msg = "";
        switch (error)
        {
            case MoneyError:
                msg = "Нужно больше золота";
                break;
            case InvalidDates:
                msg = "Некорректная дата";
                break;
        }
        System.out.println(msg);
    }

    public void showWeatherList(Weather[] weathers, GregorianCalendar date) {
        for (Weather weather : weathers) {
            System.out.print(date.get(Calendar.DAY_OF_MONTH) + ".");
            System.out.print(date.get(Calendar.MONTH) + ".");
            System.out.println(date.get(Calendar.YEAR));
            System.out.println("Описание: " + weather.description);
            System.out.println("Влажность: " + weather.humidity);
            System.out.println("Температура: " + weather.temperature);
            System.out.println("Ветер: " + weather.wind);
            System.out.println("Направление ветра: " + weather.windDirection);

            System.out.println("--------------------------------------");
            date.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
