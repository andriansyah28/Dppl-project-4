import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PengaturanPanel extends JPanel {
    private MainAppFrame parentFrame;
    public PengaturanPanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(SidebarUtil.createAdminSidebar(parentFrame, MainAppFrame.PENGATURAN), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
    }
    private JScrollPane createContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BACKGROUND);
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE,UITheme.PADDING_LARGE));
        JLabel header = new JLabel("Pengaturan Sistem");
        header.setFont(UITheme.FONT_TITLE);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(20));
        // --- Tabbed Pane untuk Kategori Pengaturan ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        tabbedPane.setMaximumSize(new Dimension(800, 500));
        tabbedPane.addTab("Aturan Peminjaman", createLoanRulesPanel());
        tabbedPane.addTab("Manajemen Denda", createFineManagementPanel());
        tabbedPane.addTab("Pengaturan Email/Notif", createNotificationSettingsPanel());
        content.add(tabbedPane);
        content.add(Box.createVerticalGlue());
        return new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    private JPanel createLoanRulesPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE,UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        // Deklarasi Field
        final JTextField durasi = new JTextField("7");
        final JTextField maksBuku = new JTextField("3");
        final JTextField maksPerpanjangan = new JTextField("1");
        final JCheckBox izinkanTerlambat = new JCheckBox("", false);
        panel.add(new JLabel("Durasi Peminjaman Default (hari):"));
        panel.add(durasi);
        panel.add(new JLabel("Maksimal Buku per Anggota:"));
        panel.add(maksBuku);
        panel.add(new JLabel("Maksimal Perpanjangan:"));
        panel.add(maksPerpanjangan);
        panel.add(new JLabel("Izinkan Perpanjangan Jika Terlambat:"));
        panel.add(izinkanTerlambat);
        ModernButton simpan = new ModernButton("Simpan Aturan");
        simpan.setButtonType(ModernButton.ButtonType.SUCCESS);
        simpan.setFont(UITheme.FONT_BUTTON);
        simpan.addActionListener(e -> JOptionPane.showMessageDialog(this,"Aturan Peminjaman berhasil disimpan:\nDurasi: " + durasi.getText() + " hari." +"\nMaks Buku: " + maksBuku.getText(),
                "Sukses", JOptionPane.INFORMATION_MESSAGE));
        JPanel wrapper = createSettingsWrapper(panel);
        wrapper.add(simpan, BorderLayout.SOUTH);
        return wrapper;
    }
    private JPanel createFineManagementPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE,UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        // Deklarasi Field
        final JTextField tarifDenda = new JTextField("1000");
        final JTextField maksDenda = new JTextField("50000");
        final JTextField batasWaktuBayar = new JTextField("30");
        panel.add(new JLabel("Tarif Denda Harian (Rp):"));
        panel.add(tarifDenda);
        panel.add(new JLabel("Maksimal Denda:"));
        panel.add(maksDenda);
        panel.add(new JLabel("Batas Waktu Pembayaran Denda (hari):"));
        panel.add(batasWaktuBayar);
        ModernButton simpan = new ModernButton("Simpan Denda");
        simpan.setButtonType(ModernButton.ButtonType.SUCCESS);
        simpan.setFont(UITheme.FONT_BUTTON);
        simpan.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Aturan Denda berhasil disimpan:\nTarif Harian: Rp" + tarifDenda.getText() +"\nMaks Denda: Rp" + maksDenda.getText(),
                "Sukses", JOptionPane.INFORMATION_MESSAGE));
        JPanel wrapper = createSettingsWrapper(panel);
        wrapper.add(simpan, BorderLayout.SOUTH);
        return wrapper;
    }
    private JPanel createNotificationSettingsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE,
                UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        // Deklarasi Field
        final JTextField notifHariSebelum = new JTextField("3");
        final JTextField emailPengirim = new JTextField("noreply@spdk.kampus.ac.id");
        final JCheckBox notifHarian = new JCheckBox("", true);
        panel.add(new JLabel("Kirim Notifikasi Jatuh Tempo (hari sebelum):"));
        panel.add(notifHariSebelum);
        panel.add(new JLabel("Kirim Notifikasi Keterlambatan Harian:"));
        panel.add(notifHarian);
        panel.add(new JLabel("Alamat Email Pengirim Default:"));
        panel.add(emailPengirim);
        ModernButton simpan = new ModernButton("Simpan Notifikasi");
        simpan.setButtonType(ModernButton.ButtonType.SUCCESS);
        simpan.setFont(UITheme.FONT_BUTTON);
        simpan.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Pengaturan Notifikasi berhasil disimpan." + "\nEmail Pengirim: " + emailPengirim.getText(),
            "Sukses", JOptionPane.INFORMATION_MESSAGE));
        JPanel wrapper = createSettingsWrapper(panel);
        wrapper.add(simpan, BorderLayout.SOUTH);
        return wrapper;
    }
    private JPanel createSettingsWrapper(JPanel contentPanel) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(contentPanel, BorderLayout.CENTER);
        return wrapper;
    }
}