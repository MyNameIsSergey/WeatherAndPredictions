package com.flex.Prediction;

import com.flex.Info;
import com.flex.OperationInfo;
import com.flex.Service;
import com.flex.ServiceExecutionResult;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.GregorianCalendar;

public class PredictionOfflineService extends Service implements PredictionService {
    private PredictionUI view;
    private PredictionGenerator generator;
    private OperationInfo operationInfo;
    private ServiceExecutionResult executionResult;

    public PredictionOfflineService(PredictionUI view, PredictionGenerator generator) throws IOException, ParserConfigurationException, SAXException {
        this.view = view;
        this.generator = generator;
    }

    @Override
    public Info getInfo() {
        return view.getServiceInfo();
    }

    @Override
    public OperationInfo tabLastOperation() {
        return operationInfo;
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

    public void predictionBySign(String sign, GregorianCalendar date) {
        Prediction prediction = generator.getPrediction(sign, date);
        if (prediction == null) {
            view.showError(PredictionError.IncorrectSign);
            operationInfo = null;
            executionResult = ServiceExecutionResult.Cancel;
        } else {
            view.showPrediction(prediction);
            operationInfo = new OperationInfo(view.getServiceInfo());
            operationInfo.time = new GregorianCalendar();
            executionResult = ServiceExecutionResult.Success;
        }
    }
}