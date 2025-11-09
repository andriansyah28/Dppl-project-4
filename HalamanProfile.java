// Nama File : HalamanProfile.java
// Deskripsi : Halaman untuk menampilkan profile mahasiswa
// Dibuat oleh : Muhammad Radithia Fatir
// Di push oleh: Muhammad Radithia Fatir

import javax.swing.*;
import java.awt.*;

public class HalamanProfile extends JPanel {
    private final JFrame parent;

    public HalamanProfile(JFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
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
            
            if (menu.equals("Profile")) {
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
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Title
        JLabel profileTitle = new JLabel("Profile Mahasiswa");
        profileTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        profileTitle.setForeground(new Color(40, 40, 60));
        profileTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Main profile content
        JPanel profileContent = new JPanel(new BorderLayout());
        profileContent.setBackground(Color.WHITE);

        // Left side - Profile info
        profileContent.add(createProfileInfo(), BorderLayout.WEST);
        
        // Right side - Additional info
        profileContent.add(createAdditionalInfo(), BorderLayout.CENTER);

        contentPanel.add(profileTitle, BorderLayout.NORTH);
        contentPanel.add(profileContent, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createProfileInfo() {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setPreferredSize(new Dimension(400, 0));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));

        // Nama Mahasiswa
        JLabel namaLabel = new JLabel("Nama Mahasiswa");
        namaLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        namaLabel.setForeground(new Color(100, 100, 120));

        JLabel namaValue = new JLabel("Radithia Andriansyah");
        namaValue.setFont(new Font("SansSerif", Font.BOLD, 20));
        namaValue.setForeground(new Color(40, 40, 60));

        // NIM
        JLabel nimLabel = new JLabel("NIM");
        nimLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nimLabel.setForeground(new Color(100, 100, 120));

        JLabel nimValue = new JLabel("240711253");
        nimValue.setFont(new Font("SansSerif", Font.BOLD, 18));
        nimValue.setForeground(new Color(70, 130, 180));

        // Separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(200, 200, 200));
        separator.setMaximumSize(new Dimension(350, 1));

        // Status
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statusPanel.setBackground(Color.WHITE);
        
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setForeground(new Color(100, 100, 120));
        statusLabel.setPreferredSize(new Dimension(150, 25));
        
        JLabel statusValue = new JLabel("Aktif");
        statusValue.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusValue.setForeground(Color.GREEN);
        statusValue.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GREEN),
            BorderFactory.createEmptyBorder(2, 10, 2, 10)
        ));
        
        statusPanel.add(statusLabel);
        statusPanel.add(statusValue);

        profilePanel.add(namaLabel);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        profilePanel.add(namaValue);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        profilePanel.add(nimLabel);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        profilePanel.add(nimValue);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        profilePanel.add(separator);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add all info rows
        profilePanel.add(createInfoRow("Program Studi", "S1 - TEKNIK INFORMATIKA"));
        profilePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        profilePanel.add(createInfoRow("Pembimbing Akademik", "Nama Dosen"));
        profilePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        profilePanel.add(createInfoRow("Email", "radithia@satu.umf.ac.id"));
        profilePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        profilePanel.add(statusPanel);

        return profilePanel;
    }

    private JPanel createAdditionalInfo() {
        JPanel additionalPanel = new JPanel();
        additionalPanel.setLayout(new BoxLayout(additionalPanel, BoxLayout.Y_AXIS));
        additionalPanel.setBackground(Color.WHITE);

        // Create two columns for additional info
        JPanel columnsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        columnsPanel.setBackground(Color.WHITE);
        columnsPanel.setMaximumSize(new Dimension(400, 200));

        // Angkatan
        columnsPanel.add(createInfoCard("Angkatan", "2024"));
        
        // Telepon
        columnsPanel.add(createInfoCard("Telepon", "62812345678"));
        
        // Semester
        columnsPanel.add(createInfoCard("Semester", "3"));
        
        // Peminjaman Aktif
        columnsPanel.add(createInfoCard("Peminjaman Aktif", "1"));

        additionalPanel.add(columnsPanel);
        additionalPanel.add(Box.createVerticalGlue());

        return additionalPanel;
    }

    private JPanel createInfoRow(String label, String value) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rowPanel.setBackground(Color.WHITE);
        
        JLabel infoLabel = new JLabel(label);
        infoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        infoLabel.setForeground(new Color(100, 100, 120));
        infoLabel.setPreferredSize(new Dimension(150, 20));
        
        JLabel infoValue = new JLabel(value);
        infoValue.setFont(new Font("SansSerif", Font.PLAIN, 14));
        infoValue.setForeground(new Color(40, 40, 60));
        
        rowPanel.add(infoLabel);
        rowPanel.add(infoValue);
        
        return rowPanel;
    }

    private JPanel createInfoCard(String title, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(245, 245, 255));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 255)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(150, 80));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        titleLabel.setForeground(new Color(100, 100, 120));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        valueLabel.setForeground(new Color(70, 130, 180));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(valueLabel);

        return card;
    }

    private void handleMenuClick(String menu) {
        switch (menu) {
            case "Koleksi" -> {
                parent.setContentPane(new HalamanKoleksi(parent));
                parent.revalidate();
            }
            case "Notifikasi" -> {
                parent.setContentPane(new HalamanNotifikasi(parent));
                parent.revalidate();
            }
            case "Riwayat" -> {
                parent.setContentPane(new HalamanRiwayat(parent));
                parent.revalidate();
            }
            case "Profile" -> {
                // Already on profile page
            }
            default -> System.out.println("Menu tidak dikenali: " + menu);
        }
        parent.revalidate();
    }
}