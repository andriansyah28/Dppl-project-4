// Nama File : HalamanLogin.java
// Deskripsi : Menampilkan halaman login user
// Dibuat oleh : Andriansyah

import javax.swing.*;
import java.awt.*;

public class HalamanLogin extends JPanel {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private final JCheckBox chkIngatSaya;
    

    public HalamanLogin(JFrame parent) {
        setLayout(new BorderLayout());

        JPanel panelKiri = new JPanel();
        panelKiri.setBackground(Color.WHITE);
        panelKiri.setLayout(new BorderLayout());

        JLabel lblJudul = new JLabel("Sistem Perpustakaan Digital Kampus", SwingConstants.CENTER);
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblJudul.setForeground(new Color(50, 50, 50));

        JLabel lblGambar = new JLabel("Perpustakaan.jpg", SwingConstants.CENTER);
        lblGambar.setFont(new Font("SansSerif", Font.ITALIC, 18));
        lblGambar.setForeground(Color.GRAY);

        panelKiri.add(lblJudul, BorderLayout.NORTH);
        panelKiri.add(lblGambar, BorderLayout.CENTER);
       
        JPanel panelKanan = new JPanel();
        panelKanan.setBackground(new Color(180, 205, 230)); 
        panelKanan.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblLogin = new JLabel("Login User", SwingConstants.CENTER);
        lblLogin.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblLogin.setForeground(new Color(40, 40, 60));

        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("SansSerif", Font.PLAIN, 16));

        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("SansSerif", Font.PLAIN, 16));

        chkIngatSaya = new JCheckBox("Ingat Saya");
        chkIngatSaya.setBackground(new Color(180, 205, 230));

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setBackground(new Color(100, 149, 237));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);

        JLabel lblLupa = new JLabel("Lupa Password?", SwingConstants.RIGHT);
        lblLupa.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblLupa.setForeground(new Color(70, 70, 100));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelKanan.add(lblLogin, gbc);

        gbc.gridy++;
        panelKanan.add(txtUsername, gbc);

        gbc.gridy++;
        panelKanan.add(txtPassword, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        panelKanan.add(chkIngatSaya, gbc);

        gbc.gridx = 1;
        panelKanan.add(lblLupa, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panelKanan.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());

            if (user.equals("2407111615") && pass.equals("123")) {
                JOptionPane.showMessageDialog(parent, "Login berhasil!");
                parent.setContentPane(new HalamanUtama());
                parent.revalidate();
            } else {
                JOptionPane.showMessageDialog(parent, "Username atau password salah!","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelKiri, panelKanan);
        splitPane.setDividerLocation(0.5);
        splitPane.setResizeWeight(0.5);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);
    }
}
