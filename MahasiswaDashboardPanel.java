import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map; 
public class MahasiswaDashboardPanel extends JPanel {
    private MainAppFrame parentFrame;
    private User user;
    private JLabel headerLabel;
    public MahasiswaDashboardPanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(SidebarUtil.createStudentSidebar(parentFrame, MainAppFrame.MAHASISWA_DASHBOARD), BorderLayout.WEST);
        add(createModernContent(), BorderLayout.CENTER);
    }
    public void setUser(User user) {
        this.user = user;
        if (headerLabel != null) {
            headerLabel.setText("Halo, " + user.getNama() + "!");
        }
    }
    private JScrollPane createModernContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        content.setBackground(UITheme.BACKGROUND);
        headerLabel = new JLabel("Halo, Nama User!");
        headerLabel.setFont(UITheme.FONT_TITLE);
        headerLabel.setForeground(UITheme.TEXT_PRIMARY);
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(headerLabel);
        JLabel nimProdi = new JLabel("NIM 2407123456 - Teknik Informatika");
        nimProdi.setFont(UITheme.FONT_BODY);
        nimProdi.setForeground(UITheme.TEXT_SECONDARY);
        nimProdi.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(nimProdi);
        content.add(Box.createVerticalStrut(UITheme.PADDING_LARGE));
        // Statistics Cards
        JPanel cardPanel = new JPanel(new GridLayout(1, 3, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130)); 
        cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.setOpaque(false);
        cardPanel.add(new StatisticCard("Dipinjam", "1", "Buku aktif", UITheme.PRIMARY));
        cardPanel.add(new StatisticCard("Akan Jatuh Tempo", "1", "Dalam 7 hari", UITheme.WARNING));
        cardPanel.add(new StatisticCard("Terlambat", "0", "Belum ada", UITheme.DANGER));
        content.add(cardPanel);
        content.add(Box.createVerticalStrut(UITheme.PADDING_LARGE));
        // Chart Section
        JLabel chartTitle = new JLabel("Analitik Aktivitas");
        chartTitle.setFont(UITheme.FONT_HEADING_2);
        chartTitle.setForeground(UITheme.TEXT_PRIMARY);
        chartTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(chartTitle);
        content.add(Box.createVerticalStrut(UITheme.SPACING_MEDIUM));
        // PERBAIKAN: Menggunakan GridLayout untuk 2 grafik
        JPanel graphPanel = new JPanel(new GridLayout(1, 2, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        graphPanel.setBackground(UITheme.BACKGROUND);
        graphPanel.setOpaque(false);
        graphPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        graphPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Menambahkan 2 grafik sesuai mockup
        graphPanel.add(createGraphImage("/assets/pengunjung_perpustakaan_chart.png", "Pengunjung Perpustakaan"));
        graphPanel.add(createGraphImage("/assets/buku_dipinjam_chart.png", "Buku yang Dipinjam"));
        content.add(graphPanel);
        content.add(Box.createVerticalGlue());
        return new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    // --- INNER CLASSES (Diambil dari AdminDashboardPanel untuk konsistensi) ---
    private RoundedCardPanel createGraphImage(String imagePath, String title) {
        RoundedCardPanel graph = new RoundedCardPanel();
        graph.setLayout(new BorderLayout(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        graph.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        graph.setBackground(UITheme.SURFACE);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UITheme.FONT_HEADING_3);
        titleLabel.setForeground(UITheme.TEXT_PRIMARY);
        graph.add(titleLabel, BorderLayout.NORTH);
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = originalIcon.getImage().getScaledInstance(350, 200, Image.SCALE_SMOOTH); 
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            graph.add(imageLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel placeholder = new JLabel("[ Grafik Placeholder ]", SwingConstants.CENTER);
            placeholder.setFont(UITheme.FONT_BODY);
            placeholder.setForeground(UITheme.TEXT_HINT);
            graph.add(placeholder, BorderLayout.CENTER);
        }
        return graph;
    }
    private static class StatisticCard extends RoundedCardPanel {
        public StatisticCard(String title, String value, String subtitle, Color accentColor) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.WHITE);
            setBorderColor(UITheme.BORDER);
            // Accent bar
            JPanel accentBar = new JPanel();
            accentBar.setBackground(accentColor);
            accentBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 4));
            accentBar.setMinimumSize(new Dimension(0, 4));
            add(accentBar);
            // Title
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(UITheme.FONT_BODY);
            titleLabel.setForeground(UITheme.TEXT_SECONDARY);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
            add(titleLabel);
            // Value
            JLabel valueLabel = new JLabel(value);
            valueLabel.setFont(UITheme.FONT_TITLE);
            valueLabel.setForeground(accentColor);
            valueLabel.setBorder(BorderFactory.createEmptyBorder(4, 12, 0, 12));
            add(valueLabel);
            // Subtitle
            JLabel subtitleLabel = new JLabel(subtitle);
            subtitleLabel.setFont(UITheme.FONT_SMALL);
            subtitleLabel.setForeground(UITheme.TEXT_HINT);
            subtitleLabel.setBorder(BorderFactory.createEmptyBorder(4, 12, 12, 12));
            add(subtitleLabel);
        }
    }
}