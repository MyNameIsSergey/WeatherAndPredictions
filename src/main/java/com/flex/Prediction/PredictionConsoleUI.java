package com.flex.Prediction;

import com.flex.Info;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class PredictionConsoleUI implements PredictionUI {
    PredictionService service;

    public void setService(PredictionService service) {
        this.service = service;
    }
    Scanner scanner = new Scanner(System.in);
    public PredictionConsoleUI() {

    }
    @Override
    public void show() {
        Info info = service.getInfo();
        System.out.println(info.name);
        System.out.println(info.description);
        service.predictionBySign(inputSign(), new GregorianCalendar());
    }

    private String inputSign() {
        System.out.println("Введите знак: ");
        return scanner.nextLine();
    }

    @Override
    public void showPrediction(Prediction prediction) {
        System.out.println(prediction.sign);
        System.out.print(prediction.date.get(Calendar.DAY_OF_MONTH) + ".");
        System.out.print(prediction.date.get(Calendar.MONTH) + ".");
        System.out.println(prediction.date.get(Calendar.YEAR));
        System.out.println(prediction.text);
    }

    @Override
    public void showError(PredictionError error)
    {
        String msg;
        switch (error)
        {
            case IncorrectSign:
                msg = "Неверный знак";
                break;
            case MoneyError:
                msg = "Нужно больше золота";
                break;
            default:
                msg = "";
        }
        System.out.println(msg);
    }

    @Override
    public Info getServiceInfo() {
        return new Info("Предсказания", "Предсказание по знаку");
    }
}
