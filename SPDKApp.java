// Nama File : SPDKApp.java
// Deskripsi : Class utama untuk menjalankan aplikasi SPDK
// Dibuat oleh : Andriansyah

import javax.swing.*;

public class SPDKApp extends JFrame {

    public SPDKApp() {
        setTitle("SPDK - Sistem Perpustakaan Digital Kampus");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setContentPane(new HalamanLogin(this));
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SPDKApp::new);
    }
}
