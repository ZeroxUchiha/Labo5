package ca.ets.log121.lab5.view;

import javax.swing.*;
import java.awt.*;

import ca.ets.log121.lab5.model.ImageModel;
import ca.ets.log121.lab5.pattern.observer.Observer;
import ca.ets.log121.lab5.pattern.observer.Observable;

public class ThumbnailView extends JPanel implements Observer {
    private ImageModel model;

    public ThumbnailView(ImageModel model){
        this.model = model;
        this.model.attach(this);
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createTitledBorder("Vignette"));
    }

    public void update(Observable sujet) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model.getImage() != null) {
            // Calculer les dimensions pour garder les proportions
            int imgWidth = model.getImage().getWidth();
            int imgHeight = model.getImage().getHeight();
            int panelWidth = getWidth() - 20;
            int panelHeight = getHeight() - 40;
            
            double scale = Math.min((double)panelWidth / imgWidth, (double)panelHeight / imgHeight);
            int scaledWidth = (int)(imgWidth * scale);
            int scaledHeight = (int)(imgHeight * scale);
            
            // Centrer l'image
            int x = (getWidth() - scaledWidth) / 2;
            int y = (getHeight() - scaledHeight) / 2 + 10;
            
            g.drawImage(model.getImage(), x, y, scaledWidth, scaledHeight, this);
        }
    }
}
