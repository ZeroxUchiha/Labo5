package ca.ets.log121.lab5.view;

import javax.swing.JPanel;
import java.awt.Graphics;

/**
 * Classe abstraite représentant une vue générique.
 * Fournit les éléments de base pour toutes les vues de l'application.
 */
public abstract class View {
    
    /**
     * Le panneau Swing pour cette vue.
     */
    protected JPanel panel;
    
    /**
     * Constructeur de la vue.
     */
    public View() {
        this.panel = createPanel();
    }
    
    /**
     * Crée le panneau pour cette vue.
     * Doit être implémenté par les sous-classes.
     * 
     * @return le panneau JPanel
     */
    protected abstract JPanel createPanel();
    
    /**
     * Met à jour la vue.
     * Doit être implémenté par les sous-classes.
     */
    public abstract void update();
    
    /**
     * Dessine le contenu de la vue.
     * Doit être implémenté par les sous-classes.
     * 
     * @param g le contexte graphique
     */
    public abstract void paintComponent(Graphics g);
    
    /**
     * Retourne le panneau de cette vue.
     * 
     * @return le panneau JPanel
     */
    public JPanel getPanel() {
        return panel;
    }
}
