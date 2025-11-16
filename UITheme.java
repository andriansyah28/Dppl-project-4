import java.awt.*;
public class UITheme {
    // Colors
    public static final Color PRIMARY = new Color(74, 144, 226); // Blue
    public static final Color PRIMARY_DARK = new Color(44, 62, 80); // Dark Blue (for sidebar/text)
    public static final Color PRIMARY_LIGHT = new Color(200, 220, 255); // Light Blue (active state)
    public static final Color PRIMARY_LIGHTER = new Color(220, 235, 255);

    public static final Color SUCCESS = new Color(46, 204, 113); // Green
    public static final Color DANGER = new Color(231, 76, 60);    // Red
    public static final Color WARNING = new Color(243, 156, 18);   // Orange
    public static final Color INFO = new Color(52, 152, 219);     // Light Blue

    public static final Color BACKGROUND = new Color(245, 247, 249); // Latar Belakang Aplikasi
    public static final Color SURFACE = Color.WHITE; // Latar Belakang Kartu/Panel
    public static final Color BORDER = new Color(220, 220, 220);
    
    public static final Color TEXT_PRIMARY = PRIMARY_DARK;
    public static final Color TEXT_SECONDARY = new Color(100, 100, 100);
    public static final Color TEXT_HINT = new Color(180, 180, 180);
    public static final Color TEXT_LIGHT = Color.WHITE;
    
    public static final Color SIDEBAR_BACKGROUND = new Color(44, 62, 80); // Dark Blue

    // Fonts
    public static final Font FONT_TITLE = new Font("Arial", Font.BOLD, 30);
    public static final Font FONT_HEADING_1 = new Font("Arial", Font.BOLD, 24);
    public static final Font FONT_HEADING_2 = new Font("Arial", Font.BOLD, 20);
    public static final Font FONT_HEADING_3 = new Font("Arial", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("Arial", Font.PLAIN, 14);
    public static final Font FONT_BODY_BOLD = new Font("Arial", Font.BOLD, 14);
    public static final Font FONT_SMALL = new Font("Arial", Font.PLAIN, 12);
    public static final Font FONT_SMALL_BOLD = new Font("Arial", Font.BOLD, 12);
    public static final Font FONT_BUTTON = new Font("Arial", Font.BOLD, 14);

    // Spacing & Padding
    public static final int SPACING_SMALL = 5;
    public static final int SPACING_MEDIUM = 10;
    public static final int SPACING_LARGE = 15;
    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 12;
    public static final int PADDING_LARGE = 20;
    public static final int PADDING_EXTRA_LARGE = 40;

    // Utility
    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}