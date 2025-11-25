package ca.ets.log121.lab5.model;

import java.io.Serializable;
import ca.ets.log121.lab5.pattern.observer.Observable;

public class PerspectiveModel extends Observable implements Serializable {
    
    private double scale;        // Facteur de zoom
    private int translateX;      // Translation horizontale
    private int translateY;      // Translation verticale
    private ImageModel imageModel;
    private String perspectiveName;
    
    /**
     * Constructeur avec valeurs par défaut.
     */
    public PerspectiveModel() {
        this.scale = 1.0;        // Zoom initial à 100%
        this.translateX = 0;     // Pas de translation
        this.translateY = 0;
        this.perspectiveName = "Perspective";
    }
    
    /**
     * Constructeur avec nom et imageModel (selon diagramme).
     */
    public PerspectiveModel(String name, ImageModel imageModel) {
        this.scale = 1.0;
        this.translateX = 0;
        this.translateY = 0;
        this.perspectiveName = name;
        this.imageModel = imageModel;
    }
    
    /**
     * Constructeur avec paramètres.
     */
    public PerspectiveModel(double scale, int translateX, int translateY) {
        this.scale = scale;
        this.translateX = translateX;
        this.translateY = translateY;
        this.perspectiveName = "Perspective";
    }
    
    // Getters
    public double getScale() {
        return scale;
    }
    
    public int getTranslateX() {
        return translateX;
    }
    
    public int getTranslateY() {
        return translateY;
    }
    
    // Setters avec notification des observateurs
    public void setScale(double scale) {
        this.scale = scale;
        notifyObservers();
    }
    
    public void setTranslateX(int translateX) {
        this.translateX = translateX;
        notifyObservers();
    }
    
    public void setTranslateY(int translateY) {
        this.translateY = translateY;
        notifyObservers();
    }
    
    /**
     * Méthode pour définir tous les paramètres en une fois.
     */
    public void setPerspective(double scale, int translateX, int translateY) {
        this.scale = scale;
        this.translateX = translateX;
        this.translateY = translateY;
        notifyObservers();
    }
    
    /**
     * Réinitialise la perspective aux valeurs par défaut.
     */
    public void resetPerspective() {
        this.scale = 1.0;
        this.translateX = 0;
        this.translateY = 0;
        notifyObservers();
    }
    
    // Alias pour correspondre au diagramme de classes
    public int getPositionX() {
        return translateX;
    }
    
    public int getPositionY() {
        return translateY;
    }
    
    public void setPosition(int x, int y) {
        this.translateX = x;
        this.translateY = y;
        notifyObservers();
    }
    
    public void setPosition(int x) {
        this.translateX = x;
        notifyObservers();
    }
    
    public ImageModel getImageModel() {
        return imageModel;
    }
    
    public String getPerspectiveName() {
        return perspectiveName;
    }
}
