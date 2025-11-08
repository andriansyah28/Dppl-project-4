// Nama File : HalamanRiwayat.java
// Deskripsi : Menampilkan halaman riwayat peminjaman buku
// Dibuat oleh : Suci Septy Rahmadina

import javax.swing.*;
import java.awt.*;

public class HalamanRiwayat extends JPanel {
    private final JFrame parent;

    public HalamanRiwayat(JFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initComponents();
    }

    private void initComponents() {
        // Header
        add(createHeader(), BorderLayout.NORTH);
        
        // Content
        add(createContent(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Sistem Perpustakaan Digital Kampus");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        menuPanel.setBackground(new Color(70, 130, 180));
        
        String[] menus = {"Koleksi", "Notifikasi", "Riwayat", "Profile"};
        for (String menu : menus) {
            JButton menuBtn = new JButton(menu);
            menuBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
            menuBtn.setForeground(Color.WHITE);
            menuBtn.setBackground(new Color(70, 130, 180));
            menuBtn.setBorderPainted(false);
            menuBtn.setFocusPainted(false);
            
            if (menu.equals("Riwayat")) {
                menuBtn.setBackground(new Color(100, 149, 237));
            }
            
            menuBtn.addActionListener(e -> handleMenuClick(menu));
            menuPanel.add(menuBtn);
        }

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(menuPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createContent() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Page Title
        JLabel pageTitle = new JLabel("💷️ ✗️ Riwayat");
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        pageTitle.setForeground(new Color(40, 40, 60));
        pageTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Tab Panel
        JPanel tabPanel = new JPanel(new GridLayout(1, 2));
        tabPanel.setPreferredSize(new Dimension(0, 40));
        
        JButton sedangDipinjamBtn = new JButton("Sedang dipinjam");
        JButton selesaiBtn = new JButton("Selesai");
        
        styleTabButton(sedangDipinjamBtn, true);
        styleTabButton(selesaiBtn, false);
        
        sedangDipinjamBtn.addActionListener(e -> switchTab(true));
        selesaiBtn.addActionListener(e -> switchTab(false));
        
        tabPanel.add(sedangDipinjamBtn);
        tabPanel.add(selesaiBtn);

        // Books Panel
        JPanel booksPanel = createBooksPanel();

        contentPanel.add(pageTitle, BorderLayout.NORTH);
        contentPanel.add(tabPanel, BorderLayout.CENTER);
        contentPanel.add(booksPanel, BorderLayout.SOUTH);

        return contentPanel;
    }

    private JPanel createBooksPanel() {
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BoxLayout(booksPanel, BoxLayout.Y_AXIS));
        booksPanel.setBackground(Color.WHITE);

        // Book 1 - Sedang Dipinjam
        booksPanel.add(createBookCard(
            "Matematika Diskrit",
            "Rinaldi Munir",
            "8 Oktober 2025", 
            "25 Oktober 2025",
            "27 hari lagi",
            true
        ));

        booksPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Book 2 - Selesai
        booksPanel.add(createBookCard(
            "Software Engineering",
            "Ian Sommerville", 
            "25 September 2025",
            "9 Oktober 2025",
            "Dikembalikan: 8 Oktober 2025",
            false
        ));

        return booksPanel;
    }

    private JPanel createBookCard(String title, String author, String borrowedDate, 
                                 String dueDate, String status, boolean isBorrowed) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(800, 200));

        // Book Info
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(40, 40, 60));

        JLabel authorLabel = new JLabel("Penulis: " + author);
        authorLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        authorLabel.setForeground(Color.GRAY);

        JLabel borrowLabel = new JLabel("Dipinjam: " + borrowedDate);
        borrowLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        borrowLabel.setForeground(Color.GRAY);

        JLabel dueLabel = new JLabel("Jatuh Tempo: " + dueDate);
        dueLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dueLabel.setForeground(Color.GRAY);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        if (isBorrowed) {
            statusLabel.setForeground(new Color(220, 20, 60));
        } else {
            statusLabel.setForeground(new Color(34, 139, 34));
        }

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(authorLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        textPanel.add(borrowLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        textPanel.add(dueLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(statusLabel);

        infoPanel.add(textPanel, BorderLayout.CENTER);

        // Action Buttons
        if (isBorrowed) {
            JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
            buttonPanel.setBackground(Color.WHITE);
            
            String[] buttonLabels = {"Perpanjang", "Baca", "Kembalikan"};
            for (String label : buttonLabels) {
                JButton btn = new JButton(label);
                btn.setFont(new Font("SansSerif", Font.BOLD, 12));
                btn.setBackground(new Color(70, 130, 180));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                btn.addActionListener(e -> handleBookAction(label, title));
                buttonPanel.add(btn);
            }
            
            infoPanel.add(buttonPanel, BorderLayout.SOUTH);
        }

        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

    private void styleTabButton(JButton button, boolean isSelected) {
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setFocusPainted(false);
        
        if (isSelected) {
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(new Color(50, 110, 160), 2));
        } else {
            button.setBackground(new Color(240, 240, 240));
            button.setForeground(Color.DARK_GRAY);
            button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        }
    }

    private void switchTab(boolean showBorrowed) {
        JOptionPane.showMessageDialog(this, 
            showBorrowed ? "Menampilkan buku yang sedang dipinjam" : "Menampilkan riwayat selesai");
    }

    private void handleMenuClick(String menu) {
        switch (menu) {
            case "Koleksi" -> {
                parent.setContentPane(new HalamanKoleksi(parent));
                parent.revalidate();
            }
            case "Notifikasi" -> JOptionPane.showMessageDialog(this, "Membuka halaman notifikasi");
            case "Riwayat" -> {
                // Already on this page
            }
            case "Profile" -> JOptionPane.showMessageDialog(this, "Membuka halaman profile");
            default -> System.out.println("Menu tidak dikenali: " + menu);
        }
    }

    private void handleBookAction(String action, String bookTitle) {
        switch (action) {
            case "Perpanjang" -> JOptionPane.showMessageDialog(this, "Memperpanjang peminjaman: " + bookTitle);
            case "Baca" -> JOptionPane.showMessageDialog(this, "Membaca buku: " + bookTitle);
            case "Kembalikan" -> JOptionPane.showMessageDialog(this, "Mengembalikan buku: " + bookTitle);
            default -> System.out.println("Aksi tidak dikenali: " + action);
        }
    }
}