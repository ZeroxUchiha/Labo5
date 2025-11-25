package ca.ets.log121.lab5.pattern.observer;

import java.util.ArrayList;

public class Observable {
    private ArrayList<Observer> observerList;

    public Observable() {
        observerList = new ArrayList<>();
    }
    public void notifyObservers(){
        for (Observer observer : observerList) {
            observer.update(this);
        }
    }
    public void attach(Observer o){
        observerList.add(o);
    }
    public void detach(Observer o){
        observerList.remove(o);
    }
}
