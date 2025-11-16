import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormPeminjaman extends JDialog {
    private MainAppFrame parentFrame;
    public FormPeminjaman(MainAppFrame parentFrame, String bookTitle, String imagePath) {
        super(parentFrame, "Form Peminjaman: " + bookTitle, true);
        this.parentFrame = parentFrame;
        setSize(700, 500);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        JPanel mainPanel = new JPanel(new BorderLayout(UITheme.SPACING_LARGE, UITheme.SPACING_LARGE));
        mainPanel.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        mainPanel.setBackground(UITheme.BACKGROUND);
        // --- Panel Kiri: Cover Buku ---
        JPanel coverPanel = new JPanel();
        coverPanel.setLayout(new BoxLayout(coverPanel, BoxLayout.Y_AXIS));
        coverPanel.setPreferredSize(new Dimension(250, 400));
        coverPanel.setBorder(BorderFactory.createLineBorder(UITheme.BORDER));
        coverPanel.setBackground(UITheme.SURFACE);
        JLabel cover;
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = originalIcon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            cover = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            cover = new JLabel("[ COVER ]", SwingConstants.CENTER);
        }
        cover.setAlignmentX(Component.CENTER_ALIGNMENT);
        coverPanel.add(Box.createVerticalGlue());
        coverPanel.add(cover);
        coverPanel.add(Box.createVerticalGlue());
        mainPanel.add(coverPanel, BorderLayout.WEST);
        // --- Panel Kanan: Detail & Syarat ---
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setOpaque(false);
        JLabel header = new JLabel("Form Peminjaman");
        header.setFont(UITheme.FONT_HEADING_2);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(header);
        detailPanel.add(Box.createVerticalStrut(15));
        // Detail Informasi Buku (Placeholder)
        String infoText = "Judul: " + bookTitle + "\n" +
                          "Pengarang: Rinaldi Munir\n" +
                          "ISBN: 978-602-1514-41-2\n\n" +
                          "Tanggal Peminjaman: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) + "\n" +
                          "Tanggal Jatuh Tempo: " + LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) + "\n";
        JTextArea infoArea = new JTextArea(infoText);
        infoArea.setEditable(false);
        infoArea.setOpaque(false);
        infoArea.setFont(UITheme.FONT_BODY);
        infoArea.setForeground(UITheme.TEXT_SECONDARY);
        infoArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(infoArea);
        detailPanel.add(Box.createVerticalStrut(15));
        // Syarat & Ketentuan
        JLabel syaratHeader = new JLabel("Syarat & Ketentuan");
        syaratHeader.setFont(UITheme.FONT_BODY_BOLD);
        syaratHeader.setForeground(UITheme.TEXT_PRIMARY);
        syaratHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextArea syaratArea = new JTextArea("1. Keterlambatan akan dikenakan denda Rp 1.000 per hari.\n2. Jika buku hilang/rusak, wajib mengganti sesuai kebijakan perpustakaan.");
        syaratArea.setEditable(false);
        syaratArea.setLineWrap(true);
        syaratArea.setWrapStyleWord(true);
        syaratArea.setOpaque(false);
        syaratArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        JCheckBox agreeCheck = new JCheckBox("Saya setuju dengan Syarat & Ketentuan di atas.");
        agreeCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        agreeCheck.setFont(UITheme.FONT_BODY);
        agreeCheck.setForeground(UITheme.TEXT_PRIMARY);
        agreeCheck.setOpaque(false);
        detailPanel.add(syaratHeader);
        detailPanel.add(syaratArea);
        detailPanel.add(Box.createVerticalStrut(10));
        detailPanel.add(agreeCheck);
        mainPanel.add(detailPanel, BorderLayout.CENTER);
        // --- Tombol Aksi (Setuju dan Pinjam) ---
        ModernButton pinjamButton = new ModernButton("Setuju dan Pinjam");
        pinjamButton.setButtonType(ModernButton.ButtonType.SUCCESS);
        // PERBAIKAN: Action Listener diubah
        pinjamButton.addActionListener(e -> {
            if (agreeCheck.isSelected()) {
                // Buat permintaan
                LoanRequest request = new LoanRequest(MainAppFrame.loggedInUser, bookTitle, imagePath);
                // Tambahkan ke antrian statis
                LibraryModel.addLoanRequest(request);
                JOptionPane.showMessageDialog(this, 
                                            "Permintaan pinjam buku '" + bookTitle + "' berhasil dikirim.\nSilakan tunggu persetujuan Admin.", 
                                            "Sukses", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Tutup dialog
            } else {
                JOptionPane.showMessageDialog(this, 
                                            "Anda harus menyetujui Syarat & Ketentuan.", 
                                            "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(UITheme.BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        buttonPanel.add(pinjamButton);
        add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
}