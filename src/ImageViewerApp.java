import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImageViewerApp {
    private JFrame mainFrame;
    private JMenuBar menuBar;
    private ImageModel imageModel;
    private List<PerspectiveModel> perspectives;
    private Serializer serializer;
    private List<ThumbnailView> thumbnailViews;
    private List<PerspectiveView> perspectiveViews;
    private List<PerspectiveController> controllers;


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

        JPanel center = new JPanel();
        center.setBackground(Color.DARK_GRAY);
        mainFrame.add(center, BorderLayout.CENTER);

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

        JMenu perspectiveMenu = new JMenu("Perspective");
        JMenuItem miNewPersp = new JMenuItem("Nouvelle perspective");
        miNewPersp.addActionListener(e -> createNewPerspective());
        perspectiveMenu.add(miNewPersp);

        menuBar.add(fileMenu);
        menuBar.add(perspectiveMenu);

        mainFrame.setJMenuBar(menuBar);
    }

    /**
     * Ouvre une image depuis le disque de l'ordi
     */
    public void openImage(){
        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            imageModel.loadImage(chooser.getSelectedFile().getAbsolutePath());

            // Mise à jour des vignettes
            for (ThumbnailView tv : thumbnailViews) {
                tv.update();
            }
        }

    }

    public void saveState(){

    }

    public void loadState(){}

    public void createNewPerspective(){
        PerspectiveModel model = new PerspectiveModel();
        perspectives.add(model);

        PerspectiveView view = new PerspectiveView(model);
        perspectiveViews.add(view);

        PerspectiveController controller = new PerspectiveController(model, view);
        controllers.add(controller);

        // Ajouter visuellement à la fenêtre
        mainFrame.add(view);

        mainFrame.revalidate();
        mainFrame.repaint();

    }

    public void exit(){}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageViewerApp::new);
    }






}
