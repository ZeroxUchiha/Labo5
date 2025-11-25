package ca.ets.log121.lab5.app;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// import depuis le packages que j'ai crée
 
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
        
        // Créer les contrôleurs pour gérer les interactions souris
        PerspectiveController controller1 = new PerspectiveController(persp1, perspView1);
        PerspectiveController controller2 = new PerspectiveController(persp2, perspView2);
        controllers.add(controller1);
        controllers.add(controller2);
        
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

        // Menu Presse-Papier avec stratégies
        JMenu clipboardMenu = new JMenu("Presse-Papier");
        
        JMenu copyMenu = new JMenu("Copier");
        JMenuItem miCopyZoom = new JMenuItem("Copier Zoom");
        JMenuItem miCopyTranslation = new JMenuItem("Copier Translation");
        JMenuItem miCopyAll = new JMenuItem("Copier Tout");
        miCopyZoom.addActionListener(e -> copyWithStrategy("zoom"));
        miCopyTranslation.addActionListener(e -> copyWithStrategy("translation"));
        miCopyAll.addActionListener(e -> copyWithStrategy("all"));
        copyMenu.add(miCopyZoom);
        copyMenu.add(miCopyTranslation);
        copyMenu.add(miCopyAll);
        
        JMenuItem miPaste = new JMenuItem("Coller");
        miPaste.addActionListener(e -> paste());
        
        clipboardMenu.add(copyMenu);
        clipboardMenu.add(miPaste);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(clipboardMenu);

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
            // Les vues seront automatiquement mises à jour via le patron Observer ( vous pouvez regarder le diagramme de classes sur lucidchart )
        }

    }

    public void saveState(){
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Sauvegarder l'état");
        
        if (chooser.showSaveDialog(mainFrame) == javax.swing.JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".dat")) {
                path += ".dat";
            }
            
            ca.ets.log121.lab5.util.ApplicationState state = 
                new ca.ets.log121.lab5.util.ApplicationState(imageModel, perspectives);
            
            if (serializer.save(state, path)) {
                javax.swing.JOptionPane.showMessageDialog(mainFrame, 
                    "État sauvegardé avec succès!", 
                    "Succès", 
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else {
                javax.swing.JOptionPane.showMessageDialog(mainFrame, 
                    "Erreur lors de la sauvegarde", 
                    "Erreur", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadState(){
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Charger un état");
        
        if (chooser.showOpenDialog(mainFrame) == javax.swing.JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            
            ca.ets.log121.lab5.util.ApplicationState state = serializer.load(path);
            
            if (state != null) {
                // Nettoyer l'état actuel
                thumbnailViews.clear();
                perspectiveViews.clear();
                controllers.clear();
                
                // Restaurer l'imageModel et les perspectives
                this.imageModel = state.getImageModel();
                this.perspectives.clear();
                this.perspectives.addAll(state.getPerspectives());
                
                // Reconstruire l'interface
                mainFrame.getContentPane().removeAll();
                
                // Panneau central pour les perspectives restaurées
                JPanel centerPanel = new JPanel();
                centerPanel.setLayout(new GridLayout(1, Math.min(perspectives.size(), 2), 10, 10));
                centerPanel.setBackground(Color.DARK_GRAY);
                centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                // Créer les vues et contrôleurs pour les perspectives restaurées
                for (int i = 0; i < Math.min(perspectives.size(), 2); i++) {
                    PerspectiveModel persp = perspectives.get(i);
                    PerspectiveView perspView = new PerspectiveView(imageModel, persp);
                    PerspectiveController controller = new PerspectiveController(persp, perspView);
                    
                    perspectiveViews.add(perspView);
                    controllers.add(controller);
                    centerPanel.add(perspView);
                }
                
                mainFrame.add(centerPanel, BorderLayout.CENTER);
                
                // Vignette à gauche
                ThumbnailView thumbnail = new ThumbnailView(imageModel);
                thumbnailViews.add(thumbnail);
                mainFrame.add(thumbnail, BorderLayout.WEST);
                
                mainFrame.revalidate();
                mainFrame.repaint();
                
                javax.swing.JOptionPane.showMessageDialog(mainFrame, 
                    "État chargé avec succès!", 
                    "Succès", 
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else {
                javax.swing.JOptionPane.showMessageDialog(mainFrame, 
                    "Erreur lors du chargement", 
                    "Erreur", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

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
     * Copie une perspective avec une stratégie donnée.
     */
    public void copyWithStrategy(String strategyType) {
        // Demander quelle perspective copier
        String[] options = new String[perspectives.size()];
        for (int i = 0; i < perspectives.size(); i++) {
            options[i] = "Perspective " + (i + 1);
        }
        
        String choice = (String) javax.swing.JOptionPane.showInputDialog(
            mainFrame,
            "Choisir la perspective à copier:",
            "Copier",
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (choice != null) {
            int index = java.util.Arrays.asList(options).indexOf(choice);
            PerspectiveModel source = perspectives.get(index);
            
            // Créer la stratégie appropriée
            ca.ets.log121.lab5.pattern.strategy.CopyStrategy strategy;
            switch (strategyType) {
                case "zoom":
                    strategy = new ca.ets.log121.lab5.pattern.strategy.CopyZoomStrategy();
                    break;
                case "translation":
                    strategy = new ca.ets.log121.lab5.pattern.strategy.CopyTranslationStrategy();
                    break;
                case "all":
                default:
                    strategy = new ca.ets.log121.lab5.pattern.strategy.CopyAllStrategy();
                    break;
            }
            
            // Copier avec le mediator
            ca.ets.log121.lab5.pattern.mediator.CopyPasteMediator.getInstance().copy(source, strategy);
            javax.swing.JOptionPane.showMessageDialog(mainFrame, 
                "Paramètres copiés!", 
                "Copie", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Colle les paramètres copiés sur une perspective.
     */
    public void paste() {
        if (!ca.ets.log121.lab5.pattern.mediator.CopyPasteMediator.getInstance().hasCopiedData()) {
            javax.swing.JOptionPane.showMessageDialog(mainFrame, 
                "Aucune donnée à coller!", 
                "Erreur", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Demander quelle perspective coller
        String[] options = new String[perspectives.size()];
        for (int i = 0; i < perspectives.size(); i++) {
            options[i] = "Perspective " + (i + 1);
        }
        
        String choice = (String) javax.swing.JOptionPane.showInputDialog(
            mainFrame,
            "Choisir la perspective destination:",
            "Coller",
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (choice != null) {
            int index = java.util.Arrays.asList(options).indexOf(choice);
            PerspectiveModel target = perspectives.get(index);
            
            ca.ets.log121.lab5.pattern.mediator.CopyPasteMediator.getInstance().paste(target);
            javax.swing.JOptionPane.showMessageDialog(mainFrame, 
                "Paramètres collés!", 
                "Collage", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
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

    public void exit(){
        int response = javax.swing.JOptionPane.showConfirmDialog(mainFrame,
            "Voulez-vous sauvegarder avant de quitter?",
            "Quitter",
            javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE);
        
        if (response == javax.swing.JOptionPane.YES_OPTION) {
            saveState();
            System.exit(0);
        } else if (response == javax.swing.JOptionPane.NO_OPTION) {
            System.exit(0);
        }
        // Si CANCEL, ne rien faire
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageViewerApp::new);
    }






}