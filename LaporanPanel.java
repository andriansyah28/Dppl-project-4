import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LaporanPanel extends JPanel {
    private MainAppFrame parentFrame;

    public LaporanPanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(SidebarUtil.createAdminSidebar(parentFrame, MainAppFrame.LAPORAN), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
    }

    private JScrollPane createContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BACKGROUND);
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE,
                UITheme.PADDING_LARGE));
        JLabel header = new JLabel("Laporan");
        header.setFont(UITheme.FONT_TITLE);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(20));
        // --- Filter Berdasarkan Periode ---
        RoundedCardPanel filterContainer = new RoundedCardPanel();
        filterContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterContainer.setBackground(UITheme.SURFACE);
        filterContainer.setBorderColor(UITheme.BORDER);
        filterContainer.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM,
                UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        filterContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        filterPanel.setOpaque(false);
        JTextField tglMulai = new JTextField(8);
        JTextField tglSampai = new JTextField(8);
        filterPanel.add(new JLabel("Mulai Tanggal:"));
        filterPanel.add(tglMulai);
        filterPanel.add(new JLabel("Sampai Tanggal:"));
        filterPanel.add(tglSampai);
        String[] reportTypes = { "Peminjaman", "Pengembalian", "Denda", "Anggota Aktif" };
        JComboBox<String> reportBox = new JComboBox<>(reportTypes);
        filterPanel.add(new JLabel("Jenis Laporan:"));
        filterPanel.add(reportBox);
        ModernButton generateButton = new ModernButton("Generate Laporan");
        generateButton.setButtonType(ModernButton.ButtonType.SUCCESS);
        // Action Generate Laporan
        generateButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Membuat Laporan: " + reportBox.getSelectedItem() +
                        "\nPeriode: " + tglMulai.getText() + " sampai " + tglSampai.getText(),
                "Generate Laporan", JOptionPane.INFORMATION_MESSAGE));
        filterPanel.add(generateButton);
        filterContainer.add(filterPanel, BorderLayout.CENTER);
        content.add(filterContainer);
        content.add(Box.createVerticalStrut(30));
        // --- Area Tampilan Laporan (Placeholder Grafik) ---
        JLabel reportTitle = new JLabel("Preview Data Analisis");
        reportTitle.setFont(UITheme.FONT_HEADING_2);
        reportTitle.setForeground(UITheme.TEXT_PRIMARY);
        reportTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(reportTitle);
        content.add(Box.createVerticalStrut(10));
        JPanel graphPlaceholder = new JPanel(new GridLayout(1, 2, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        graphPlaceholder.setBackground(UITheme.BACKGROUND);
        graphPlaceholder.setOpaque(false);
        graphPlaceholder.setPreferredSize(new Dimension(700, 300));
        graphPlaceholder.setAlignmentX(Component.LEFT_ALIGNMENT);
        graphPlaceholder.add(createReportGraphPlaceholder("Grafik Peminjaman", "/assets/buku_dipinjam_chart.png"));
        graphPlaceholder.add(createReportGraphPlaceholder("Grafik Denda (Placeholder)",
                "/assets/pengunjung_perpustakaan_chart.png"));
        content.add(graphPlaceholder);
        content.add(Box.createVerticalGlue());
        return new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private JPanel createReportGraphPlaceholder(String title, String imagePath) {
        RoundedCardPanel graph = new RoundedCardPanel();
        graph.setLayout(new BorderLayout());
        graph.setBackground(UITheme.SURFACE);
        graph.setBorderColor(UITheme.BORDER);
        graph.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM,
                UITheme.PADDING_MEDIUM));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UITheme.FONT_HEADING_3);
        titleLabel.setForeground(UITheme.TEXT_PRIMARY);
        titleLabel.setBorder(new EmptyBorder(0, 0, UITheme.SPACING_SMALL, 0));
        graph.add(titleLabel, BorderLayout.NORTH);
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = originalIcon.getImage().getScaledInstance(350, 200, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            graph.add(imageLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel placeholder = new JLabel("[ Grafik " + title + " ]", SwingConstants.CENTER);
            placeholder.setFont(UITheme.FONT_BODY);
            placeholder.setForeground(UITheme.TEXT_HINT);
            graph.add(placeholder, BorderLayout.CENTER);
        }
        return graph;
    }
}