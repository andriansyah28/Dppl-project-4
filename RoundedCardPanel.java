import javax.swing.*;
import java.awt.*;
public class RoundedCardPanel extends JPanel {
    private int cornerRadius = 15;
    private Color borderColor = UITheme.BORDER;
    private int borderThickness = 1;
    public RoundedCardPanel() {
        super();
        setOpaque(false); 
    }
    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }
    public void setBorderThickness(int thickness) {
        this.borderThickness = thickness;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g);
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderThickness));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2.dispose();
    }
}