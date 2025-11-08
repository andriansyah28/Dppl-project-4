// Nama File : HalamanKoleksi.java
// Deskripsi : Halaman untuk menampilkan koleksi buku
// Dibuat oleh : Suci Septy Rahmadina

import javax.swing.*;
import java.awt.*;

public class HalamanKoleksi extends JPanel {
    private final JFrame parent;

    public HalamanKoleksi(JFrame parent) {
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

        JLabel titleLabel = new JLabel("Koleksi Buku");
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
            
            if (menu.equals("Koleksi")) {
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
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel infoLabel = new JLabel("Halaman Koleksi Buku - Fitur dalam pengembangan", SwingConstants.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        infoLabel.setForeground(new Color(40, 40, 60));

        contentPanel.add(infoLabel);

        return contentPanel;
    }

    private void handleMenuClick(String menu) {
        switch (menu) {
            case "Koleksi" -> {
                // Already on collection page
            }
            case "Notifikasi" -> JOptionPane.showMessageDialog(this, "Membuka halaman notifikasi");
            case "Riwayat" -> {
                parent.setContentPane(new HalamanRiwayat(parent));
                parent.revalidate();
            }
            case "Profile" -> JOptionPane.showMessageDialog(this, "Membuka halaman profile");
            default -> System.out.println("Menu tidak dikenali: " + menu);
        }
        parent.revalidate();
    }
}