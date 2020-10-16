package com.flex;


public abstract class Service {
    protected Info info;

    public Info getInfo() {
        return info;
    }

    public abstract OperationInfo tabLastOperation();

    public abstract void run();
}
