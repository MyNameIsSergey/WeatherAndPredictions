package com.flex.Weather;

import com.flex.BaseParserXML;
import com.flex.DataSequence;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class WeatherDataSequenceXML extends BaseParserXML implements DataSequence<Weather> {

    public WeatherDataSequenceXML(Document doc) {
        super(doc.getDocumentElement().getElementsByTagName("weather"));
    }

    @Override
    public Weather NextElement() {
        Node node;
        if((node = super.nextChildNode()) == null)
            return null;
        Weather weather = new Weather();
        weather.description = super.getChildrenValue(node,"description");

        return weather;
    }
}
