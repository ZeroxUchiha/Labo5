package ca.ets.log121.lab5.pattern.strategy;

import ca.ets.log121.lab5.model.PerspectiveModel;

/**
 * Stratégie pour copier tous les paramètres (zoom et translation).
 */
public class CopyAllStrategy implements CopyStrategy {
    
    @Override
    public void copy(PerspectiveModel source, PerspectiveModel target) {
        target.setScale(source.getScale());
        target.setPosition(source.getPositionX(), source.getPositionY());
    }
}
