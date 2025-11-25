package ca.ets.log121.lab5.pattern.command;

import ca.ets.log121.lab5.model.PerspectiveModel;

/**
 * Commande pour modifier la position (translation) d'une perspective.
 */
public class TranslateCommand implements Command {
    
    private PerspectiveModel perspective;
    private int newX;
    private int newY;
    private int prevX;
    private int prevY;
    private String description;
    
    /**
     * Constructeur de la commande.
     * @param perspective Le modèle de perspective
     * @param newX La nouvelle position X
     * @param newY La nouvelle position Y
     */
    public TranslateCommand(PerspectiveModel perspective, int newX, int newY) {
        this.perspective = perspective;
        this.newX = newX;
        this.newY = newY;
        this.description = "Translation: (" + newX + ", " + newY + ")";
    }
    
    @Override
    public void execute() {
        // Sauvegarder l'état précédent
        prevX = perspective.getPositionX();
        prevY = perspective.getPositionY();
        
        // Appliquer la nouvelle position
        perspective.setPosition(newX, newY);
    }
    
    @Override
    public void undo() {
        // Restaurer l'état précédent
        perspective.setPosition(prevX, prevY);
    }
    
    /**
     * Retourne la description de la commande.
     * @return La description
     */
    public String getDescription() {
        return description;
    }
}
