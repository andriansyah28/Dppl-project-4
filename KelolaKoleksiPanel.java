import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList; 
public class KelolaKoleksiPanel extends JPanel {
    private MainAppFrame parentFrame;
    private JPanel koleksiGrid; 
    private JTextField judulField;
    private JTextField pengarangField;
    private JTextField isbnField;
    private ArrayList<JPanel> allBookCards = new ArrayList<>();
    public KelolaKoleksiPanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(SidebarUtil.createAdminSidebar(parentFrame, MainAppFrame.KELOLA_KOLEKSI), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
    }
    // Helper untuk membuat input field yang rapi
    private JPanel createSearchField(String title, int columns) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        JLabel label = new JLabel(title);
        label.setFont(UITheme.FONT_SMALL_BOLD);
        label.setForeground(UITheme.TEXT_SECONDARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Buat JTextField TAPI jangan simpan di variabel lokal
        JTextField textField = new JTextField(columns);
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
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        content.setBackground(UITheme.BACKGROUND);
        JLabel header = new JLabel("Kelola Koleksi");
        header.setFont(UITheme.FONT_TITLE);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(16));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        searchPanel.setOpaque(false);
        // Panggil helper untuk menginisialisasi instance field
        JPanel judulFieldPanel = createSearchField("Judul Buku", 12);
        JPanel pengarangFieldPanel = createSearchField("Pengarang", 12);
        JPanel isbnFieldPanel = createSearchField("ISBN", 10);
        ModernButton searchButton = new ModernButton("Cari");
        searchButton.setButtonType(ModernButton.ButtonType.PRIMARY);
        ModernButton resetButton = new ModernButton("Reset");
        resetButton.setButtonType(ModernButton.ButtonType.SECONDARY);
        ModernButton tambahButton = new ModernButton("+ Tambah Buku Baru");
        tambahButton.setButtonType(ModernButton.ButtonType.SUCCESS);
        // --- Action Listeners ---
        searchButton.addActionListener(e -> filterBooks());
        resetButton.addActionListener(e -> resetFilter());
        tambahButton.addActionListener(e -> {
            Map<String, String> data = showBukuForm(null); 
            if (data != null && !data.get("Judul").isEmpty()) {
                // Tambahkan kartu baru ke grid
                JPanel newCard = createBookCard(data);
                allBookCards.add(newCard); // Tambahkan ke daftar master
                koleksiGrid.add(newCard);  // Tambahkan ke grid yang terlihat
                // Refresh layout
                koleksiGrid.revalidate();
                koleksiGrid.repaint();
            }
        });
        searchPanel.add(judulFieldPanel);
        searchPanel.add(pengarangFieldPanel);
        searchPanel.add(isbnFieldPanel);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton); // Tambah tombol reset
        searchPanel.add(Box.createHorizontalStrut(20));
        searchPanel.add(tambahButton);
        content.add(searchPanel);
        content.add(Box.createVerticalStrut(20));
        JLabel subheader = new JLabel("Daftar Koleksi");
        subheader.setFont(UITheme.FONT_HEADING_2);
        subheader.setForeground(UITheme.TEXT_PRIMARY);
        subheader.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(subheader);
        content.add(Box.createVerticalStrut(10));
        // Koleksi Grid
        koleksiGrid = new JPanel(new GridLayout(0, 4, 15, 15)); // 4 Kolom
        koleksiGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        koleksiGrid.setOpaque(false);
        // PERBAIKAN: Inisialisasi daftar kartu
        allBookCards.clear();
        allBookCards.add(createBookCard(createBookData("MATEMATIKA DISKRIT", "Rinaldi Munir", "978-602-1514-41-2", "Tersedia")));
        allBookCards.add(createBookCard(createBookData("Software Engineering", "Ian Sommerville", "978-0133943030", "Dipinjam")));
        allBookCards.add(createBookCard(createBookData("Buku Java", "John Doe", "978-1234567890", "Tersedia")));
        allBookCards.add(createBookCard(createBookData("Basis Data", "Fathansyah", "978-9792900732", "Tersedia")));
        allBookCards.add(createBookCard(createBookData("AI Modern", "Stuart Russell", "978-0136042594", "Dipinjam")));
        allBookCards.add(createBookCard(createBookData("Clean Code", "Robert C. Martin", "978-0132350884", "Tersedia")));
        // Tampilkan semua kartu di awal
        resetFilter();
        content.add(koleksiGrid);
        content.add(Box.createVerticalGlue());
        return new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    private void filterBooks() {
        String judulQuery = judulField.getText().toLowerCase().trim();
        String pengarangQuery = pengarangField.getText().toLowerCase().trim();
        String isbnQuery = isbnField.getText().toLowerCase().trim();
        koleksiGrid.removeAll();
        for (JPanel card : allBookCards) {
            String cardData = card.getName().toLowerCase(); // Data pencarian disimpan di .getName()
            // Cek apakah query kosong ATAU data mengandung query
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
    private Map<String, String> createBookData(String title, String author, String isbn, String status) {
        Map<String, String> data = new HashMap<>();
        data.put("Judul", title);
        data.put("Pengarang", author);
        data.put("ISBN", isbn);
        data.put("Status", status);
        data.put("Edisi", "1");
        data.put("Tahun", "2023");
        data.put("Penerbit", "Informatika");
        data.put("Kategori", "Komputer");
        data.put("Jumlah", "10");
        return data;
    }
    private Map<String, String> showBukuForm(Map<String, String> dataBuku) {
        boolean isEditMode = (dataBuku != null);
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        // Field Data (Isi dengan dataBuku jika mode Edit)
        JTextField judulField = new JTextField(isEditMode ? dataBuku.get("Judul") : "", 25);
        JTextField pengarangField = new JTextField(isEditMode ? dataBuku.get("Pengarang") : "", 25);
        JTextField isbnField = new JTextField(isEditMode ? dataBuku.get("ISBN") : "", 25);
        JTextField edisiField = new JTextField(isEditMode ? dataBuku.get("Edisi") : "", 10);
        JTextField tahunTerbitField = new JTextField(isEditMode ? dataBuku.get("Tahun") : "", 10);
        JTextField penerbitField = new JTextField(isEditMode ? dataBuku.get("Penerbit") : "", 25);
        JTextField kategoriField = new JTextField(isEditMode ? dataBuku.get("Kategori") : "", 25);
        JTextField jumlahField = new JTextField(isEditMode ? dataBuku.get("Jumlah") : "", 10);
        // PERBAIKAN: Tambahkan ComboBox Status untuk mode Edit
        String[] statuses = {"Tersedia", "Dipinjam"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        if(isEditMode) {
            statusComboBox.setSelectedItem(dataBuku.get("Status"));
        }
        ModernButton uploadButton = new ModernButton("Upload Cover...");
        uploadButton.setButtonType(ModernButton.ButtonType.INFO);
        // Label
        JLabel[] labels = {
            new JLabel("Judul Buku:"), new JLabel("Pengarang:"), new JLabel("ISBN:"),
            new JLabel("Edisi:"), new JLabel("Tahun Terbit:"), new JLabel("Penerbit:"),
            new JLabel("Kategori:"), new JLabel("Jumlah:"), new JLabel("Cover Buku:")
        };
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(UITheme.FONT_BODY_BOLD);
            labels[i].setForeground(UITheme.TEXT_PRIMARY);
            gbc.gridx = 0; gbc.gridy = i;
            formPanel.add(labels[i], gbc);
        }
        // Field
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(judulField, gbc);
        gbc.gridy++; formPanel.add(pengarangField, gbc);
        gbc.gridy++; formPanel.add(isbnField, gbc);
        gbc.gridy++; formPanel.add(edisiField, gbc);
        gbc.gridy++; formPanel.add(tahunTerbitField, gbc);
        gbc.gridy++; formPanel.add(penerbitField, gbc);
        gbc.gridy++; formPanel.add(kategoriField, gbc);
        gbc.gridy++; formPanel.add(jumlahField, gbc);
        gbc.gridy++; formPanel.add(uploadButton, gbc);
        // Tambahkan Status HANYA jika mode Edit
        if (isEditMode) {
            gbc.gridx = 0; gbc.gridy++;
            JLabel statusLabel = new JLabel("Status:");
            statusLabel.setFont(UITheme.FONT_BODY_BOLD);
            statusLabel.setForeground(UITheme.TEXT_PRIMARY);
            formPanel.add(statusLabel, gbc);
            gbc.gridx = 1;
            formPanel.add(statusComboBox, gbc);
        }
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setPreferredSize(new Dimension(500, 450));
        String dialogTitle = isEditMode ? "Edit Koleksi Buku" : "Tambah Koleksi Buku Baru";
        int result = JOptionPane.showConfirmDialog(this, scrollPane, 
                            dialogTitle, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String judul = judulField.getText().trim();
            if (!judul.isEmpty()) {
                // Kembalikan data
                Map<String, String> data = new HashMap<>();
                data.put("Judul", judul);
                data.put("Pengarang", pengarangField.getText());
                data.put("ISBN", isbnField.getText());
                data.put("Edisi", edisiField.getText());
                data.put("Tahun", tahunTerbitField.getText());
                data.put("Penerbit", penerbitField.getText());
                data.put("Kategori", kategoriField.getText());
                data.put("Jumlah", jumlahField.getText());
                data.put("Status", (String) statusComboBox.getSelectedItem()); // Ambil status
                return data;
            } else {
                JOptionPane.showMessageDialog(this, "Harap isi judul buku!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        return null; // Jika dibatalkan atau error
    }
    private JPanel createBookCard(Map<String, String> bookData) {
        final RoundedCardPanel card = new RoundedCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorderColor(UITheme.BORDER);
        card.setBorder(null); 
        card.setBackground(UITheme.SURFACE);
        card.setPreferredSize(new Dimension(200, 350)); 
        // PERBAIKAN: Simpan data pencarian di 'name'
        card.setName(
            "judul:" + bookData.get("Judul") + "|" +
            "pengarang:" + bookData.get("Pengarang") + "|" +
            "isbn:" + bookData.get("ISBN")
        );
        card.add(Box.createVerticalStrut(UITheme.PADDING_MEDIUM));
        JLabel cover;
        try {
            // Gunakan path gambar yang relevan jika ada, jika tidak, gunakan placeholder
            String imagePath = bookData.get("Judul").contains("Software") ? "/assets/software_engineering.png" : "/assets/matematika_diskrit.png";
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = originalIcon.getImage().getScaledInstance(130, 180, Image.SCALE_SMOOTH);
            cover = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            cover = new JLabel("<html><br>[ COVER ]<br>" + bookData.get("Judul") + "</html>", SwingConstants.CENTER);
        }
        cover.setAlignmentX(Component.CENTER_ALIGNMENT);
        cover.setPreferredSize(new Dimension(180, 190));
        card.add(cover);
        card.add(Box.createVerticalStrut(8));
        // Judul
        final JLabel titleLabel = new JLabel(bookData.get("Judul"));
        titleLabel.setFont(UITheme.FONT_BODY_BOLD);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        // Status
        final JLabel statusLabel = new JLabel(" " + bookData.get("Status") + " ");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setFont(UITheme.FONT_SMALL_BOLD);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(bookData.get("Status").equalsIgnoreCase("Tersedia") ? UITheme.SUCCESS : UITheme.WARNING);
        card.add(statusLabel);
        card.add(Box.createVerticalGlue());
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        actionPanel.setOpaque(false);
        ModernButton editButton = new ModernButton("Edit");
        editButton.setButtonType(ModernButton.ButtonType.SECONDARY);
        ModernButton hapusButton = new ModernButton("Hapus");
        hapusButton.setButtonType(ModernButton.ButtonType.DANGER);
        // PERBAIKAN: Action Edit
        editButton.addActionListener(e -> {
            Map<String, String> newData = showBukuForm(bookData); 
            if (newData != null) {
                // Perbarui label di kartu
                titleLabel.setText(newData.get("Judul"));
                statusLabel.setText(" " + newData.get("Status") + " ");
                statusLabel.setBackground(newData.get("Status").equalsIgnoreCase("Tersedia") ? UITheme.SUCCESS : UITheme.WARNING);
                // Perbarui data internal kartu
                bookData.putAll(newData); 
                card.setName(
                    "judul:" + newData.get("Judul") + "|" +
                    "pengarang:" + newData.get("Pengarang") + "|" +
                    "isbn:" + newData.get("ISBN")
                );
                JOptionPane.showMessageDialog(this, "Buku '" + newData.get("Judul") + "' berhasil diperbarui.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // PERBAIKAN: Action Hapus Dinamis
        hapusButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Anda yakin ingin menghapus koleksi '" + bookData.get("Judul") + "'?",
                    "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                // Hapus kartu dari parent container (koleksiGrid)
                koleksiGrid.remove(card);
                allBookCards.remove(card); 
                koleksiGrid.revalidate();
                koleksiGrid.repaint();
                JOptionPane.showMessageDialog(this, "Buku '" + bookData.get("Judul") + "' berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        actionPanel.add(editButton);
        actionPanel.add(hapusButton);
        card.add(actionPanel);
        card.add(Box.createVerticalStrut(10));
        return card;
    }
}