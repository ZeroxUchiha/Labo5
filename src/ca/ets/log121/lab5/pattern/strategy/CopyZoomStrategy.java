package ca.ets.log121.lab5.pattern.strategy;

import ca.ets.log121.lab5.model.PerspectiveModel;

/**
 * Stratégie pour copier uniquement le zoom.
 */
public class CopyZoomStrategy implements CopyStrategy {
    
    @Override
    public void copy(PerspectiveModel source, PerspectiveModel target) {
        target.setScale(source.getScale());
    }
}
