package ca.ets.log121.lab5.pattern.strategy;

import ca.ets.log121.lab5.model.PerspectiveModel;

/**
 * Stratégie pour copier uniquement la translation (position).
 */
public class CopyTranslationStrategy implements CopyStrategy {
    
    @Override
    public void copy(PerspectiveModel source, PerspectiveModel target) {
        target.setPosition(source.getPositionX(), source.getPositionY());
    }
}
