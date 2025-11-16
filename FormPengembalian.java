import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class FormPengembalian extends JDialog {
    // PERBAIKAN: Menyimpan referensi ke objek Loan
    private Loan loan;
    public FormPengembalian(MainAppFrame parentFrame, String bookTitle, String imagePath, Loan loan) {
        super(parentFrame, "Form Pengembalian: " + bookTitle, true);
        this.loan = loan; // Simpan objek loan
        setSize(700, 550);
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
        // --- Panel Kanan: Detail & Kondisi Buku ---
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setOpaque(false);
        JLabel header = new JLabel("Form Pengembalian");
        header.setFont(UITheme.FONT_HEADING_2);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(header);
        detailPanel.add(Box.createVerticalStrut(15));
        // Detail Informasi Buku (dari objek Loan)
        String infoText = "Judul: " + bookTitle + "\n" +
                          "Peminjam: " + loan.getUser().getNama() + "\n" +
                          "Tgl Peminjaman: " + loan.getBorrowDateFormatted() + "\n" +
                          "Tgl Jatuh Tempo: " + loan.getDueDateFormatted() + "\n" +
                          "Tgl Pengembalian: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) + "\n" +
                          "Status Denda: Rp 0 (Simulasi)\n";
        JTextArea infoArea = new JTextArea(infoText);
        infoArea.setEditable(false);
        infoArea.setOpaque(false);
        infoArea.setFont(UITheme.FONT_BODY);
        infoArea.setForeground(UITheme.TEXT_SECONDARY);
        infoArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.add(infoArea);
        detailPanel.add(Box.createVerticalStrut(15));
        // Opsi Kondisi Buku
        JLabel kondisiHeader = new JLabel("Kondisi Buku Saat Dikembalikan:");
        kondisiHeader.setFont(UITheme.FONT_BODY_BOLD);
        kondisiHeader.setForeground(UITheme.TEXT_PRIMARY);
        kondisiHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        JRadioButton baik = new JRadioButton("Baik");
        JRadioButton hilang = new JRadioButton("Hilang");
        JRadioButton rusakRingan = new JRadioButton("Rusak Ringan");
        JRadioButton rusakBerat = new JRadioButton("Rusak Berat");
        ButtonGroup group = new ButtonGroup();
        group.add(baik);
        group.add(hilang);
        group.add(rusakRingan);
        group.add(rusakBerat);
        baik.setSelected(true); // Default
        // apply fonts/colors for radio buttons
        baik.setFont(UITheme.FONT_BODY); baik.setOpaque(false);
        hilang.setFont(UITheme.FONT_BODY); hilang.setOpaque(false);
        rusakRingan.setFont(UITheme.FONT_BODY); rusakRingan.setOpaque(false);
        rusakBerat.setFont(UITheme.FONT_BODY); rusakBerat.setOpaque(false);
        JPanel kondisiPanel = new JPanel();
        kondisiPanel.setLayout(new BoxLayout(kondisiPanel, BoxLayout.Y_AXIS));
        kondisiPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        kondisiPanel.setOpaque(false);
        kondisiPanel.add(baik);
        kondisiPanel.add(hilang);
        kondisiPanel.add(rusakRingan);
        kondisiPanel.add(rusakBerat);
        detailPanel.add(kondisiHeader);
        detailPanel.add(kondisiPanel);
        mainPanel.add(detailPanel, BorderLayout.CENTER);
        // --- Tombol Aksi (Kembalikan Buku) ---
        ModernButton kembalikanButton = new ModernButton("Kembalikan Buku");
        kembalikanButton.setButtonType(ModernButton.ButtonType.SUCCESS);
        // PERBAIKAN: Action Listener diubah
        kembalikanButton.addActionListener(e -> {
            String selectedKondisi = baik.isSelected() ? "Baik" : hilang.isSelected() ? "Hilang" : rusakRingan.isSelected() ? "Rusak Ringan" : "Rusak Berat";
            JOptionPane.showMessageDialog(this, 
                                        "Buku '" + bookTitle + "' berhasil dikembalikan dengan status kondisi: " + selectedKondisi, 
                                        "Pengembalian Sukses", JOptionPane.INFORMATION_MESSAGE);
            // Pindahkan data di Model
            LibraryModel.completeLoan(this.loan);
            dispose(); // Tutup dialog
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(UITheme.BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        buttonPanel.add(kembalikanButton);
        add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
}