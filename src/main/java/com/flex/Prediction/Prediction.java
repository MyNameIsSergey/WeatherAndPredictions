package com.flex.Prediction;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Prediction implements Serializable {
    public String text;
    public String sign;
    public GregorianCalendar date;
    public Prediction(String text, String sign, GregorianCalendar date)
    {
        this.date = date;
        this.text = text;
        this.sign = sign;
    }
    public Prediction(String text, String sign)
    {
        this.date = new GregorianCalendar();
        this.text = text;
        this.sign = sign;
    }
    public Prediction(){}

    @Override
    public boolean equals(Object p) {
        if(!(p instanceof Prediction))
            return false;
        Prediction prediction = (Prediction)p;
        return text.regionMatches(false, 0, prediction.text, 0, prediction.text.length())
                && sign.regionMatches(false, 0, prediction.sign, 0, prediction.sign.length());
    }
}
