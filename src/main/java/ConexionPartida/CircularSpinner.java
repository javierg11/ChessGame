package ConexionPartida;

import javax.swing.*;
import java.awt.*;


//https://stackoverflow.com/questions/48620030/how-to-make-the-youtubes-rotating-spinner-loading-screen-on-java-swing
public class CircularSpinner extends JComponent {
    private int angle = 0;
    private Timer timer;

    public CircularSpinner(int size) {
        setPreferredSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        setOpaque(false);
        timer = new Timer(50, e -> {
            angle += 10;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight());
        int thickness = size / 10;
        int radius = size / 2 - thickness;
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        // Fondo claro
        g2.setColor(new Color(220, 220, 220, 120));
        g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawArc(cx - radius, cy - radius, 2 * radius, 2 * radius, 0, 360);

        // Arco animado
        g2.setColor(new Color(30, 144, 255));
        g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawArc(cx - radius, cy - radius, 2 * radius, 2 * radius, angle, 90);

        g2.dispose();
    }
}

