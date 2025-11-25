package ca.ets.log121.lab5.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ca.ets.log121.lab5.model.ImageModel;
import ca.ets.log121.lab5.model.PerspectiveModel;
import ca.ets.log121.lab5.pattern.observer.Observer;
import ca.ets.log121.lab5.pattern.observer.Observable;

public class PerspectiveView extends View implements Observer {

    private PerspectiveModel perspectiveModel;

    public PerspectiveView(ImageModel imageModel, PerspectiveModel perspectiveModel){
        super();
        this.perspectiveModel = perspectiveModel;
        
        // S'abonner aux deux modèles
        imageModel.attach(this);
        this.perspectiveModel.attach(this);
    }

    @Override
    protected JPanel createPanel() {
        JPanel customPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                PerspectiveView.this.paintComponent(g);
            }
        };
        customPanel.setPreferredSize(new Dimension(400, 400));
        customPanel.setBackground(Color.WHITE);
        customPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
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
        if (perspectiveModel.getImageModel().getImage() != null) {
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
            g2d.drawImage(perspectiveModel.getImageModel().getImage(), 0, 0, panel);
            
            // Restaurer la transformation
            g2d.setTransform(originalTransform);
        }
    }

    /**
     * Ajoute un MouseListener au panel.
     * 
     * @param listener le MouseListener à ajouter
     */
    public void addMouseListener(MouseListener listener) {
        if (panel != null) {
            panel.addMouseListener(listener);
        }
    }

    /**
     * Ajoute un MouseMotionListener au panel.
     * 
     * @param listener le MouseMotionListener à ajouter
     */
    public void addMouseMotionListener(MouseMotionListener listener) {
        if (panel != null) {
            panel.addMouseMotionListener(listener);
        }
    }

    /**
     * Ajoute un MouseWheelListener au panel.
     * 
     * @param listener le MouseWheelListener à ajouter
     */
    public void addMouseWheelListener(java.awt.event.MouseWheelListener listener) {
        if (panel != null) {
            panel.addMouseWheelListener(listener);
        }
    }

    /**
     * Crée et affiche un menu contextuel pour cette perspective.
     * 
     * @param copyAction action à exécuter pour copier
     * @param pasteAction action à exécuter pour coller
     */
    public void setupContextMenu(Runnable copyAction, Runnable pasteAction) {
        JPopupMenu contextMenu = new JPopupMenu();
        
        JMenuItem copyItem = new JMenuItem("Copier Tout");
        copyItem.addActionListener(e -> copyAction.run());
        
        JMenuItem pasteItem = new JMenuItem("Coller");
        pasteItem.addActionListener(e -> pasteAction.run());
        
        contextMenu.add(copyItem);
        contextMenu.add(pasteItem);
        
        // Ajouter le menu contextuel au panel
        if (panel != null) {
            panel.setComponentPopupMenu(contextMenu);
        }
    }
}
