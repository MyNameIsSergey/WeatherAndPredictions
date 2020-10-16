package com.flex.Prediction;

import com.flex.BaseParserXML;
import com.flex.DataSequence;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class PredictionDataSequenceXML extends BaseParserXML implements DataSequence<Prediction>
{
    public PredictionDataSequenceXML(Document doc)
    {
        super(doc.getDocumentElement().getElementsByTagName("prediction"));
    }
    @Override
    public Prediction NextElement() {
        Node node;
        if((node = super.nextChildNode()) == null)
            return null;
        Prediction prediction = new Prediction();
        prediction.text = super.getChildrenValue(node, "text");
        prediction.sign = super.getChildrenValue(node, "sign");
        return prediction;
    }
}


