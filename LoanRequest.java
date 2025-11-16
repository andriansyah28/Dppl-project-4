public class LoanRequest {
    private User user;
    private String bookTitle;
    private String imagePath;
    public LoanRequest(User user, String bookTitle, String imagePath) {
        this.user = user;
        this.bookTitle = bookTitle;
        this.imagePath = imagePath;
    }
    public User getUser() {
        return user;
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public String getImagePath() {
        return imagePath;
    }
}