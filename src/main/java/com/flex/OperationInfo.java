package com.flex;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class OperationInfo implements Serializable {

    public GregorianCalendar time;
    public String name;
    public String description;

    public OperationInfo(){}
    public OperationInfo(Info info)
    {
        name = info.name;
        description = info.description;
    }
}