// Nama File : HalamanUtama.java
// Deskripsi : Tampilan halaman utama setelah login berhasil
// Dibuat oleh : Andriansyah

import javax.swing.*;
import java.awt.*;

public class HalamanUtama extends JFrame {

    public HalamanUtama() {
        setTitle("SPDK - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 

        // layout
        setLayout(new BorderLayout());

        // sidebar
        JPanel panelSamping = new JPanel();
        panelSamping.setPreferredSize(new Dimension(300, getHeight()));
        panelSamping.setBackground(new Color(150, 177, 194)); 
        panelSamping.setLayout(new GridBagLayout());

        GridBagConstraints sbc = new GridBagConstraints();
        sbc.gridx = 0;
        sbc.fill = GridBagConstraints.HORIZONTAL;
        sbc.gridy = 0;

        // Logo (placeholder)
        JLabel teksLogo = new JLabel("SPDK\nSistem Perpustakaan\nDigital Kampus", SwingConstants.CENTER);
        teksLogo.setFont(new Font("Arial", Font.BOLD, 18));
        teksLogo.setForeground(Color.WHITE);
        panelSamping.add(teksLogo, sbc);

        sbc.gridy++;

        // Tambah menu
        panelSamping.add(buatTombolMenu("Dashboard"), sbc); sbc.gridy++;
        panelSamping.add(buatTombolMenu("Koleksi"), sbc); sbc.gridy++;
        panelSamping.add(buatTombolMenu("Notifikasi"), sbc); sbc.gridy++;
        panelSamping.add(buatTombolMenu("Riwayat"), sbc); sbc.gridy++;
        panelSamping.add(buatTombolMenu("Profile"), sbc);

        add(panelSamping, BorderLayout.WEST);

        // panel konten
        JPanel panelKonten = new JPanel();
        panelKonten.setLayout(new BorderLayout());
        panelKonten.setBackground(Color.WHITE);

        // header
        JPanel panelHeader = new JPanel(new GridLayout(2, 1));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 0));
        panelHeader.setBackground(Color.WHITE);

        JLabel teksHalo = new JLabel("Halo, Andri!");
        teksHalo.setFont(new Font("Arial", Font.BOLD, 32));

        JLabel teksNIM = new JLabel("NIM 2407111615 - Teknik Informatika");
        teksNIM.setFont(new Font("Arial", Font.PLAIN, 20));

        panelHeader.add(teksHalo);
        panelHeader.add(teksNIM);

        panelKonten.add(panelHeader, BorderLayout.NORTH);

        // panel utama
        JPanel panelTengah = new JPanel();
        panelTengah.setBackground(Color.WHITE);
        panelTengah.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));

        JPanel panelKartuUtama = new JPanel();
        panelKartuUtama.setBackground(new Color(150, 177, 194));
        panelKartuUtama.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 30));
        panelKartuUtama.setPreferredSize(new Dimension(800, 250));
        panelKartuUtama.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tambah kartu-kartu informasi
        panelKartuUtama.add(buatKartu("Dipinjam", "1"));
        panelKartuUtama.add(buatKartu("Akan Jatuh Tempo", "1"));
        panelKartuUtama.add(buatKartu("Terlambat", "0"));

        panelTengah.add(panelKartuUtama);
        JPanel panelGrafik = new JPanel();
        panelGrafik.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panelGrafik.setBackground(Color.WHITE);

        panelGrafik.add(buatKotakGrafik("PENGUNJUNG PERPUSTAKAAN", "1 OKT - 31 OKT"));
        panelGrafik.add(buatKotakGrafik("BUKU YANG DIPINJAM", "1 OKT - 31 OKT"));

        panelTengah.add(panelGrafik);

        panelKonten.add(panelTengah, BorderLayout.CENTER);

        add(panelKonten, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel buatTombolMenu(String nama) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(250, 50));
        panel.setBackground(new Color(150, 177, 194));

        JLabel teks = new JLabel("  " + nama);
        teks.setFont(new Font("Arial", Font.BOLD, 18));
        teks.setForeground(Color.WHITE);

        JLabel icon = new JLabel(" ➜  ");
        icon.setFont(new Font("Arial", Font.BOLD, 20));
        icon.setForeground(Color.WHITE);
        icon.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(teks, BorderLayout.WEST);
        panel.add(icon, BorderLayout.EAST);

        return panel;
    }

    private JPanel buatKartu(String judul, String nilai) {
        JPanel kartu = new JPanel();
        kartu.setPreferredSize(new Dimension(200, 230));
        kartu.setBackground(Color.WHITE);
        kartu.setLayout(new BorderLayout());
        kartu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelJudul = new JLabel(judul, SwingConstants.CENTER);
        labelJudul.setFont(new Font("Arial", Font.BOLD, 20));
        labelJudul.setForeground(new Color(120, 150, 167));

        JLabel labelNilai = new JLabel(nilai, SwingConstants.CENTER);
        labelNilai.setFont(new Font("Arial", Font.BOLD, 36));
        labelNilai.setForeground(new Color(120, 150, 167));

        JLabel labelDetail = new JLabel("Lihat Detail", SwingConstants.CENTER);
        labelDetail.setFont(new Font("Arial", Font.PLAIN, 16));
        labelDetail.setForeground(new Color(120, 150, 167));

        kartu.add(labelJudul, BorderLayout.NORTH);
        kartu.add(labelNilai, BorderLayout.CENTER);
        kartu.add(labelDetail, BorderLayout.SOUTH);

        return kartu;
    }

    private JPanel buatKotakGrafik(String judul, String tanggal) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(350, 250));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JPanel bagianAtas = new JPanel(new BorderLayout());
        bagianAtas.setBackground(Color.WHITE);
        bagianAtas.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel labelJudul = new JLabel(judul);
        labelJudul.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel labelTanggal = new JLabel(tanggal);
        labelTanggal.setFont(new Font("Arial", Font.PLAIN, 12));

        bagianAtas.add(labelJudul, BorderLayout.WEST);
        bagianAtas.add(labelTanggal, BorderLayout.EAST);

        panel.add(bagianAtas, BorderLayout.NORTH);

        JPanel grafikPlaceholder = new JPanel();
        grafikPlaceholder.setBackground(new Color(210, 240, 255));
        panel.add(grafikPlaceholder, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HalamanUtama::new);
    }
}

