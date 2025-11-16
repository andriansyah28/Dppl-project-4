import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Loan {
    User user;
    String bookTitle;
    String imagePath;
    LocalDate borrowDate;
    LocalDate dueDate;
    LocalDate returnDate; // null jika belum dikembalikan
    // Formatter untuk tampilan
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public Loan(User user, String bookTitle, String imagePath, LocalDate borrowDate, LocalDate dueDate) {
        this.user = user;
        this.bookTitle = bookTitle;
        this.imagePath = imagePath;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }
    // --- Getters ---
    public User getUser() { return user; }
    public String getBookTitle() { return bookTitle; }
    public String getImagePath() { return imagePath; }
    public String getBorrowDateFormatted() { return borrowDate.format(dtf); }
    public String getDueDateFormatted() { return dueDate.format(dtf); }
    public String getReturnDateFormatted() {
        return (returnDate != null) ? returnDate.format(dtf) : "N/A";
    }
    // --- Setters ---
    public void setReturnDate(LocalDate date) {
        this.returnDate = date;
    }
}