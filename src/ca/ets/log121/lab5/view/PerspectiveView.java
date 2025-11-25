package ca.ets.log121.lab5.view;

import javax.swing.*;
import java.awt.*;

import ca.ets.log121.lab5.model.ImageModel;
import ca.ets.log121.lab5.model.PerspectiveModel;
import ca.ets.log121.lab5.pattern.observer.Observer;
import ca.ets.log121.lab5.pattern.observer.Observable;

public class PerspectiveView extends JPanel implements Observer{

    private ImageModel imageModel;
    private PerspectiveModel perspectiveModel;

    public PerspectiveView(ImageModel imageModel, PerspectiveModel perspectiveModel){
        this.imageModel = imageModel;
        this.perspectiveModel = perspectiveModel;
        
        // S'abonner aux deux modèles
        this.imageModel.attach(this);
        this.perspectiveModel.attach(this);
        
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
    }

    public void update(Observable sujet) {
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (imageModel.getImage() != null) {
            Graphics2D g2d = (Graphics2D) g;
            
            // Appliquer la transformation de perspective
            double scale = perspectiveModel.getScale();
            int translateX = perspectiveModel.getTranslateX();
            int translateY = perspectiveModel.getTranslateY();
            
            // Sauvegarder la transformation originale
            var originalTransform = g2d.getTransform();
            
            // Appliquer translation et zoom
            g2d.translate(translateX, translateY);
            g2d.scale(scale, scale);
            
            // Dessiner l'image
            g2d.drawImage(imageModel.getImage(), 0, 0, this);
            
            // Restaurer la transformation
            g2d.setTransform(originalTransform);
        }
    }
}
