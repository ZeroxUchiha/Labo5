package ca.ets.log121.lab5.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import ca.ets.log121.lab5.pattern.observer.Observable;

public class ImageModel extends Observable implements Serializable {
    private transient BufferedImage image;
    private String imagePath;
    private String imageName;

    public ImageModel(){
        this.image = null;
        this.imagePath = null;
        this.imageName = null;
    }
    
    /**
     * Constructeur avec chemin d'image.
     */
    public ImageModel(String path) {
        loadImage(path);
    }

    /**
     * Charge une image à partir d'un fichier.
     */
    public void loadImage(String path) {
        try {
            this.image = ImageIO.read(new File(path));
            this.imagePath = path;
            
            // Extraire le nom du fichier
            File file = new File(path);
            this.imageName = file.getName();

            notifyObservers();   // Mise à jour des thumbnails
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void reloadAfterDeserialization() {
        if (imagePath != null) {
            loadImage(imagePath);
        }
    }

    public String getImagePath(){
        return imagePath;
    }
    
    public String getImageName() {
        return imageName;
    }
    
    /**
     * Définit l'image directement.
     */
    public void setImage(BufferedImage img) {
        this.image = img;
        notifyObservers();
    }
}
