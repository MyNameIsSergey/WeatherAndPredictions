package com.flex;


import java.util.*;

public class MenuUI {
    private Application application;
    private boolean exit;

    public MenuUI(Application app) {
        application = app;
    }

    public void show() {
        while (!exit)
            mainNode();
        application.onClose();
    }

    private void mainNode() {

        showMainNode();
        switch (inputChoice())
        {
            case 1:
                servicesNode();
                break;
            case 2:
                billingNode();
                break;
            case 3:
                exit = true;
        }
    }
    private void showMainNode()
    {
        System.out.println("1. Сервисы");
        System.out.println("2. История");
        System.out.println("3. Выход");
        System.out.println("=================================");
    }
    private void billingNode()
    {
        showTab();
    }
    private void showTab()
    {
        Collection<OperationInfo> tabs = application.getHistory();
        if(tabs.size() == 0)
        {
            System.out.println("История пуста");
            System.out.println("--------------------------");
            return;
        }
        for (OperationInfo tab : tabs) {
            System.out.println(tab.name);
            System.out.println(tab.description);
            showDateTime(tab.time);
            System.out.println("--------------------------");
        }
        System.out.println("0. Выход");
    }
    private void showDateTime(GregorianCalendar date)
    {
        System.out.print(date.get(Calendar.DAY_OF_WEEK) + ".");
        System.out.print(date.get(Calendar.MONTH) + ".");
        System.out.print(date.get(Calendar.YEAR) + " ");
        System.out.print(date.get(Calendar.HOUR_OF_DAY) + ":");
        System.out.println(date.get(Calendar.MINUTE));

    }
    private void servicesNode() {

        showServices();
        int choice = inputChoice();
        if (choice == 0)
            return;
        application.useService(--choice);
    }


    private void showServices() {
        Iterable<Service> services = application.getServices();
        int i = 1;
        System.out.println("-------------------------------");
        for (Service service : services) {
            System.out.println(i++ + ". " + service.getInfo().toString());
            System.out.println("Статистика использования (за время запуска приложения):");
            ServiceStatistic statistic = service.getStatisticPerAppInstance();
            System.out.println("Количество запусков: ");
            System.out.println(statistic.countOfExecuting);
            System.out.println("Время последнего запуска: ");
            System.out.println(statistic.lastExecutionStartTime);
            System.out.println("Время окончания выполнения: ");
            System.out.println(statistic.lastExecutionEndDuration);
            System.out.println("Результат выполнения: ");
            System.out.println(statistic.executionResult);
            System.out.println("-------------------------------");
        }
        System.out.println("0. Выход");
        System.out.println("=================================");

    }

    private int inputChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception e)
        {
            return -1;
        }
        return choice;
    }
}