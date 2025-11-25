import javax.swing.*;
import java.awt.*;

public class ThumbnailView extends JPanel implements Observer {
    private ImageModel model;

    public ThumbnailView(ImageModel model){
        this.model = model;
        this.model.attach(this);
        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.BLACK);
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img = model.getImage();
        if (img == null) return;
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}
