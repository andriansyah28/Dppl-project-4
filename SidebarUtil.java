import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidebarUtil {

    private static final int SIDEBAR_WIDTH = 280; 

    public static JPanel createAdminSidebar(MainAppFrame parentFrame, String activeView) {
        return createSidebar(parentFrame, activeView, true);
    }

    public static JPanel createStudentSidebar(MainAppFrame parentFrame, String activeView) {
        return createSidebar(parentFrame, activeView, false);
    }

    private static JPanel createSidebar(MainAppFrame parentFrame, String activeView, boolean isAdmin) {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0)); 
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(0, 0, 0, 0));
        sidebar.setBackground(UITheme.SIDEBAR_BACKGROUND); 

        // Logo Section
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_SMALL, UITheme.PADDING_LARGE, UITheme.PADDING_SMALL)); 
        logoPanel.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 120)); 
        
        try {
            ImageIcon originalIcon = new ImageIcon(SidebarUtil.class.getResource("/assets/spdk_logo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
            logoPanel.add(logoLabel);
        } catch (Exception e) {
            JLabel logoText = new JLabel("SPDK");
            logoText.setFont(UITheme.FONT_HEADING_1);
            logoText.setForeground(UITheme.TEXT_LIGHT);
            logoText.setAlignmentX(Component.LEFT_ALIGNMENT);
            logoPanel.add(logoText);
        }
        
        JLabel brandLabel = new JLabel(isAdmin ? "Admin Portal" : "Digital Kampus");
        brandLabel.setFont(UITheme.FONT_SMALL_BOLD);
        brandLabel.setForeground(UITheme.PRIMARY_LIGHTER);
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        logoPanel.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
        logoPanel.add(brandLabel);
        sidebar.add(logoPanel);

        // Menu Items
        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, 0, UITheme.PADDING_MEDIUM, 0)); 
        
        if (isAdmin) {
            menuPanel.add(createSidebarButton(parentFrame, "Dashboard", MainAppFrame.ADMIN_DASHBOARD, activeView));
            menuPanel.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
            menuPanel.add(createSidebarButton(parentFrame, "Permintaan", MainAppFrame.PERMINTAAN_PEMINJAMAN, activeView));
            menuPanel.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
            menuPanel.add(createSidebarButton(parentFrame, "Koleksi", MainAppFrame.KELOLA_KOLEKSI, activeView));
            menuPanel.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
            menuPanel.add(createSidebarButton(parentFrame, "Pengguna", MainAppFrame.MANAGE_USER, activeView));
            menuPanel.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
            menuPanel.add(createSidebarButton(parentFrame, "Laporan", MainAppFrame.LAPORAN, activeView));
            menuPanel.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
            menuPanel.add(createSidebarButton(parentFrame, "Pengaturan", MainAppFrame.PENGATURAN, activeView));
        } else {
            menuPanel.add(createSidebarButton(parentFrame, "Dashboard", MainAppFrame.MAHASISWA_DASHBOARD, activeView));
            menuPanel.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
            menuPanel.add(createSidebarButton(parentFrame, "Koleksi", MainAppFrame.MHS_KOLEKSI, activeView));
            menuPanel.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
            menuPanel.add(createSidebarButton(parentFrame, "Notifikasi", MainAppFrame.NOTIFIKASI, activeView));
            menuPanel.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
            menuPanel.add(createSidebarButton(parentFrame, "Riwayat", MainAppFrame.RIWAYAT, activeView));
            menuPanel.add(Box.createVerticalStrut(UITheme.SPACING_SMALL));
            menuPanel.add(createSidebarButton(parentFrame, "Profile", MainAppFrame.PROFILE, activeView));
        }

        sidebar.add(menuPanel);
        
        // Logout Button at Bottom
        sidebar.add(Box.createVerticalGlue());
        JButton logout = createSidebarButton(parentFrame, "Logout", MainAppFrame.LOGIN_VIEW, activeView);
        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        logoutPanel.setLayout(new BoxLayout(logoutPanel, BoxLayout.X_AXIS));
        
        logoutPanel.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, 40, UITheme.PADDING_MEDIUM, 0));
        logoutPanel.add(logout);
        sidebar.add(logoutPanel);
        
        return sidebar;
    }

    private static JButton createSidebarButton(MainAppFrame parentFrame, String text, String viewName, String activeView) {
        boolean isActive = viewName.equals(activeView);
        JButton button = new JButton(text);
        
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(SIDEBAR_WIDTH, 45)); 
        button.setMinimumSize(new Dimension(SIDEBAR_WIDTH, 45));
        button.setFont(UITheme.FONT_BODY_BOLD);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false); 
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM, UITheme.PADDING_SMALL, UITheme.PADDING_MEDIUM));
        button.setOpaque(true);

        // Set initial colors
        if (isActive) {
            button.setBackground(UITheme.PRIMARY_LIGHT);
            button.setForeground(UITheme.PRIMARY_DARK);
        } else {
            button.setBackground(UITheme.SIDEBAR_BACKGROUND);
            button.setForeground(UITheme.TEXT_LIGHT);
        }

        // Action Listener
        button.addActionListener(e -> parentFrame.showView(viewName));
        
        // Hover Effect dengan UI update thread-safe
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isActive) {
                    SwingUtilities.invokeLater(() -> {
                        button.setBackground(UITheme.withAlpha(UITheme.PRIMARY_LIGHT, 100)); 
                        button.setForeground(Color.WHITE);
                    });
                }
            }

            @Override
            public void mouseExited(MouseEvent e) { 
                if (!isActive) {
                    SwingUtilities.invokeLater(() -> {
                        button.setBackground(UITheme.SIDEBAR_BACKGROUND);
                        button.setForeground(UITheme.TEXT_LIGHT);
                    });
                }
            }
        });

        return button;
    }
}