import javax.swing.*;
import java.awt.*;
public class MainAppFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private LibraryModel model = new LibraryModel();
    public static User loggedInUser;
    // Konstanta Navigasi
    public static final String LOGIN_VIEW = "LoginView";
    public static final String ADMIN_DASHBOARD = "AdminDashboard";
    public static final String MAHASISWA_DASHBOARD = "MahasiswaDashboard";
    public static final String KELOLA_KOLEKSI = "KelolaKoleksi";
    public static final String MHS_KOLEKSI = "MhsKoleksi";
    public static final String MANAGE_USER = "ManageUser";
    public static final String RIWAYAT = "Riwayat";
    public static final String PROFILE = "Profile";
    public static final String LAPORAN = "Laporan";
    public static final String PENGATURAN = "Pengaturan";
    public static final String NOTIFIKASI = "Notifikasi";
    public static final String FORM_PEMINJAMAN = "FormPeminjaman"; 
    public static final String FORM_PENGEMBALIAN = "FormPengembalian";
    public static final String PERMINTAAN_PEMINJAMAN = "PermintaanPeminjaman"; 
    private AdminDashboardPanel adminDashboard;
    private MahasiswaDashboardPanel mhsDashboard;
    public MainAppFrame() {
        setTitle("Sistem Perpustakaan Digital Kampus (Swing)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700); 
        setLocationRelativeTo(null); 
        // Inisialisasi Views
        LoginPanel loginPanel = new LoginPanel(this);
        adminDashboard = new AdminDashboardPanel(this);
        mhsDashboard = new MahasiswaDashboardPanel(this);
        // Views Administrasi
        KelolaKoleksiPanel kelolaKoleksi = new KelolaKoleksiPanel(this);
        ManageUserPanel manageUser = new ManageUserPanel(this);
        LaporanPanel laporan = new LaporanPanel(this);
        PengaturanPanel pengaturan = new PengaturanPanel(this); 
        PermintaanPeminjamanPanel permintaanPeminjaman = new PermintaanPeminjamanPanel(this); 
        // Views Mahasiswa
        KoleksiMahasiswaPanel mhsKoleksi = new KoleksiMahasiswaPanel(this);
        RiwayatPanel riwayat = new RiwayatPanel(this);
        NotifikasiPanel notifikasi = new NotifikasiPanel(this);
        ProfilePanel profile = new ProfilePanel(this);
        // Tambahkan Views sebagai "Kartu"
        mainPanel.add(loginPanel, LOGIN_VIEW);
        mainPanel.add(adminDashboard, ADMIN_DASHBOARD);
        mainPanel.add(mhsDashboard, MAHASISWA_DASHBOARD);
        mainPanel.add(kelolaKoleksi, KELOLA_KOLEKSI);
        mainPanel.add(manageUser, MANAGE_USER);
        mainPanel.add(laporan, LAPORAN);
        mainPanel.add(pengaturan, PENGATURAN);
        mainPanel.add(permintaanPeminjaman, PERMINTAAN_PEMINJAMAN); 
        mainPanel.add(mhsKoleksi, MHS_KOLEKSI);
        mainPanel.add(riwayat, RIWAYAT);
        mainPanel.add(notifikasi, NOTIFIKASI);
        mainPanel.add(profile, PROFILE);
        add(mainPanel);
        cardLayout.show(mainPanel, LOGIN_VIEW); 
    }
    public void showView(String viewName) {
        cardLayout.show(mainPanel, viewName);
    }
    public void attemptLogin(String id, String password) {
        User user = model.authenticate(id, password);
        if (user != null) {
            loggedInUser = user; 
            if ("ADMIN".equals(user.getRole())) {
                adminDashboard.setUser(user); 
                showView(MainAppFrame.ADMIN_DASHBOARD);
            } else if ("MAHASISWA".equals(user.getRole())) {
                mhsDashboard.setUser(user); 
                showView(MainAppFrame.MAHASISWA_DASHBOARD);
            }
        } else {
            JOptionPane.showMessageDialog(this, "NIM/Password salah.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Helper method untuk menampilkan form peminjaman
    public void showFormPeminjaman(String bookTitle, String imagePath) {
        new FormPeminjaman(this, bookTitle, imagePath).setVisible(true);
    }
    // Helper method untuk menampilkan form pengembalian
    public void showFormPengembalian(String bookTitle, String imagePath, Loan loan) {
        // Mengirim data 'loan' ke form pengembalian
        new FormPengembalian(this, bookTitle, imagePath, loan).setVisible(true);
    }
    public static void main(String[] args) {
        try {
            if (MainAppFrame.class.getResource("/assets/spdk_logo.png") == null) {
                System.err.println("PERINGATAN: Folder 'assets' tidak ditemukan. Pastikan semua gambar ada.");
            }
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> {
            new MainAppFrame().setVisible(true);
        });
    }
}