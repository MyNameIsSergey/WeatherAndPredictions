package com.flex;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

class Application {
    private LinkedList<Service> services = new LinkedList<Service>();
    private ArrayList<OperationInfo> history = new ArrayList<OperationInfo>();
    private String historyFileName = "history.dat";

    public Application() {
        deserializeHistory();
    }
    public void addService(Service service)
    {
        if(service != null)
        {
            services.add(service);
        }
    }
    public void useService(int index) {
        if (services.size() > index && index >= 0) {
            services.get(index).run();
            OperationInfo info = services.get(index).tabLastOperation();
            if(info != null)
                history.add(info);
        }
    }
    public void close() {
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(openOrCreateFile())))
        {
            stream.writeObject(history);
        }
        catch(Exception ex) {
        }
    }

    public Collection<OperationInfo> getHistory() {
        return history;
    }

    public Iterable<Service> getServices() {
        return services;
    }

    private File openOrCreateFile() throws IOException {
        File historyFile = new File(historyFileName);
        if(historyFile.exists() == false)
            historyFile.createNewFile();
        return historyFile;
    }

    private void deserializeHistory() {
        try(ObjectInputStream stream = new ObjectInputStream(new FileInputStream(openOrCreateFile())))
        {
            Object historyBeforeICastToCollection = stream.readObject();
            history = (ArrayList<OperationInfo>)historyBeforeICastToCollection;
        }
        catch(Exception ex) {
            history = new ArrayList<>();
        }
    }
}
/*
        На день матери
    В этот солнечный или дождливый день
    Я тебя поздравляю крепко
    Ты от жизни все больше балдей
    И анекдоты рассказывай метко

    Про подлодку и про жену,
    Пошути очень метко и колко
    А потом я тебе расскажу
    Как родился гербарий у елки

 */