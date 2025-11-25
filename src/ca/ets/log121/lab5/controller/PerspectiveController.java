package ca.ets.log121.lab5.controller;

import java.awt.event.*;
import ca.ets.log121.lab5.model.PerspectiveModel;
import ca.ets.log121.lab5.view.PerspectiveView;
import ca.ets.log121.lab5.pattern.command.CommandManager;
import ca.ets.log121.lab5.pattern.command.ZoomCommand;
import ca.ets.log121.lab5.pattern.command.TranslateCommand;

/**
 * Contrôleur pour gérer les interactions souris sur une PerspectiveView.
 */
public class PerspectiveController implements MouseListener, MouseMotionListener, MouseWheelListener {
    
    private PerspectiveModel model;
    private CommandManager commandManager;
    
    // Pour le drag
    private int lastX;
    private int lastY;
    
    public PerspectiveController(PerspectiveModel model, PerspectiveView view) {
        this.model = model;
        this.commandManager = CommandManager.getInstance();
        
        // Attacher les listeners à la vue
        view.addMouseListener(this);
        view.addMouseMotionListener(this);
        view.addMouseWheelListener(this);
    }
    
    /**
     * Gère le zoom avec un facteur donné.
     */
    public void handleZoom(double factor) {
        double newScale = model.getScale() * factor;
        // Limiter le zoom entre 0.1 et 10 || À voir la valeur à prendre 
        newScale = Math.max(0.1, Math.min(10.0, newScale));
        
        ZoomCommand command = new ZoomCommand(model, newScale);
        commandManager.executeCommand(command);
    }
    
    /**
     * Gère la translation avec des deltas.
     */
    public void handleTranslate(int deltaX, int deltaY) {
        int newX = model.getPositionX() + deltaX;
        int newY = model.getPositionY() + deltaY;
        
        TranslateCommand command = new TranslateCommand(model, newX, newY);
        commandManager.executeCommand(command);
    }
    
    /**
     * Gère la molette de la souris pour le zoom.
     */
    public void handleMouseWheel(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        double factor = notches < 0 ? 1.1 : 0.9;
        handleZoom(factor);
    }
    
    // Implémentation MouseListener
    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
       
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
       
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    // Implémentation MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) {
        int deltaX = e.getX() - lastX;
        int deltaY = e.getY() - lastY;
        
        handleTranslate(deltaX, deltaY);
        
        lastX = e.getX();
        lastY = e.getY();
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        // Rien à faire
    }
    
    // Implémentation MouseWheelListener
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        handleMouseWheel(e);
    }
}
