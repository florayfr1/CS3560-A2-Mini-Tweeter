package sample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Subject {
    private List<Observer> observers = new ArrayList();

    public Subject() {
    }

    public void attach(Observer observer) {
        this.observers.add(observer);
    }

    public void detach(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        Iterator var2 = this.observers.iterator();

        while(var2.hasNext()) {
            Observer observer = (Observer)var2.next();
            observer.update(this);
        }

    }
}
