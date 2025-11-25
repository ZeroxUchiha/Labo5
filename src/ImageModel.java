
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class ImageModel extends Observable implements Serializable {
    private transient  BufferedImage image;
    private String imagePath;

    public ImageModel(){
        this.image = null;
        this.imagePath = null;
    }

    /**
     * Charge une image à partir d'un fichier.
     */
    public void loadImage(String path) {
        try {
            this.image = ImageIO.read(new File(path));
            this.imagePath = path;

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
}

