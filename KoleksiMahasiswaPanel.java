import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class KoleksiMahasiswaPanel extends JPanel {
    private MainAppFrame parentFrame;
    private JPanel koleksiGrid;
    // Variabel untuk search bar
    private JTextField judulField;
    private JTextField pengarangField;
    private JTextField isbnField;
    private ArrayList<JPanel> allBookCards = new ArrayList<>();

    public KoleksiMahasiswaPanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(SidebarUtil.createStudentSidebar(parentFrame, MainAppFrame.MHS_KOLEKSI), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
    }

    // Helper untuk membuat input field yang rapi
    private JPanel createSearchField(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        JLabel label = new JLabel(title);
        label.setFont(UITheme.FONT_SMALL_BOLD);
        label.setForeground(UITheme.TEXT_SECONDARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField textField = new JTextField(12);
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Simpan referensi ke variabel instance
        if (title.equals("Judul Buku")) {
            this.judulField = textField;
        } else if (title.equals("Pengarang")) {
            this.pengarangField = textField;
        } else if (title.equals("ISBN")) {
            this.isbnField = textField;
        }
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    private JScrollPane createContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BACKGROUND);
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE,
                UITheme.PADDING_LARGE));
        JLabel header = new JLabel("Koleksi");
        header.setFont(UITheme.FONT_TITLE);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(15));
        // --- Search Bar Mahasiswa (Judul, Pengarang, ISBN) ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        searchPanel.setOpaque(false);
        JPanel judulFieldPanel = createSearchField("Judul Buku");
        JPanel pengarangFieldPanel = createSearchField("Pengarang");
        JPanel isbnFieldPanel = createSearchField("ISBN");
        ModernButton searchButton = new ModernButton("Cari");
        searchButton.setButtonType(ModernButton.ButtonType.PRIMARY);
        ModernButton resetButton = new ModernButton("Reset");
        resetButton.setButtonType(ModernButton.ButtonType.SECONDARY);
        // Action Listeners
        searchButton.addActionListener(e -> filterBooks());
        resetButton.addActionListener(e -> resetFilter());
        searchPanel.add(judulFieldPanel);
        searchPanel.add(pengarangFieldPanel);
        searchPanel.add(isbnFieldPanel);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);
        content.add(searchPanel);
        content.add(Box.createVerticalStrut(20));
        JLabel latestCollection = new JLabel("Koleksi Terbaru");
        latestCollection.setFont(UITheme.FONT_HEADING_2);
        latestCollection.setForeground(UITheme.TEXT_PRIMARY);
        latestCollection.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(latestCollection);
        content.add(Box.createVerticalStrut(10));
        // --- Daftar Koleksi (Grid) ---
        // 5 Kolom Grid
        koleksiGrid = new JPanel(new GridLayout(0, 5, 15, 15));
        koleksiGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        koleksiGrid.setOpaque(false);
        // 10 Kartu
        allBookCards.clear();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                allBookCards.add(createPinjamBookCard("MATEMATIKA DISKRIT", "Rinaldi Munir", "978-602-1514-41-2",
                        "/assets/matematika_diskrit.png"));
            } else {
                allBookCards.add(createPinjamBookCard("Software Engineering", "Ian Sommerville", "978-0133943030",
                        "/assets/software_engineering.png"));
            }
        }
        resetFilter(); // Tampilkan semua di awal
        content.add(koleksiGrid);
        content.add(Box.createVerticalGlue());
        return new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void filterBooks() {
        String judulQuery = judulField.getText().toLowerCase().trim();
        String pengarangQuery = pengarangField.getText().toLowerCase().trim();
        String isbnQuery = isbnField.getText().toLowerCase().trim();
        koleksiGrid.removeAll();
        for (JPanel card : allBookCards) {
            String cardData = card.getName().toLowerCase();
            boolean matchesJudul = judulQuery.isEmpty() || cardData.contains("judul:" + judulQuery);
            boolean matchesPengarang = pengarangQuery.isEmpty() || cardData.contains("pengarang:" + pengarangQuery);
            boolean matchesIsbn = isbnQuery.isEmpty() || cardData.contains("isbn:" + isbnQuery);
            if (matchesJudul && matchesPengarang && matchesIsbn) {
                koleksiGrid.add(card);
            }
        }
        koleksiGrid.revalidate();
        koleksiGrid.repaint();
    }

    private void resetFilter() {
        judulField.setText("");
        pengarangField.setText("");
        isbnField.setText("");
        koleksiGrid.removeAll();
        for (JPanel card : allBookCards) {
            koleksiGrid.add(card);
        }
        koleksiGrid.revalidate();
        koleksiGrid.repaint();
    }

    private JPanel createPinjamBookCard(String title, String author, String isbn, String imagePath) {
        RoundedCardPanel card = new RoundedCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorderColor(UITheme.PRIMARY_LIGHT);
        card.setBorderThickness(3);
        card.setBorder(null);
        card.setBackground(UITheme.SURFACE);
        card.setName(
                "judul:" + title + "|" +
                        "pengarang:" + author + "|" +
                        "isbn:" + isbn);
        JLabel cover;
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = originalIcon.getImage().getScaledInstance(120, 170, Image.SCALE_SMOOTH);
            cover = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            cover = new JLabel("<html><br>[ COVER ]<br>" + title + "</html>", SwingConstants.CENTER);
        }
        cover.setAlignmentX(Component.CENTER_ALIGNMENT);
        cover.setPreferredSize(new Dimension(180, 210));
        cover.setForeground(UITheme.TEXT_SECONDARY);
        // Tambahkan padding di atas cover agar tidak menempel border radius
        cover.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, 0, 0, 0));
        card.add(cover);
        // PERBAIKAN: Mengurangi jarak antara cover dan info
        card.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
        // --- Panel Info (Judul, Pengarang, Deskripsi) ---
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(new EmptyBorder(0, UITheme.PADDING_MEDIUM, 0, UITheme.PADDING_MEDIUM));
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UITheme.FONT_HEADING_3);
        titleLabel.setForeground(UITheme.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(titleLabel);
        JLabel authorLabel = new JLabel("oleh: " + author);
        authorLabel.setFont(UITheme.FONT_SMALL);
        authorLabel.setForeground(UITheme.TEXT_SECONDARY);
        authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(authorLabel);
        JTextArea descArea = new JTextArea("Deskripsi singkat buku... Lorem ipsum dolor sit amet.");
        descArea.setFont(UITheme.FONT_SMALL);
        descArea.setForeground(UITheme.TEXT_HINT);
        descArea.setOpaque(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(descArea);
        card.add(infoPanel);
        // Jarak tetap antara deskripsi dan tombol
        card.add(Box.createVerticalStrut(30));
        ModernButton pinjamButton = new ModernButton("Pinjam Buku");
        pinjamButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pinjamButton.setButtonType(ModernButton.ButtonType.SUCCESS);
        pinjamButton.setMaximumSize(new Dimension(160, 40));
        // Action Pinjam Buku
        pinjamButton.addActionListener(e -> {
            parentFrame.showFormPeminjaman(title, imagePath);
        });
        card.add(pinjamButton);
        card.add(Box.createVerticalStrut(10)); 
        return card;
    }
}