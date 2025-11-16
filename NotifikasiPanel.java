import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NotifikasiPanel extends JPanel {
    private MainAppFrame parentFrame;
    private JPanel listContainer; 
    
    // Variabel untuk filter
    private ArrayList<JPanel> allNotificationCards = new ArrayList<>();
    private ModernButton semuaButton, belumDibacaButton, sudahDibacaButton;

    public NotifikasiPanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);

        add(SidebarUtil.createStudentSidebar(parentFrame, MainAppFrame.NOTIFIKASI), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
        
        // Refresh daftar saat panel ini ditampilkan
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                loadNotifications();
            }
        });
    }
    
    private JScrollPane createContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BACKGROUND);
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));

        JLabel header = new JLabel("Notifikasi");
        header.setFont(UITheme.FONT_TITLE);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(10));
        
        JLabel subHeader = new JLabel("Berikut pemberitahuan terbaru dari sistem perpustakaan.");
        subHeader.setFont(UITheme.FONT_BODY);
        subHeader.setForeground(UITheme.TEXT_SECONDARY);
        subHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(subHeader);
        content.add(Box.createVerticalStrut(20));

        // --- Inisialisasi listContainer dipindahkan ke SINI ---
        listContainer = new JPanel(); 
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);
        listContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        // --- Akhir Pemindahan ---

        // --- Filter Notifikasi ---
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS)); 
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterPanel.setOpaque(false);
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        semuaButton = new ModernButton("Semua"); 
        belumDibacaButton = new ModernButton("Belum dibaca"); 
        sudahDibacaButton = new ModernButton("Sudah dibaca"); 
        ModernButton hapusSemua = new ModernButton("Hapus semua"); 
        hapusSemua.setButtonType(ModernButton.ButtonType.DANGER);
                
        filterPanel.add(semuaButton);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(belumDibacaButton);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(sudahDibacaButton);
        
        filterPanel.add(Box.createHorizontalGlue()); 
        
        filterPanel.add(hapusSemua);
        
        // --- Action Listeners untuk Filter ---
        semuaButton.addActionListener(e -> filterNotifications("Semua"));
        belumDibacaButton.addActionListener(e -> filterNotifications("Belum Dibaca"));
        sudahDibacaButton.addActionListener(e -> filterNotifications("Sudah Dibaca"));
        
        content.add(filterPanel);
        content.add(Box.createVerticalStrut(20)); 

        // --- Container untuk Daftar Notifikasi ---
        
        // Action Hapus Semua
        hapusSemua.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus semua notifikasi?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                // Panggil model untuk hapus data
                LibraryModel.clearAllNotifications(MainAppFrame.loggedInUser);
                // Muat ulang UI
                loadNotifications(); 
                JOptionPane.showMessageDialog(this, "Semua notifikasi berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Memuat notifikasi dari Model
        loadNotifications();
        
        content.add(listContainer);
        content.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null); 
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
    
    /**
     * Memuat notifikasi dari LibraryModel
     */
    private void loadNotifications() {
        listContainer.removeAll(); // Aman
        allNotificationCards.clear();
        
        ArrayList<Notification> notifications = LibraryModel.getNotificationsForUser(MainAppFrame.loggedInUser);
        
        if (notifications.isEmpty()) {
            // Jangan tambahkan label di sini, biarkan filterNotifications yang menangani
        } else {
            for (Notification notif : notifications) {
                JPanel card = createNotificationCard(notif);
                allNotificationCards.add(card); // Tambahkan ke daftar master
            }
        }
        
        // Tampilkan filter "Semua" di awal
        filterNotifications("Semua"); 
    }

     //Fungsi filter dinamis
    private void filterNotifications(String type) {
        if (semuaButton != null) { 
            semuaButton.setButtonType(type.equals("Semua") ? ModernButton.ButtonType.PRIMARY : ModernButton.ButtonType.SECONDARY);
            belumDibacaButton.setButtonType(type.equals("Belum Dibaca") ? ModernButton.ButtonType.PRIMARY : ModernButton.ButtonType.SECONDARY);
            sudahDibacaButton.setButtonType(type.equals("Sudah Dibaca") ? ModernButton.ButtonType.PRIMARY : ModernButton.ButtonType.SECONDARY);
        }
        
        listContainer.removeAll(); 
        
        int notifCount = 0;
        for (JPanel card : allNotificationCards) {
            Notification notif = (Notification) card.getClientProperty("notification");
            if (notif == null) continue;

            if (type.equals("Semua")) {
                listContainer.add(card);
                listContainer.add(Box.createVerticalStrut(8));
                notifCount++;
            } else if (type.equals("Belum Dibaca") && notif.isUnread()) {
                listContainer.add(card);
                listContainer.add(Box.createVerticalStrut(8));
                notifCount++;
            } else if (type.equals("Sudah Dibaca") && !notif.isUnread()) {
                listContainer.add(card);
                listContainer.add(Box.createVerticalStrut(8));
                notifCount++;
            }
        }
        
        if (notifCount == 0) {
            listContainer.add(new JLabel("Tidak ada notifikasi dalam kategori ini."));
        }

        listContainer.revalidate();
        listContainer.repaint();
    }

  
     //Membuat kartu berdasarkan objek Notification
    private JPanel createNotificationCard(final Notification notif) {
        final JPanel card = new JPanel(new BorderLayout(10, 10));
        
        // Simpan objek data di dalam komponen UI
        card.putClientProperty("notification", notif);
        
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(notif.isUnread() ? UITheme.PRIMARY : UITheme.BORDER, 1),
            new EmptyBorder(UITheme.PADDING_SMALL, UITheme.PADDING_SMALL, UITheme.PADDING_SMALL, UITheme.PADDING_SMALL)
        ));
        card.setMaximumSize(new Dimension(9999, 70)); 
        card.setBackground(notif.isUnread() ? UITheme.PRIMARY_LIGHTER : UITheme.SURFACE);
        
        JLabel icon = new JLabel("🔔"); 
        icon.setFont(UITheme.FONT_HEADING_1);
        icon.setBorder(new EmptyBorder(0, UITheme.PADDING_MEDIUM, 0, 0));
        card.add(icon, BorderLayout.WEST);
        
        JPanel detail = new JPanel(new BorderLayout());
        detail.setOpaque(false);
        
        final JLabel msgLabel = new JLabel(notif.getMessage());
        msgLabel.setFont(notif.isUnread() ? UITheme.FONT_BODY_BOLD : UITheme.FONT_BODY);
        msgLabel.setForeground(UITheme.TEXT_PRIMARY);
        detail.add(msgLabel, BorderLayout.CENTER);
        
        JLabel timeLabel = new JLabel(notif.getTime(), SwingConstants.RIGHT);
        timeLabel.setFont(UITheme.FONT_SMALL);
        timeLabel.setForeground(UITheme.TEXT_SECONDARY);
        timeLabel.setBorder(new EmptyBorder(0, 0, 0, UITheme.PADDING_MEDIUM));
        detail.add(timeLabel, BorderLayout.EAST);
        
        card.add(detail, BorderLayout.CENTER);
        
        // Tambahkan MouseListener untuk menandai "Sudah Dibaca"
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (notif.isUnread()) {
                    notif.setUnread(false); // Update model
                    
                    // Update tampilan kartu
                    card.setBackground(UITheme.SURFACE);
                    card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UITheme.BORDER, 1),
                        new EmptyBorder(UITheme.PADDING_SMALL, UITheme.PADDING_SMALL, UITheme.PADDING_SMALL, UITheme.PADDING_SMALL)
                    ));
                    msgLabel.setFont(UITheme.FONT_BODY);
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setCursor(Cursor.getDefaultCursor());
            }
        });
        
        return card;
    }
}