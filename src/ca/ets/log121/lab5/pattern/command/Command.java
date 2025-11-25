package ca.ets.log121.lab5.pattern.command;

/**
 * Interface Command pour le patron de conception Command.
 * Permet d'encapsuler une requête en tant qu'objet.
 */
public interface Command {
    
    /**
     * Exécute la commande.
     */
    void execute();
    
    /**
     * Annule la commande (undo).
     */
    void undo();
    
    /**
     * Retourne la description de la commande.
     * @return La description
     */
    String getDescription();
}