import java.util.ArrayList;

public class Observable {
    private ArrayList<Observer> observerList;

    public Observable() {
        observerList = new ArrayList<>();
    }
    public void notifyObservers(){

    }
    public void attach(Observer o){
        observerList.add(o);
    }
    public void dettach(Observer o){
        observerList.remove(o);
    }
}
