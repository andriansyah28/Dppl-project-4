import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class RiwayatPanel extends JPanel {
    private MainAppFrame parentFrame;
    private JPanel sedangDipinjamContainer;
    private JPanel selesaiContainer;
    public RiwayatPanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(SidebarUtil.createStudentSidebar(parentFrame, MainAppFrame.RIWAYAT), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
        // Refresh daftar saat panel ini ditampilkan
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                loadData();
            }
        });
    }
    private JScrollPane createContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE,UITheme.PADDING_LARGE));
        content.setBackground(UITheme.BACKGROUND);
        JLabel header = new JLabel("Riwayat Peminjaman");
        header.setFont(UITheme.FONT_TITLE);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(20));
        // --- Sedang Dipinjam ---
        JLabel borrowing = new JLabel("Sedang dipinjam");
        borrowing.setFont(UITheme.FONT_HEADING_2);
        borrowing.setForeground(UITheme.TEXT_PRIMARY);
        borrowing.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(borrowing);
        content.add(Box.createVerticalStrut(10));
        sedangDipinjamContainer = new JPanel();
        sedangDipinjamContainer.setLayout(new BoxLayout(sedangDipinjamContainer, BoxLayout.Y_AXIS));
        sedangDipinjamContainer.setOpaque(false);
        sedangDipinjamContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(sedangDipinjamContainer);
        content.add(Box.createVerticalStrut(30));
        // --- Selesai ---
        JLabel finished = new JLabel("Selesai");
        finished.setFont(UITheme.FONT_HEADING_2);
        finished.setForeground(UITheme.TEXT_PRIMARY);
        finished.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(finished);
        content.add(Box.createVerticalStrut(10));
        selesaiContainer = new JPanel();
        selesaiContainer.setLayout(new BoxLayout(selesaiContainer, BoxLayout.Y_AXIS));
        selesaiContainer.setOpaque(false);
        selesaiContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(selesaiContainer);
        loadData(); // Muat data awal
        content.add(Box.createVerticalGlue());
        return new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    private void loadData() {
        sedangDipinjamContainer.removeAll();
        selesaiContainer.removeAll();
        ArrayList<Loan> activeLoans = LibraryModel.getActiveLoans();
        if (activeLoans.isEmpty()) {
            sedangDipinjamContainer.add(new JLabel("Tidak ada buku yang sedang dipinjam."));
        } else {
            for (Loan loan : activeLoans) {
                sedangDipinjamContainer.add(createOngoingLoanCard(loan));
                sedangDipinjamContainer.add(Box.createVerticalStrut(10));
            }
        }
        ArrayList<Loan> completedLoans = LibraryModel.getCompletedLoans();
        if (completedLoans.isEmpty()) {
            selesaiContainer.add(new JLabel("Belum ada riwayat pengembalian."));
        } else {
            for (Loan loan : completedLoans) {
                selesaiContainer.add(createFinishedLoanCard(loan));
                selesaiContainer.add(Box.createVerticalStrut(10));
            }
        }
        revalidate();
        repaint();
    }
    private JPanel createOngoingLoanCard(final Loan loan) {
        RoundedCardPanel card = new RoundedCardPanel();
        card.setLayout(new BorderLayout(15, 0));
        card.setBorderColor(UITheme.PRIMARY_LIGHT);
        card.setBackground(UITheme.SURFACE);
        card.setMaximumSize(new Dimension(9999, 150));
        JLabel cover;
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(loan.getImagePath()));
            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 130, Image.SCALE_SMOOTH);
            cover = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            cover = new JLabel("<html><b>COVER</b></html>", SwingConstants.CENTER);
        }
        cover.setPreferredSize(new Dimension(100, 150));
        cover.setBackground(Color.DARK_GRAY.brighter());
        cover.setOpaque(true);
        card.add(cover, BorderLayout.WEST);
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setOpaque(false);
        JTextArea detailText = new JTextArea(loan.getBookTitle() + "\n\n" +"Dipinjam: " + loan.getBorrowDateFormatted() + "\n" +"Jatuh Tempo: " + loan.getDueDateFormatted());
        detailText.setEditable(false);
        detailText.setOpaque(false);
        detailText.setFont(UITheme.FONT_BODY);
        detailText.setBorder(new EmptyBorder(UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM, 0, 0));
        detailPanel.add(detailText, BorderLayout.CENTER);
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(new EmptyBorder(0, 0, UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM));
        ModernButton perpanjang = new ModernButton("Perpanjang");
        perpanjang.setButtonType(ModernButton.ButtonType.SECONDARY);
        ModernButton kembalikan = new ModernButton("Kembalikan");
        kembalikan.setButtonType(ModernButton.ButtonType.SUCCESS);
        perpanjang.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Permintaan perpanjangan buku " + loan.getBookTitle() + " dikirim.", "Perpanjangan",
                JOptionPane.INFORMATION_MESSAGE));
        // PERBAIKAN: Memanggil FormPengembalian dan me-refresh
        kembalikan.addActionListener(e -> {
            parentFrame.showFormPengembalian(loan.getBookTitle(), loan.getImagePath(), loan);
            // Setelah pop-up ditutup, muat ulang data
            loadData();
        });
        actionPanel.add(perpanjang);
        actionPanel.add(kembalikan);
        detailPanel.add(actionPanel, BorderLayout.SOUTH);
        card.add(detailPanel, BorderLayout.CENTER);
        return card;
    }
    private JPanel createFinishedLoanCard(Loan loan) {
        RoundedCardPanel card = new RoundedCardPanel();
        card.setLayout(new BorderLayout(15, 0));
        card.setBorderColor(UITheme.BORDER);
        card.setBackground(UITheme.SURFACE);
        card.setMaximumSize(new Dimension(9999, 150));
        JLabel cover;
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(loan.getImagePath()));
            Image scaledImage = originalIcon.getImage().getScaledInstance(100, 130, Image.SCALE_SMOOTH);
            cover = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            cover = new JLabel("<html><b>COVER</b></html>", SwingConstants.CENTER);
        }
        cover.setPreferredSize(new Dimension(100, 150));
        cover.setBackground(Color.LIGHT_GRAY);
        cover.setOpaque(true);
        card.add(cover, BorderLayout.WEST);
        JTextArea detailText = new JTextArea(
                loan.getBookTitle() + "\n\n" +
                        "Dipinjam: " + loan.getBorrowDateFormatted() + "\n" +
                        "Dikembalikan: " + loan.getReturnDateFormatted());
        detailText.setEditable(false);
        detailText.setOpaque(false);
        detailText.setFont(UITheme.FONT_BODY);
        detailText.setBorder(new EmptyBorder(UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM, 0, 0));
        card.add(detailText, BorderLayout.CENTER);
        return card;
    }
}