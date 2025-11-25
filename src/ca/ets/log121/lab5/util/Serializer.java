package ca.ets.log121.lab5.util;

import java.io.*;
import ca.ets.log121.lab5.model.ImageModel;

/**
 * Classe pour gérer la sérialisation et désérialisation des objets.
 */
public class Serializer {
    
    public Serializer() {
    }
    
    /**
     * Sauvegarde un état d'application dans un fichier.
     * @param state L'objet ApplicationState à sauvegarder
     * @param path Le chemin du fichier
     * @return true si succès, false sinon
     */
    public boolean save(ApplicationState state, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(state);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Charge un état d'application depuis un fichier.
     * @param path Le chemin du fichier
     * @return L'objet ApplicationState chargé ou null si échec
     */
    public ApplicationState load(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            ApplicationState state = (ApplicationState) ois.readObject();
            // Recharger les images après désérialisation
            if (state.getImageModel() != null) {
                state.getImageModel().reloadAfterDeserialization();
            }
            return state;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Sauvegarde une image dans un fichier.
     * @param imageModel Le modèle d'image à sauvegarder
     * @param path Le chemin du fichier
     * @return true si succès, false sinon
     */
    public boolean saveImage(ImageModel imageModel, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(imageModel);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Charge une image depuis un fichier.
     * @param path Le chemin du fichier
     * @return Le modèle d'image chargé ou null si échec
     */
    public ImageModel loadImage(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            ImageModel model = (ImageModel) ois.readObject();
            model.reloadAfterDeserialization();
            return model;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
