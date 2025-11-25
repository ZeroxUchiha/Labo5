package ca.ets.log121.lab5.view;

import javax.swing.*;
import java.awt.*;

import ca.ets.log121.lab5.model.ImageModel;
import ca.ets.log121.lab5.pattern.observer.Observer;
import ca.ets.log121.lab5.pattern.observer.Observable;

public class ThumbnailView extends View implements Observer {
    private ImageModel imageModel;
    private Dimension preferredSize;

    public ThumbnailView(ImageModel model){
        super();
        this.imageModel = model;
        this.imageModel.attach(this);
        this.preferredSize = new Dimension(400, 400);
    }

    @Override
    protected JPanel createPanel() {
        JPanel customPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ThumbnailView.this.paintComponent(g);
            }
        };
        customPanel.setPreferredSize(preferredSize);
        customPanel.setBackground(Color.LIGHT_GRAY);
        customPanel.setBorder(BorderFactory.createTitledBorder("Vignette"));
        return customPanel;
    }

    @Override
    public void update(Observable sujet) {
        update();
    }

    @Override
    public void update() {
        if (panel != null) {
            panel.repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (imageModel.getImage() != null && panel != null) {
            // Calculer les dimensions pour garder les proportions
            int imgWidth = imageModel.getImage().getWidth();
            int imgHeight = imageModel.getImage().getHeight();
            int panelWidth = panel.getWidth() - 20;
            int panelHeight = panel.getHeight() - 40;
            
            double scale = Math.min((double)panelWidth / imgWidth, (double)panelHeight / imgHeight);
            int scaledWidth = (int)(imgWidth * scale);
            int scaledHeight = (int)(imgHeight * scale);
            
            // Centrer l'image
            int x = (panel.getWidth() - scaledWidth) / 2;
            int y = (panel.getHeight() - scaledHeight) / 2 + 10;
            
            g.drawImage(imageModel.getImage(), x, y, scaledWidth, scaledHeight, panel);
        }
    }
}
