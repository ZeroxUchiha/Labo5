package ca.ets.log121.lab5.util;

import java.io.Serializable;
import java.util.List;
import ca.ets.log121.lab5.model.ImageModel;
import ca.ets.log121.lab5.model.PerspectiveModel;

/**
 * Classe pour encapsuler l'état de l'application à sauvegarder.
 */
public class ApplicationState implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private ImageModel imageModel;
    private List<PerspectiveModel> perspectives;
    
    public ApplicationState(ImageModel imageModel, List<PerspectiveModel> perspectives) {
        this.imageModel = imageModel;
        this.perspectives = perspectives;
    }
    
    public ImageModel getImageModel() {
        return imageModel;
    }
    
    public List<PerspectiveModel> getPerspectives() {
        return perspectives;
    }
}
