package ca.ets.log121.lab5.app;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import ca.ets.log121.lab5.model.ImageModel;
import ca.ets.log121.lab5.model.PerspectiveModel;
import ca.ets.log121.lab5.view.ThumbnailView;
import ca.ets.log121.lab5.view.PerspectiveView;
import ca.ets.log121.lab5.controller.PerspectiveController;
import ca.ets.log121.lab5.util.Serializer;
import ca.ets.log121.lab5.pattern.command.CommandManager;
import ca.ets.log121.lab5.pattern.command.LoadImageCommand;

public class ImageViewerApp {
    private JFrame mainFrame;
    private JMenuBar menuBar;
    private ImageModel imageModel;
    private List<PerspectiveModel> perspectives;
    private Serializer serializer;
    private List<ThumbnailView> thumbnailViews;
    private List<PerspectiveView> perspectiveViews;
    private List<PerspectiveController> controllers;
    private CommandManager commandManager;


    public ImageViewerApp(){
        // Initialisation des modèles
        this.imageModel = new ImageModel();
        this.perspectives = new ArrayList<>();

        // Initialisation des vues et contrôleurs
        this.thumbnailViews = new ArrayList<>();
        this.perspectiveViews = new ArrayList<>();
        this.controllers = new ArrayList<>();

        // Gestionnaire de sérialisation
        this.serializer = new Serializer();

        // Gestionnaire de commandes
        this.commandManager = CommandManager.getInstance();

        // Création de l'interface
        createMainInterface();
        createMenuBar();

    }
    /**
     * Création de la fenêtre principale
     */
    public void createMainInterface(){
        mainFrame = new JFrame("Image Viewer App");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1200, 800);
        mainFrame.setLayout(new BorderLayout());

        // Panneau central pour les perspectives
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2, 10, 10));
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Créer 2 perspectives par défaut
        PerspectiveModel persp1 = new PerspectiveModel();
        PerspectiveModel persp2 = new PerspectiveModel();
        perspectives.add(persp1);
        perspectives.add(persp2);
        
        PerspectiveView perspView1 = new PerspectiveView(imageModel, persp1);
        PerspectiveView perspView2 = new PerspectiveView(imageModel, persp2);
        perspectiveViews.add(perspView1);
        perspectiveViews.add(perspView2);
        
        centerPanel.add(perspView1);
        centerPanel.add(perspView2);
        
        mainFrame.add(centerPanel, BorderLayout.CENTER);

        // Vignette à gauche
        ThumbnailView thumbnail = new ThumbnailView(imageModel);
        thumbnailViews.add(thumbnail);
        mainFrame.add(thumbnail, BorderLayout.WEST);

        mainFrame.setVisible(true);
    }


    public void createMenuBar(){
      menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fichier");
        JMenuItem miOpen = new JMenuItem("Ouvrir image");
        JMenuItem miSave = new JMenuItem("Sauvegarder");
        JMenuItem miLoad = new JMenuItem("Charger");
        JMenuItem miExit = new JMenuItem("Quitter");
        miOpen.addActionListener(e -> openImage());
        miSave.addActionListener(e -> saveState());
        miLoad.addActionListener(e -> loadState());
        miExit.addActionListener(e -> exit());

        fileMenu.add(miOpen);
        fileMenu.add(miSave);
        fileMenu.add(miLoad);
        fileMenu.addSeparator();
        fileMenu.add(miExit);

        // Menu Édition avec Undo/Redo
        JMenu editMenu = new JMenu("Édition");
        JMenuItem miUndo = new JMenuItem("Annuler (Undo)");
        JMenuItem miRedo = new JMenuItem("Refaire (Redo)");
        miUndo.addActionListener(e -> undo());
        miRedo.addActionListener(e -> redo());
        editMenu.add(miUndo);
        editMenu.add(miRedo);

        JMenu perspectiveMenu = new JMenu("Perspective");
        JMenuItem miNewPersp = new JMenuItem("Nouvelle perspective");
        miNewPersp.addActionListener(e -> createNewPerspective());
        perspectiveMenu.add(miNewPersp);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(perspectiveMenu);

        mainFrame.setJMenuBar(menuBar);
    }

    /**
     * Ouvre une image depuis le disque de l'ordi
     */
    public void openImage(){
        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            String imagePath = chooser.getSelectedFile().getAbsolutePath();
            LoadImageCommand command = new LoadImageCommand(imageModel, imagePath);
            commandManager.executeCommand(command);
            // Les vues seront automatiquement mises à jour via le patron Observer
        }

    }

    public void saveState(){

    }

    public void loadState(){}

    public void createNewPerspective(){
        // Créer une nouvelle perspective
        PerspectiveModel newPerspective = new PerspectiveModel();
        perspectives.add(newPerspective);
        
        PerspectiveView newView = new PerspectiveView(imageModel, newPerspective);
        perspectiveViews.add(newView);
        
        // Créer une nouvelle fenêtre pour cette perspective
        JFrame perspFrame = new JFrame("Perspective " + perspectives.size());
        perspFrame.setSize(500, 500);
        perspFrame.add(newView);
        perspFrame.setVisible(true);
    }

    /**
     * Annule la dernière commande exécutée.
     */
    public void undo() {
        commandManager.undo();
    }

    /**
     * Refait la dernière commande annulée.
     */
    public void redo() {
        commandManager.redo();
    }

    public void exit(){}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageViewerApp::new);
    }






}