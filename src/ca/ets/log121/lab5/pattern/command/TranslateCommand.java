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
     */
    public TranslateCommand(PerspectiveModel perspective) {
        this.perspective = perspective;
        this.description = "Translation";
    }

    public void setPrevPosition(int x, int y) {
        this.prevX = x;
        this.prevY = y;
    }


    public void setNewPosition(int x, int y) {
        this.newX = x;
        this.newY = y;
    }
    
    @Override
    public void execute() {
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
