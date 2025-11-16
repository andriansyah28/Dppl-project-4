import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;
public class AdminDashboardPanel extends JPanel {
    private MainAppFrame parentFrame;
    private LibraryModel model = new LibraryModel();
    public AdminDashboardPanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(SidebarUtil.createAdminSidebar(parentFrame, MainAppFrame.ADMIN_DASHBOARD), BorderLayout.WEST);
        add(createModernContent(), BorderLayout.CENTER);
    }
    public void setUser(User user) {
        // Opsional: Gunakan informasi user jika diperlukan
    }
    private JScrollPane createModernContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BACKGROUND);
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        // Header
        JLabel header = new JLabel("Dashboard");
        header.setFont(UITheme.FONT_TITLE);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(UITheme.SPACING_MEDIUM));
        // Announcement Card
        ModernCardPanel announcementCard = new ModernCardPanel();
        announcementCard.setBackground(new Color(227, 242, 253));
        announcementCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.PRIMARY_LIGHT, 2),
            new EmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM)
        ));
        announcementCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JLabel announcementTitle = new JLabel("Pengumuman Penting");
        announcementTitle.setFont(UITheme.FONT_HEADING_3);
        announcementTitle.setForeground(UITheme.PRIMARY_DARK);
        JLabel announcementText = new JLabel("Tolong rapihin kembali buku ke tempatnya untuk buku yang telah dipinjam");
        announcementText.setFont(UITheme.FONT_BODY);
        announcementText.setForeground(UITheme.TEXT_SECONDARY);
        announcementCard.setLayout(new BoxLayout(announcementCard, BoxLayout.Y_AXIS));
        announcementCard.add(announcementTitle);
        announcementCard.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
        announcementCard.add(announcementText);
        content.add(announcementCard);
        content.add(Box.createVerticalStrut(UITheme.PADDING_LARGE));
        // Statistics Section Title
        JLabel statsTitle = new JLabel("Statistik Perpustakaan");
        statsTitle.setFont(UITheme.FONT_HEADING_2);
        statsTitle.setForeground(UITheme.TEXT_PRIMARY);
        statsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(statsTitle);
        content.add(Box.createVerticalStrut(UITheme.SPACING_MEDIUM));
        // Card Statistik
        JPanel cardPanel = new JPanel(new GridLayout(2, 2, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        cardPanel.setBackground(UITheme.BACKGROUND);
        cardPanel.setOpaque(false);
        cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Fetch data
        Map<String, String> stats = model.getAdminStats();
        cardPanel.add(new StatisticCard("Banyak Anggota", stats.get("Banyak Anggota"), "Total pengguna aktif", UITheme.PRIMARY));
        cardPanel.add(new StatisticCard("Banyak Buku", stats.get("Banyak Buku"), "Total koleksi", UITheme.SUCCESS));
        cardPanel.add(new StatisticCard("Pengunjung", stats.get("Pengunjung"), "Hari ini", UITheme.INFO));
        cardPanel.add(new StatisticCard("Total Denda Masuk", stats.get("Total Denda Masuk").replace("Rp. ", "Rp"), "Tahun ini", UITheme.DANGER));
        content.add(cardPanel);
        content.add(Box.createVerticalStrut(UITheme.PADDING_LARGE));
        // Grafik Section
        JLabel chartTitle = new JLabel("Analitik");
        chartTitle.setFont(UITheme.FONT_HEADING_2);
        chartTitle.setForeground(UITheme.TEXT_PRIMARY);
        chartTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(chartTitle);
        content.add(Box.createVerticalStrut(UITheme.SPACING_MEDIUM));
        JPanel graphPanel = new JPanel(new GridLayout(1, 2, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        graphPanel.setBackground(UITheme.BACKGROUND);
        graphPanel.setOpaque(false);
        graphPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        graphPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        graphPanel.add(createGraphImage("/assets/pengunjung_perpustakaan_chart.png", "Pengunjung Perpustakaan"));
        graphPanel.add(createGraphImage("/assets/buku_dipinjam_chart.png", "Buku yang Dipinjam"));
        content.add(graphPanel);
        return new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    private ModernCardPanel createGraphImage(String imagePath, String title) {
        ModernCardPanel graph = new ModernCardPanel();
        graph.setLayout(new BorderLayout(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        graph.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        graph.setBackground(UITheme.SURFACE);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UITheme.FONT_HEADING_3);
        titleLabel.setForeground(UITheme.TEXT_PRIMARY);
        graph.add(titleLabel, BorderLayout.NORTH);
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = originalIcon.getImage().getScaledInstance(450, 200, Image.SCALE_SMOOTH); 
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
    // --- INNER CLASSES YANG HILANG (FIX) ---
    private static class ModernCardPanel extends JPanel {
        private int cornerRadius = 15;
        private Color borderColor = UITheme.BORDER;
        public ModernCardPanel() {
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Latar Belakang
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            super.paintComponent(g);
            // Border
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
            g2.dispose();
        }
        @Override
        public void setBackground(Color bg) {
            super.setBackground(bg);
            repaint();
        }
    }
    private static class StatisticCard extends ModernCardPanel {
        public StatisticCard(String title, String value, String subtitle, Color accentColor) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(UITheme.SURFACE);
            setBorderColor(UITheme.BORDER);
            // Accent bar
            JPanel accentBar = new JPanel();
            accentBar.setBackground(accentColor);
            accentBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 4));
            accentBar.setMinimumSize(new Dimension(0, 4));
            add(accentBar);
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(UITheme.FONT_BODY);
            titleLabel.setForeground(UITheme.TEXT_SECONDARY);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
            JLabel valueLabel = new JLabel(value);
            valueLabel.setFont(UITheme.FONT_TITLE);
            valueLabel.setForeground(accentColor);
            valueLabel.setBorder(BorderFactory.createEmptyBorder(4, 12, 0, 12));
            JLabel subtitleLabel = new JLabel(subtitle);
            subtitleLabel.setFont(UITheme.FONT_SMALL);
            subtitleLabel.setForeground(UITheme.TEXT_HINT);
            subtitleLabel.setBorder(BorderFactory.createEmptyBorder(4, 12, 12, 12));
            add(titleLabel);
            add(valueLabel);
            add(subtitleLabel);
            add(Box.createVerticalGlue());
        }
        // Overload setBorderColor dari ModernCardPanel
        public void setBorderColor(Color color) {
            super.borderColor = color;
        }
    }
}