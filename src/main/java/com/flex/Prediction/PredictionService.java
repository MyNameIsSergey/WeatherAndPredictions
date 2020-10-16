package com.flex.Prediction;


import com.flex.Info;

import java.util.GregorianCalendar;

public interface PredictionService {
    Info getInfo();
    void predictionBySign(String sign, GregorianCalendar date);
}
