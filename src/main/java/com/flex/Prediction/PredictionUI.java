package com.flex.Prediction;

import com.flex.Info;

public interface PredictionUI {
    void show();

    void showPrediction(Prediction prediction);

    void showError(PredictionError error);

    Info getServiceInfo();
}
