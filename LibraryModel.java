import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.time.LocalDate; 
class User {
    private String id;
    private String password;
    private String nama;
    private String role; // "ADMIN" atau "MAHASISWA"
    // Data Profile Tambahan
    private String prodi;
    private String telepon;
    private String email;
    private String angkatan;
    private String pembimbingAkademik;
    private String semester;
    private String status; // Aktif / Nonaktif
    public User(String id, String password, String nama, String role, 
                String prodi, String telepon, String email, String angkatan, 
                String pembimbingAkademik, String semester, String status) {
        this.id = id;
        this.password = password;
        this.nama = nama;
        this.role = role;
        this.prodi = prodi;
        this.telepon = telepon;
        this.email = email;
        this.angkatan = angkatan;
        this.pembimbingAkademik = pembimbingAkademik;
        this.semester = semester;
        this.status = status;
    }
    // --- Getters ---
    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getNama() { return nama; }
    public String getRole() { return role; }
    public String getProdi() { return prodi; }
    public String getTelepon() { return telepon; }
    public String getEmail() { return email; }
    public String getAngkatan() { return angkatan; }
    public String getPembimbingAkademik() { return pembimbingAkademik; }
    public String getSemester() { return semester; }
    public String getStatus() { return status; }
    // --- Setters (Untuk Tombol Edit) ---
    public void setId(String id) { this.id = id; }
    public void setNama(String nama) { this.nama = nama; }
    public void setProdi(String prodi) { this.prodi = prodi; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
    public void setEmail(String email) { this.email = email; }
    public void setAngkatan(String angkatan) { this.angkatan = angkatan; }
    public void setPembimbingAkademik(String pa) { this.pembimbingAkademik = pa; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setStatus(String status) { this.status = status; }
}
public class LibraryModel {
    private Map<String, User> users;
    private static ArrayList<LoanRequest> loanRequests = new ArrayList<>();
    private static ArrayList<Loan> activeLoans = new ArrayList<>();
    private static ArrayList<Loan> completedLoans = new ArrayList<>();
    private static ArrayList<Notification> notifications = new ArrayList<>();
    public LibraryModel() {
        users = new HashMap<>();
        // PERBAIKAN: Data dummy diperbarui dengan info profile
        users.put("admin123", new User("admin123", "adminpass", "Pustakawan A", "ADMIN", 
            "Administrasi", "N/A", "admin@kampus.ac.id", "N/A", "N/A", "N/A", "Aktif"));
        users.put("2407123456", new User("2407123456", "mhs123", "Nama User", "MAHASISWA", 
            "Teknik Informatika", "08123456789", "user@kampus.ac.id", "2024", "Dr. Nama Dosen", "7", "Aktif"));
        // Inisialisasi notifikasi dummy
        if (notifications.isEmpty()) {
            User dummyUser = users.get("2407123456");
            // Cek jika dummyUser tidak null sebelum digunakan
            if (dummyUser != null) {
                notifications.add(new Notification("Selamat datang di SPDK, " + dummyUser.getNama() + "!", dummyUser));
                notifications.add(new Notification("Buku 'Fisika Dasar' akan jatuh tempo 3 hari lagi.", dummyUser));
            }
        }
    }
    public User authenticate(String id, String password) {
        if (users.containsKey(id)) {
            User user = users.get(id);
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    public Map<String, String> getAdminStats() {
        Map<String, String> stats = new HashMap<>();
        stats.put("Banyak Anggota", "35");
        stats.put("Banyak Buku", "150");
        stats.put("Pengunjung", "20");
        stats.put("Total Denda Masuk", "Rp. 1.000.000");
        return stats;
    }
    // --- METODE ALUR PEMINJAMAN ---
    public static void addLoanRequest(LoanRequest request) {
        loanRequests.add(request);
    }
    public static ArrayList<LoanRequest> getLoanRequests() {
        return loanRequests;
    }
    public static void approveLoan(LoanRequest request) {
        Loan newLoan = new Loan(
            request.getUser(), 
            request.getBookTitle(), 
            request.getImagePath(), 
            LocalDate.now(),
            LocalDate.now().plusDays(7)
        );
        activeLoans.add(newLoan);
        loanRequests.remove(request); 
        addNotification(new Notification(
            "Peminjaman buku '" + request.getBookTitle() + "' telah DISETUJUI.", 
            request.getUser()
        ));
    }
    public static void rejectLoan(LoanRequest request) {
        loanRequests.remove(request);
        addNotification(new Notification(
            "Peminjaman buku '" + request.getBookTitle() + "' DITOLAK.", 
            request.getUser()
        ));
    }
    public static void completeLoan(Loan loan) {
        loan.setReturnDate(LocalDate.now());
        activeLoans.remove(loan);
        completedLoans.add(loan);
        addNotification(new Notification(
            "Buku '" + loan.getBookTitle() + "' telah berhasil dikembalikan.", 
            loan.getUser()
        ));
    }
    public static ArrayList<Loan> getActiveLoans() { return activeLoans; }
    public static ArrayList<Loan> getCompletedLoans() { return completedLoans; }
    // --- METODE NOTIFIKASI ---
    public static void addNotification(Notification notification) {
        notifications.add(0, notification); // Tambahkan di paling atas
    }
    public static ArrayList<Notification> getNotificationsForUser(User user) {
        ArrayList<Notification> userNotifs = new ArrayList<>();
        if (user == null) {
            return userNotifs;
        }
        for (Notification n : notifications) {
            if (n.getUser() != null && n.getUser().getId().equals(user.getId())) {
                userNotifs.add(n);
            }
        }
        return userNotifs;
    }
    public static void clearAllNotifications(User user) {
        if (user == null) return;
        notifications.removeIf(n -> n.getUser() != null && n.getUser().getId().equals(user.getId()));
    }
}