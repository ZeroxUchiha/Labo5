package ca.ets.log121.lab5.pattern.command;

import ca.ets.log121.lab5.model.PerspectiveModel;

/**
 * Commande pour modifier le zoom d'une perspective.
 */
public class ZoomCommand implements Command {
    
    private PerspectiveModel perspective;
    private double newScale;
    private double prevScale;
    private String description;
    
    /**
     * Constructeur de la commande.
     * @param perspective Le modèle de perspective
     * @param newScale Le nouveau facteur de zoom
     */
    public ZoomCommand(PerspectiveModel perspective, double newScale) {
        this.perspective = perspective;
        this.newScale = newScale;
        this.description = "Zoom: " + newScale;
    }
    
    @Override
    public void execute() {
        // Sauvegarder l'état précédent
        prevScale = perspective.getScale();
        
        // Appliquer le nouveau zoom
        perspective.setScale(newScale);
    }
    
    @Override
    public void undo() {
        // Restaurer l'état précédent
        perspective.setScale(prevScale);
    }
    
    /**
     * Retourne la description de la commande.
     * @return La description
     */
    public String getDescription() {
        return description;
    }
}
