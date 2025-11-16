import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class ModernButton extends JButton {
    public enum ButtonType { PRIMARY, SECONDARY, SUCCESS, DANGER, INFO }
    private ButtonType type = ButtonType.PRIMARY;
    private Color baseColor;
    private Color hoverColor;
    public ModernButton(String text) {
        super(text);
        setFont(UITheme.FONT_BUTTON);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false); // Draw our own background
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(8, 15, 8, 15));
        setButtonType(ButtonType.PRIMARY);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(baseColor);
                repaint();
            }
        });
    }
    public void setButtonType(ButtonType type) {
        this.type = type;
        switch (type) {
            case PRIMARY:
                baseColor = UITheme.PRIMARY;
                hoverColor = UITheme.INFO;
                setForeground(Color.WHITE);
                break;
            case SECONDARY:
                baseColor = UITheme.BORDER;
                hoverColor = UITheme.TEXT_HINT;
                setForeground(UITheme.TEXT_PRIMARY);
                break;
            case SUCCESS:
                baseColor = UITheme.SUCCESS;
                hoverColor = UITheme.withAlpha(UITheme.SUCCESS, 200);
                setForeground(Color.WHITE);
                break;
            case DANGER:
                baseColor = UITheme.DANGER;
                hoverColor = UITheme.withAlpha(UITheme.DANGER, 200);
                setForeground(Color.WHITE);
                break;
            case INFO:
                baseColor = UITheme.INFO;
                hoverColor = UITheme.PRIMARY;
                setForeground(Color.WHITE);
                break;
        }
        super.setBackground(baseColor);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        // Draw Text
        super.paintComponent(g2);
        g2.dispose();
    }
}