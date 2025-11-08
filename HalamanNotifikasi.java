// Nama File : HalamanNotifikasi.java
// Deskripsi : Halaman untuk menampilkan notifikasi sistem
// Dibuat oleh : Muhammad Radithia Fatir

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HalamanNotifikasi extends JPanel {
    private JPanel panelNotifikasi;
    private JButton btnSemua, btnBelumDibaca, btnSudahDibaca, btnHapusSemua;
    private final JFrame parent;

    public HalamanNotifikasi(JFrame parent) {
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
            
            if (menu.equals("Notifikasi")) {
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
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        
        JLabel pageTitle = new JLabel("Notifikasi");
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        pageTitle.setForeground(new Color(40, 40, 60));

        JLabel pageSubtitle = new JLabel("Berikut pemberitahuan terbaru dari sistem perpustakaan");
        pageSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pageSubtitle.setForeground(Color.GRAY);

        titlePanel.add(pageTitle, BorderLayout.NORTH);
        titlePanel.add(pageSubtitle, BorderLayout.SOUTH);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        btnSemua = createTabButton("Semua", true);
        btnBelumDibaca = createTabButton("Belum dibaca", false);
        btnSudahDibaca = createTabButton("Sudah dibaca", false);
        btnHapusSemua = createHapusButton("Hapus semua");

        filterPanel.add(btnSemua);
        filterPanel.add(btnBelumDibaca);
        filterPanel.add(btnSudahDibaca);
        filterPanel.add(Box.createHorizontalGlue());
        filterPanel.add(btnHapusSemua);

        // Notifications Panel
        panelNotifikasi = new JPanel();
        panelNotifikasi.setLayout(new BoxLayout(panelNotifikasi, BoxLayout.Y_AXIS));
        panelNotifikasi.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelNotifikasi);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(filterPanel, BorderLayout.CENTER);
        contentPanel.add(scrollPane, BorderLayout.SOUTH);

        loadNotifikasiSemua();
        setupEventListeners();

        return contentPanel;
    }

    private JButton createTabButton(String text, boolean active) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        
        if (active) {
            button.setForeground(new Color(70, 130, 180));
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 130, 180)),
                BorderFactory.createEmptyBorder(10, 20, 8, 20)
            ));
        } else {
            button.setForeground(Color.GRAY);
        }
        
        return button;
    }

    private JButton createHapusButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setForeground(Color.RED);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    private void setupEventListeners() {
        btnSemua.addActionListener(e -> {
            resetTabButtons();
            setActiveTab(btnSemua);
            loadNotifikasiSemua();
        });

        btnBelumDibaca.addActionListener(e -> {
            resetTabButtons();
            setActiveTab(btnBelumDibaca);
            loadNotifikasiBelumDibaca();
        });

        btnSudahDibaca.addActionListener(e -> {
            resetTabButtons();
            setActiveTab(btnSudahDibaca);
            loadNotifikasiSudahDibaca();
        });

        btnHapusSemua.addActionListener(e -> hapusSemuaNotifikasi());
    }

    private void resetTabButtons() {
        btnSemua.setForeground(Color.GRAY);
        btnSemua.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        btnBelumDibaca.setForeground(Color.GRAY);
        btnBelumDibaca.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        btnSudahDibaca.setForeground(Color.GRAY);
        btnSudahDibaca.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void setActiveTab(JButton button) {
        button.setForeground(new Color(70, 130, 180));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 130, 180)),
            BorderFactory.createEmptyBorder(10, 20, 8, 20)
        ));
    }

    private void loadNotifikasiSemua() {
        panelNotifikasi.removeAll();
        
        String[][] notifikasiData = {
            {"Buku yang dipinjam akan jatuh tempo dalam 2 hari lagi", "1 jam yang lalu"},
            {"Buku yang dipinjam akan jatuh tempo dalam 5 hari lagi", "4 jam yang lalu"},
            {"Buku yang dipinjam akan jatuh tempo dalam 1 hari lagi", "7 jam yang lalu"},
            {"Persetujuan peminjaman buku anda telah disetujui", "12 jam yang lalu"},
            {"Persetujuan peminjaman buku anda telah disetujui", "13 jam yang lalu"},
            {"Buku 'Komputer dan Masyarakat' sudah tersedia untuk dipinjam", "1 hari yang lalu"},
            {"Peminjaman buku 'Basis Data' berhasil diperpanjang", "1 hari yang lalu"},
            {"Terdapat buku baru di koleksi: 'Matematika Diskrit'", "2 hari yang lalu"},
            {"Pengembalian buku 'Komputer dan Masyarakat' berhasil dicatat", "2 hari yang lalu"},
            {"Notifikasi sistem: Maintenance jadwal minggu depan", "3 hari yang lalu"},
            {"Buku 'Sistem Operasi' melewati batas peminjaman", "3 hari yang lalu"},
            {"Peringatan: Akun Anda akan kedaluwarsa dalam 7 hari", "4 hari yang lalu"},
            {"Buku yang Anda reserve sudah siap untuk diambil", "4 hari yang lalu"},
            {"Promo: Pinjam 3 buku gratis biaya admin", "5 hari yang lalu"},
            {"Update sistem: Fitur baru telah ditambahkan", "5 hari yang lalu"},
            {"Survey kepuasan: Bantu kami meningkatkan layanan", "6 hari yang lalu"},
            {"Workshop pemrograman gratis minggu depan", "6 hari yang lalu"},
            {"Buku 'Aljabar linear dan matriks' telah dikembalikan", "1 minggu yang lalu"},
            {"Pemberitahuan: Libur nasional besok", "1 minggu yang lalu"},
            {"Selamat! Anda mencapai level anggota premium", "1 minggu yang lalu"}
        };

        for (String[] data : notifikasiData) {
            panelNotifikasi.add(createNotifikasiItem(data[0], data[1]));
            panelNotifikasi.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panelNotifikasi.revalidate();
        panelNotifikasi.repaint();
    }

    private void loadNotifikasiBelumDibaca() {
        panelNotifikasi.removeAll();
        
        String[][] notifikasiData = {
            {"Buku yang dipinjam akan jatuh tempo dalam 2 hari lagi", "1 jam yang lalu"},
            {"Persetujuan peminjaman buku anda telah disetujui", "12 jam yang lalu"},
            {"Buku 'Pemrograman Java' sudah tersedia untuk dipinjam", "1 hari yang lalu"},
            {"Terdapat buku baru di koleksi: 'Machine Learning Fundamentals'", "2 hari yang lalu"},
            {"Peringatan: Akun Anda akan kedaluwarsa dalam 7 hari", "4 hari yang lalu"},
            {"Promo: Pinjam 3 buku gratis biaya admin", "5 hari yang lalu"},
            {"Workshop pemrograman gratis minggu depan", "6 hari yang lalu"}
        };

        for (String[] data : notifikasiData) {
            panelNotifikasi.add(createNotifikasiItem(data[0], data[1]));
            panelNotifikasi.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panelNotifikasi.revalidate();
        panelNotifikasi.repaint();
    }

    private void loadNotifikasiSudahDibaca() {
        panelNotifikasi.removeAll();
        
        String[][] notifikasiData = {
            {"Buku yang dipinjam akan jatuh tempo dalam 5 hari lagi", "4 jam yang lalu"},
            {"Buku yang dipinjam akan jatuh tempo dalam 1 hari lagi", "7 jam yang lalu"},
            {"Persetujuan peminjaman buku anda telah disetujui", "13 jam yang lalu"},
            {"Peminjaman buku 'Struktur Data' berhasil diperpanjang", "1 hari yang lalu"},
            {"Pengembalian buku 'Algoritma & Pemrograman' berhasil dicatat", "2 hari yang lalu"},
            {"Notifikasi sistem: Maintenance jadwal minggu depan", "3 hari yang lalu"},
            {"Buku 'Database Systems' melewati batas peminjaman", "3 hari yang lalu"},
            {"Buku yang Anda reserve sudah siap untuk diambil", "4 hari yang lalu"},
            {"Update sistem: Fitur baru telah ditambahkan", "5 hari yang lalu"},
            {"Survey kepuasan: Bantu kami meningkatkan layanan", "6 hari yang lalu"},
            {"Buku 'Web Development' telah dikembalikan", "1 minggu yang lalu"},
            {"Pemberitahuan: Libur nasional besok", "1 minggu yang lalu"},
            {"Selamat! Anda mencapai level anggota premium", "1 minggu yang lalu"}
        };

        for (String[] data : notifikasiData) {
            panelNotifikasi.add(createNotifikasiItem(data[0], data[1]));
            panelNotifikasi.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panelNotifikasi.revalidate();
        panelNotifikasi.repaint();
    }

    private JPanel createNotifikasiItem(String pesan, String waktu) {
        JPanel panelItem = new JPanel(new BorderLayout());
        panelItem.setBackground(Color.WHITE);
        panelItem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        panelItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JLabel lblPesan = new JLabel(pesan);
        lblPesan.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lblWaktu = new JLabel(waktu);
        lblWaktu.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblWaktu.setForeground(Color.GRAY);

        panelItem.add(lblPesan, BorderLayout.CENTER);
        panelItem.add(lblWaktu, BorderLayout.EAST);

        return panelItem;
    }

    private void hapusSemuaNotifikasi() {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Apakah Anda yakin ingin menghapus semua notifikasi?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            panelNotifikasi.removeAll();
            panelNotifikasi.revalidate();
            panelNotifikasi.repaint();
            JOptionPane.showMessageDialog(this, "Semua notifikasi telah dihapus");
        }
    }

    private void handleMenuClick(String menu) {
        switch (menu) {
            case "Koleksi" -> {
                parent.setContentPane(new HalamanKoleksi(parent));
                parent.revalidate();
            }
            case "Notifikasi" -> {
                // Already on this page
            }
            case "Riwayat" -> {
                parent.setContentPane(new HalamanRiwayat(parent));
                parent.revalidate();
            }
            case "Profile" -> JOptionPane.showMessageDialog(this, "Membuka halaman profile");
            default -> System.out.println("Menu tidak dikenali: " + menu);
        }
    }
}