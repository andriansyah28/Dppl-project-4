// Nama File : HalamanUtama.java
// Deskripsi : Tampilan halaman utama setelah login berhasil
// Dibuat oleh : Andriansyah

import javax.swing.*;
import java.awt.*;

public class HalamanUtama extends JPanel {

    public HalamanUtama() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lblWelcome = new JLabel("Selamat Datang di Sistem SPDK", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(40, 40, 60));

        add(lblWelcome, BorderLayout.CENTER);
    }
}
