package ca.ets.log121.lab5.pattern.command;

import ca.ets.log121.lab5.model.ImageModel;

/**
 * Commande pour charger une image.
 */
public class LoadImageCommand implements Command {
    
    private ImageModel imageModel;
    private String imagePath;
    private String previousImagePath;
    
    /**
     * Constructeur de la commande.
     * @param imageModel Le modèle d'image
     * @param imagePath Le chemin de l'image à charger
     */
    public LoadImageCommand(ImageModel imageModel, String imagePath) {
        this.imageModel = imageModel;
        this.imagePath = imagePath;
    }
    
    @Override
    public void execute() {
        // Sauvegarder l'état précédent pour l'undo
        previousImagePath = imageModel.getImagePath();
        imageModel.loadImage(imagePath);
    }
    
    @Override
    public void undo() {
        // Restaurer l'image précédente
        if (previousImagePath != null) {
            imageModel.loadImage(previousImagePath);
        }
        // Note: Pour une implémentation complète, il faudrait gérer 
        // le cas où il n'y avait pas d'image précédente
    }
}