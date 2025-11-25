import javax.swing.*;
import java.awt.*;

public class PerspectiveView extends JPanel implements Observer{
    private PerspectiveModel perspectiveModel;



    public PerspectiveView(PerspectiveModel model){
        this.perspectiveModel = model;
        this.perspectiveModel.attach(this);
        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.BLACK);
    }
    public void update() {
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img = perspectiveModel.getImage();
        if (img == null) return;
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}
