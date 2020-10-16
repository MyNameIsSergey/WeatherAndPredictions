package com.flex;


public class Info {

    public String name;
    public String description;
    public Info(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + '\n' + description;
    }
}
