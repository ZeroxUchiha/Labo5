import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class PerspectiveModel extends Observable implements Serializable {
    private ImageModel imageModel;


    public PerspectiveModel(ImageModel model){
        this.imageModel = model;

    }


    public Image getImage(){
           return imageModel.getImage();
    }





}
