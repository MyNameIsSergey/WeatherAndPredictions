package com.flex;

import com.flex.Prediction.*;
import com.flex.Weather.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    static String url = "jdbc:mysql://localhost:3306/predications?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static Document OpenDOM(String path) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = f.newDocumentBuilder();
        Document doc = builder.parse(new File(path));
        return doc;
    }

    public static Service ConfigureWeatherService() throws IOException, SAXException, ParserConfigurationException, SQLException {
        WeatherGenerator generator = new WeatherGenerator(
                new WeatherDataSequenceXML(OpenDOM("weather.xml")),
                new WeatherDataSequenceXML(OpenDOM("weather1.xml")));

        WeatherConsoleUI serviceIO = new WeatherConsoleUI();
        WeatherOfflineService service = new WeatherOfflineService(serviceIO, generator);
        serviceIO.setService(service);
        return service;
    }
    public static Service ConfigurePredictionService() throws IOException, SAXException, ParserConfigurationException, SQLException {
        PredictionGenerator generator = new PredictionGenerator(
                new PredictionDataSequenceXML(OpenDOM("predictions.xml")),
                new PredictionDataSequenceXML(OpenDOM("predictions1.xml")));

        PredictionConsoleUI UI = new PredictionConsoleUI();
        PredictionOfflineService service = new PredictionOfflineService(UI, generator);
        UI.setService(service);
        return service;
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, SQLException {
        Application application = new Application();

        Service service = ConfigureWeatherService();
        Service predictionService = ConfigurePredictionService();

        application.addService(service);
        application.addService(predictionService);
        MenuUI menuUI = new MenuUI(application);
        menuUI.show();
    }
}

