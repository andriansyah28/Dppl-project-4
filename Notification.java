// File: Notification.java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Notification {
    private String message;
    private LocalDateTime timestamp;
    private boolean isUnread;
    private User user; // Untuk siapa notifikasi ini
    public Notification(String message, User user) {
        this.message = message;
        this.user = user;
        this.isUnread = true; // Selalu belum dibaca saat dibuat
        this.timestamp = LocalDateTime.now();
    }
    // --- Getters ---
    public String getMessage() { return message; }
    public boolean isUnread() { return isUnread; }
    public User getUser() { return user; }
    public String getTime() {
        // Ini adalah simulasi sederhana. Dalam aplikasi nyata, Anda akan menghitung selisih waktu.
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
    // --- Setter ---
    public void setUnread(boolean unread) {
        isUnread = unread;
    }
}