package ca.ets.log121.lab5.pattern.strategy;

import ca.ets.log121.lab5.model.PerspectiveModel;

/**
 * Interface pour les stratégies de copie de perspective.
 */
public interface CopyStrategy {
    
    /**
     * Copie les paramètres de la source vers la cible.
     * @param source Le modèle de perspective source
     * @param target Le modèle de perspective cible
     */
    void copy(PerspectiveModel source, PerspectiveModel target);
}
