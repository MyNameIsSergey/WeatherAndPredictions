package com.flex;


import java.util.GregorianCalendar;

public abstract class Service {
    private ServiceStatistic statistic;

    public Service() {
        statistic = new ServiceStatistic();
    }

    protected abstract ServiceExecutionResult run();

    protected abstract OperationInfo tabLastOperation();

    public abstract Info getInfo();

    public OperationInfo execute() {
        statistic.countOfExecuting++;
        statistic.lastExecutionStartTime = new GregorianCalendar();
        statistic.executionResult = run();
        statistic.lastExecutionEndDuration = new GregorianCalendar();
        return tabLastOperation();
    }

    public ServiceStatistic getStatisticPerAppInstance() {
        return statistic;
    }
}

