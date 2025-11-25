package ca.ets.log121.lab5.pattern.command;

import java.util.Stack;

/**
 * CommandManager - Singleton pour gérer l'exécution et l'annulation des commandes.
 * Maintient un historique des commandes pour les fonctionnalités undo/redo.
 */
public class CommandManager {
    
    private static CommandManager instance = null;
    private Stack<Command> commandHistory;
    private Stack<Command> undoHistory;
    
    /**
     * Constructeur privé pour le singleton.
     */
    private CommandManager() {
        commandHistory = new Stack<>();
        undoHistory = new Stack<>();
    }
    
    /**
     * Retourne l'instance unique du CommandManager.
     * @return L'instance unique
     */
    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }
    
    /**
     * Exécute une commande et l'ajoute à l'historique.
     * @param command La commande à exécuter
     */
    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
        // Vider l'historique redo quand une nouvelle commande est exécutée
        undoHistory.clear();
    }
    
    /**
     * Annule la dernière commande exécutée.
     */
    public void undo() {
        if (!commandHistory.isEmpty()) {
            Command command = commandHistory.pop();
            command.undo();
            undoHistory.push(command);
        }
    }
    
    /**
     * Refait la dernière commande annulée.
     */
    public void redo() {
        if (!undoHistory.isEmpty()) {
            Command command = undoHistory.pop();
            command.execute();
            commandHistory.push(command);
        }
    }
    
    /**
     * Vérifie si une annulation est possible.
     * @return true si une commande peut être annulée
     */
    public boolean canUndo() {
        return !commandHistory.isEmpty();
    }
    
    /**
     * Vérifie si une répétition est possible.
     * @return true si une commande peut être refaite
     */
    public boolean canRedo() {
        return !undoHistory.isEmpty();
    }
    
    /**
     * Vide l'historique des commandes.
     */
    public void clearHistory() {
        commandHistory.clear();
        undoHistory.clear();
    }
    
    /**
     * Retourne la description de la commande qui sera annulée.
     * @return La description ou null si aucune commande à annuler
     */
    public String getUndoDescription() {
        if (!commandHistory.isEmpty()) {
            return commandHistory.peek().getDescription();
        }
        return null;
    }
    
    /**
     * Retourne la description de la commande qui sera refaite.
     * @return La description ou null si aucune commande à refaire
     */
    public String getRedoDescription() {
        if (!undoHistory.isEmpty()) {
            return undoHistory.peek().getDescription();
        }
        return null;
    }
}