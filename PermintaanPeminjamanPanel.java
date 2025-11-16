import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
public class PermintaanPeminjamanPanel extends JPanel {
    private MainAppFrame parentFrame;
    private JPanel requestContainer;
    public PermintaanPeminjamanPanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(SidebarUtil.createAdminSidebar(parentFrame, MainAppFrame.PERMINTAAN_PEMINJAMAN), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
        // Refresh daftar saat panel ini ditampilkan
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                loadRequests();
            }
        });
    }
    private JScrollPane createContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BACKGROUND);
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        JLabel header = new JLabel("Permintaan Peminjaman");
        header.setFont(UITheme.FONT_TITLE);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        JLabel subheader = new JLabel("Setujui atau tolak permintaan peminjaman dari mahasiswa.");
        subheader.setFont(UITheme.FONT_BODY);
        subheader.setForeground(UITheme.TEXT_SECONDARY);
        subheader.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(subheader);
        content.add(Box.createVerticalStrut(20));
        // Kontainer untuk kartu-kartu permintaan
        requestContainer = new JPanel();
        requestContainer.setLayout(new BoxLayout(requestContainer, BoxLayout.Y_AXIS));
        requestContainer.setOpaque(false);
        requestContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        loadRequests(); // Muat permintaan saat pertama kali dibuat
        content.add(requestContainer);
        content.add(Box.createVerticalGlue());
        return new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    private void loadRequests() {
        requestContainer.removeAll();
        ArrayList<LoanRequest> requests = LibraryModel.getLoanRequests();
        if (requests.isEmpty()) {
            JLabel emptyLabel = new JLabel("Tidak ada permintaan peminjaman baru.");
            emptyLabel.setFont(UITheme.FONT_HEADING_3);
            emptyLabel.setForeground(UITheme.TEXT_HINT);
            requestContainer.add(emptyLabel);
        } else {
            for (LoanRequest request : requests) {
                requestContainer.add(createRequestCard(request));
                requestContainer.add(Box.createVerticalStrut(15));
            }
        }
        requestContainer.revalidate();
        requestContainer.repaint();
    }
    private JPanel createRequestCard(final LoanRequest request) {
        RoundedCardPanel card = new RoundedCardPanel();
        card.setLayout(new BorderLayout(15, 0));
        card.setBorderColor(UITheme.BORDER);
        card.setBackground(UITheme.SURFACE);
        card.setMaximumSize(new Dimension(9999, 150));
        // Info Buku (Kiri)
        JPanel bookInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        bookInfo.setOpaque(false);
        JLabel cover;
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(request.getImagePath()));
            Image scaledImage = originalIcon.getImage().getScaledInstance(80, 110, Image.SCALE_SMOOTH);
            cover = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
             cover = new JLabel("<html><b>COVER</b></html>", SwingConstants.CENTER);
        }
        bookInfo.add(cover);
        JTextArea bookText = new JTextArea(
            request.getBookTitle() + "\n" +
            "ISBN: (Dummy ISBN)"
        );
        bookText.setEditable(false);
        bookText.setOpaque(false);
        bookText.setFont(UITheme.FONT_BODY);
        bookInfo.add(bookText);
        // Info User (Tengah)
        JTextArea userInfo = new JTextArea(
            "Peminjam: " + request.getUser().getNama() + "\n" +
            "NIM: " + request.getUser().getId() + "\n" +
            "Status: Mahasiswa Aktif"
        );
        userInfo.setEditable(false);
        userInfo.setOpaque(false);
        userInfo.setFont(UITheme.FONT_BODY);
        userInfo.setBorder(new EmptyBorder(10, 20, 10, 20));
        // Aksi (Kanan)
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        ModernButton tolakButton = new ModernButton("Tolak");
        tolakButton.setButtonType(ModernButton.ButtonType.DANGER);
        ModernButton terimaButton = new ModernButton("Terima");
        terimaButton.setButtonType(ModernButton.ButtonType.SUCCESS);
        // PERBAIKAN: Action Tolak
        tolakButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, 
                "Tolak permintaan peminjaman untuk " + request.getBookTitle() + "?",
                "Konfirmasi Tolak", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                LibraryModel.rejectLoan(request); // Panggil model
                loadRequests(); // Muat ulang daftar
            }
        });
        // PERBAIKAN: Action Terima
        terimaButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, 
                "Terima permintaan peminjaman untuk " + request.getBookTitle() + "?",
                "Konfirmasi Terima", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                LibraryModel.approveLoan(request); // Panggil model
                loadRequests(); // Muat ulang daftar
            }
        });
        actionPanel.add(tolakButton);
        actionPanel.add(terimaButton);
        card.add(bookInfo, BorderLayout.WEST);
        card.add(userInfo, BorderLayout.CENTER);
        card.add(actionPanel, BorderLayout.EAST);
        return card;
    }
}