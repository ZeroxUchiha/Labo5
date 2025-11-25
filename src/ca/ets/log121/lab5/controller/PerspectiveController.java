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
    private double lastScrollDirection = 0;
    private PerspectiveModel model;
    private CommandManager commandManager;
    private javax.swing.Timer zoomEndTimer;
    private TranslateCommand activeTranslateCommand;

    private boolean zooming = false;
    private double zoom;
    private double lastZoomBeforeScroll = 1.0;

    // Pour le drag
    private int lastX;
    private int lastY;
    
    public PerspectiveController(PerspectiveModel model, PerspectiveView view) {
        this.model = model;
        this.commandManager = CommandManager.getInstance();
        zoom = 1;
        
        // Attacher les listeners à la vue
        view.addMouseListener(this);
        view.addMouseMotionListener(this);
        view.addMouseWheelListener(this);
    }
    
    /**
     * Gère le zoom avec un facteur donné.
     */
    public void handleZoom(double factor) {
        zoom = model.getScale() * factor;
        // Limiter le zoom entre 0.1 et 10 || À voir la valeur à prendre 
        zoom = Math.max(0.1, Math.min(10.0, zoom));
        model.setScale(zoom);

    }
    
    /**
     * Gère la translation avec des deltas.
     */
    public void handleTranslate(int deltaX, int deltaY) {
        int newX = model.getPositionX() + deltaX;
        int newY = model.getPositionY() + deltaY;
        
        TranslateCommand command = new TranslateCommand(model);
        commandManager.executeCommand(command);
    }
    
    /**
     * Gère la molette de la souris pour le zoom.
     */
    public void handleMouseWheel(MouseWheelEvent e) {
        if(!zooming){
            zooming = true;
            lastZoomBeforeScroll = zoom;
        }
        double precise = e.getPreciseWheelRotation();
        double direction = Math.signum(precise);

        // Si le touchpad inverse brièvement la direction → on ignore
        if (direction != 0 && direction != lastScrollDirection) {
            lastScrollDirection = direction;
            return; // ignore le rebond
        }

        lastScrollDirection = direction;

        // facteur de zoom très smoot
        double factor = 1.0 - (precise * 0.05);

        scheduleZoomEnd();
        handleZoom(factor);
    }

    private void scheduleZoomEnd() {
        if (zoomEndTimer != null) {
            zoomEndTimer.stop();
        }

        zoomEndTimer = new javax.swing.Timer(50, evt -> {
            zooming = false;

            ZoomCommand command = new ZoomCommand(model, model.getScale());
            command.setPrevScale(lastZoomBeforeScroll);
            CommandManager.getInstance().executeCommand(command);

            zoomEndTimer.stop();
        });

        zoomEndTimer.setRepeats(false);
        zoomEndTimer.start();
    }
    
    // Implémentation MouseListener

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    @Override
    public void mousePressed(MouseEvent e) {
        activeTranslateCommand = new TranslateCommand(model);
        activeTranslateCommand.setPrevPosition(model.getPositionX(), model.getPositionY());

        lastX = e.getX();
        lastY = e.getY();
    }



    @Override
    public void mouseReleased(MouseEvent e) {
        if (activeTranslateCommand != null) {
            commandManager.executeCommand(activeTranslateCommand);
            activeTranslateCommand = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
    // Implémentation MouseMotionListener
    @Override
    public void mouseMoved(MouseEvent e) {
        // Rien à faire
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int deltaX = e.getX() - lastX;
        int deltaY = e.getY() - lastY;

        int newX = model.getPositionX() + deltaX;
        int newY = model.getPositionY() + deltaY;

        model.setPosition(newX, newY);  //effet visuel immediat

        activeTranslateCommand.setNewPosition(newX, newY); // État sauvegarder pour undo/redo

        lastX = e.getX();
        lastY = e.getY();
    }
    

    
    // Implémentation MouseWheelListener
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        handleMouseWheel(e);
    }
}
