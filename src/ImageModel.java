
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class ImageModel extends Observable implements Serializable {
    private ArrayList<Observer> observerList;


    public void notifyObservers(){}
    public void attach(Observer o){}
    public void dettach(Observer o){}

}
