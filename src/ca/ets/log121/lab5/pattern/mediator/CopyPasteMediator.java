package ca.ets.log121.lab5.pattern.mediator;

import ca.ets.log121.lab5.model.PerspectiveModel;
import ca.ets.log121.lab5.pattern.strategy.CopyStrategy;

/**
 * Mediator pour gérer le copier-coller entre perspectives.
 * Patron Singleton.
 */
public class CopyPasteMediator {
    
    private static CopyPasteMediator instance = null;
    private PerspectiveModel copiedModel;
    private CopyStrategy copyStrategy;
    
    /**
     * Constructeur privé pour le singleton.
     */
    private CopyPasteMediator() {
        this.copiedModel = null;
        this.copyStrategy = null;
    }
    
    /**
     * Retourne l'instance unique du CopyPasteMediator.
     * @return L'instance unique
     */
    public static CopyPasteMediator getInstance() {
        if (instance == null) {
            instance = new CopyPasteMediator();
        }
        return instance;
    }
    
    /**
     * Copie un modèle de perspective avec une stratégie donnée.
     * @param model Le modèle à copier
     * @param strategy La stratégie de copie à utiliser
     */
    public void copy(PerspectiveModel model, CopyStrategy strategy) {
        this.copiedModel = model;
        this.copyStrategy = strategy;
    }
    
    /**
     * Colle les paramètres copiés sur le modèle cible.
     * @param target Le modèle cible
     */
    public void paste(PerspectiveModel target) {
        if (copiedModel != null && copyStrategy != null) {
            copyStrategy.copy(copiedModel, target);
        }
    }
    
    /**
     * Vérifie si des données ont été copiées.
     * @return true si des données sont disponibles pour coller
     */
    public boolean hasCopiedData() {
        return copiedModel != null && copyStrategy != null;
    }
}
