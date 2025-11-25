package ca.ets.log121.lab5.view;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Vue pour la barre de menu de l'application.
 * Gère tous les menus et items de menu.
 */
public class MenuView extends View {
    
    private JMenuBar menuBar;
    
    // Items pour contrôle externe si nécessaire
    private JMenuItem chargerImageItem;
    private JMenuItem sauvegarderPerspectiveItem;
    private JMenuItem changerPerspectiveItem;
    private JMenuItem quitterItem;
    private JMenuItem defaireItem;
    private JMenuItem refaireItem;
    private JMenuItem choisirStrategieItem;
    
    /**
     * Constructeur de la vue menu.
     */
    public MenuView() {
        super();
    }
    
    @Override
    protected JPanel createPanel() {
        // MenuView n'a pas besoin de panel, elle gère seulement la menuBar
        return new JPanel();
    }
    
    @Override
    public void update() {
        // MenuView ne nécessite pas de mise à jour graphique
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // MenuView ne dessine rien directement
    }
    
    /**
     * Retourne la barre de menu.
     * 
     * @return la JMenuBar
     */
    public JMenuBar getMenuBar() {
        return menuBar;
    }
    
    /**
     * Ajoute un ActionListener aux items de menu.
     * 
     * @param listener le listener d'actions
     */
    public void addActionListener(ActionListener listener) {
        if (chargerImageItem != null) chargerImageItem.addActionListener(listener);
        if (sauvegarderPerspectiveItem != null) sauvegarderPerspectiveItem.addActionListener(listener);
        if (changerPerspectiveItem != null) changerPerspectiveItem.addActionListener(listener);
        if (quitterItem != null) quitterItem.addActionListener(listener);
        if (defaireItem != null) defaireItem.addActionListener(listener);
        if (refaireItem != null) refaireItem.addActionListener(listener);
        if (choisirStrategieItem != null) choisirStrategieItem.addActionListener(listener);
    }
    
    /**
     * Active ou désactive le bouton Défaire.
     * 
     * @param enabled true pour activer, false pour désactiver
     */
    public void setDefaireEnabled(boolean enabled) {
        if (defaireItem != null) {
            defaireItem.setEnabled(enabled);
        }
    }
    
    /**
     * Active ou désactive le bouton Refaire.
     * 
     * @param enabled true pour activer, false pour désactiver
     */
    public void setRefaireEnabled(boolean enabled) {
        if (refaireItem != null) {
            refaireItem.setEnabled(enabled);
        }
    }
    
    /**
     * Gère les événements d'action des menus.
     * 
     * @param e l'événement d'action
     */
    public void actionPerformed(ActionEvent e) {
        // Cette méthode peut être utilisée pour centraliser la gestion des événements
        // si nécessaire dans une implémentation future
    }
}
