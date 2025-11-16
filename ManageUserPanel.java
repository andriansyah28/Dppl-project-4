import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ManageUserPanel extends JPanel {
    private MainAppFrame parentFrame;
    private JPanel userGrid;
    private JTextField searchField;
    private ArrayList<JPanel> allUserCards = new ArrayList<>();

    public ManageUserPanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(SidebarUtil.createAdminSidebar(parentFrame, MainAppFrame.MANAGE_USER), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
    }

    private JPanel createSearchField(String title, int columns) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        JLabel label = new JLabel(title);
        label.setFont(UITheme.FONT_SMALL_BOLD);
        label.setForeground(UITheme.TEXT_SECONDARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchField = new JTextField(columns);
        searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(searchField);
        return panel;
    }

    private JScrollPane createContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE,
                UITheme.PADDING_LARGE));
        content.setBackground(UITheme.BACKGROUND);
        JLabel header = new JLabel("Manajemen User");
        header.setFont(UITheme.FONT_TITLE);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(16));
        // --- Search Panel ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        JPanel searchFieldPanel = createSearchField("Cari Nama atau NIM", 25);
        ModernButton searchButton = new ModernButton("Filter User");
        searchButton.setButtonType(ModernButton.ButtonType.PRIMARY);
        ModernButton resetButton = new ModernButton("Reset");
        resetButton.setButtonType(ModernButton.ButtonType.SECONDARY);
        searchPanel.add(searchFieldPanel);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);
        // --- Action Listeners untuk Pencarian ---
        searchButton.addActionListener(e -> filterUsers());
        resetButton.addActionListener(e -> resetFilter());
        searchField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    filterUsers();
                }
            }
        });
        content.add(searchPanel);
        content.add(Box.createVerticalStrut(20));
        // Daftar User dalam Grid
        userGrid = new JPanel(new GridLayout(0, 2, 15, 15));
        userGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        userGrid.setOpaque(false);
        allUserCards.clear();
        allUserCards.add(createUserCard("Nama User 1", "NIM: 2407123451", "Teknik Informatika", "Aktif"));
        allUserCards.add(createUserCard("Nama User 2", "NIM: 2407123452", "Sistem Informasi", "Aktif"));
        allUserCards.add(createUserCard("Nama User 3", "NIM: 2407123453", "Teknik Sipil", "Nonaktif"));
        allUserCards.add(createUserCard("Nama User 4", "NIM: 2407123454", "Manajemen", "Aktif"));
        allUserCards.add(createUserCard("Nama User 5", "NIM: 2407123455", "Teknik Informatika", "Aktif"));
        allUserCards.add(createUserCard("Nama User 6", "NIM: 2407123456", "Desain Grafis", "Aktif"));
        for (JPanel card : allUserCards) {
            userGrid.add(card);
        }
        content.add(userGrid);
        content.add(Box.createVerticalGlue());
        return new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void filterUsers() {
        String query = searchField.getText().toLowerCase().trim();
        userGrid.removeAll();
        for (JPanel card : allUserCards) {
            if (card.getName().toLowerCase().contains(query)) {
                userGrid.add(card);
            }
        }
        userGrid.revalidate();
        userGrid.repaint();
    }

    private void resetFilter() {
        searchField.setText("");
        userGrid.removeAll();
        for (JPanel card : allUserCards) {
            userGrid.add(card);
        }
        userGrid.revalidate();
        userGrid.repaint();
    }

    private Map<String, String> showEditUserForm(String currentName, String currentNim, String currentProdi) {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM,
                UITheme.PADDING_MEDIUM));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField nameField = new JTextField(currentName, 25);
        JTextField nimField = new JTextField(currentNim.replace("NIM: ", ""), 25);
        JTextField prodiField = new JTextField(currentProdi.replace("Prodi: ", ""), 25);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("NIM:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nimField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Prodi:"), gbc);
        gbc.gridx = 1;
        formPanel.add(prodiField, gbc);
        int result = JOptionPane.showConfirmDialog(this, formPanel, "Edit User: " + currentName,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Map<String, String> newData = new HashMap<>();
            newData.put("name", nameField.getText());
            newData.put("nim", "NIM: " + nimField.getText());
            newData.put("prodi", "Prodi: " + prodiField.getText());
            return newData;
        }
        return null;
    }

    private JPanel createUserCard(String name, String nim, String prodi, String status) {
        final RoundedCardPanel card = new RoundedCardPanel();
        card.setLayout(new BorderLayout());
        card.setBorderColor(UITheme.BORDER);
        card.setBorder(null);
        card.setBackground(UITheme.SURFACE);
        card.setPreferredSize(new Dimension(350, 150));
        card.setName(name + " " + nim);
        JPanel infoWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        infoWrapper.setOpaque(false);
        Icon userIcon = UIManager.getIcon("OptionPane.informationIcon");
        JLabel iconLabel = new JLabel(userIcon);
        iconLabel.setPreferredSize(new Dimension(40, 40));
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        final JLabel nameLabel = new JLabel("<html><b>" + name + "</b></html>");
        nameLabel.setFont(UITheme.FONT_BODY_BOLD);
        nameLabel.setForeground(UITheme.TEXT_PRIMARY);
        final JLabel nimLabel = new JLabel(nim);
        nimLabel.setFont(UITheme.FONT_SMALL);
        final JLabel prodiLabel = new JLabel("Prodi: " + prodi);
        prodiLabel.setFont(UITheme.FONT_SMALL);
        final JLabel statusLabel = new JLabel("Status: " + status);
        statusLabel.setFont(UITheme.FONT_SMALL_BOLD);
        statusLabel.setForeground(status.equals("Aktif") ? UITheme.SUCCESS : UITheme.DANGER);
        infoPanel.add(nameLabel);
        infoPanel.add(nimLabel);
        infoPanel.add(prodiLabel);
        infoPanel.add(statusLabel);
        infoWrapper.add(iconLabel);
        infoWrapper.add(infoPanel);
        // Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(new EmptyBorder(0, 0, 0, UITheme.PADDING_MEDIUM));
        ModernButton editButton = new ModernButton("Edit");
        editButton.setButtonType(ModernButton.ButtonType.INFO);
        editButton.setPreferredSize(new Dimension(80, 30));
        final ModernButton toggleStatusButton = new ModernButton("");
        toggleStatusButton.setPreferredSize(new Dimension(120, 30));
        // Atur tombol berdasarkan status awal
        if (status.equals("Aktif")) {
            toggleStatusButton.setText("Nonaktifkan");
            toggleStatusButton.setButtonType(ModernButton.ButtonType.DANGER);
        } else {
            toggleStatusButton.setText("Aktifkan");
            toggleStatusButton.setButtonType(ModernButton.ButtonType.SUCCESS);
        }
        // Action Edit User
        editButton.addActionListener(e -> {
            String currentName = nameLabel.getText().replaceAll("<html><b>|</b></html>", "");
            String currentNim = nimLabel.getText();
            String currentProdi = prodiLabel.getText();
            Map<String, String> newData = showEditUserForm(currentName, currentNim, currentProdi);
            if (newData != null) {
                // Update label di kartu
                nameLabel.setText("<html><b>" + newData.get("name") + "</b></html>");
                nimLabel.setText(newData.get("nim"));
                prodiLabel.setText(newData.get("prodi"));
                card.setName(newData.get("name") + " " + newData.get("nim"));
                JOptionPane.showMessageDialog(this, "Data user " + newData.get("name") + " berhasil diperbarui.",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // Action Toggle Status
        toggleStatusButton.addActionListener(e -> {
            String currentName = nameLabel.getText().replaceAll("<html><b>|</b></html>", "");
            String currentStatus = statusLabel.getText();
            if (currentStatus.contains("Aktif")) {
                // NONAKTIFKAN
                int result = JOptionPane.showConfirmDialog(this,
                        "Anda yakin ingin menonaktifkan akun " + currentName + "?", "Konfirmasi Nonaktifkan",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    statusLabel.setText("Status: Nonaktif");
                    statusLabel.setForeground(UITheme.DANGER);
                    toggleStatusButton.setText("Aktifkan");
                    toggleStatusButton.setButtonType(ModernButton.ButtonType.SUCCESS);
                }
            } else {
                // AKTIFKAN
                int result = JOptionPane.showConfirmDialog(this,
                        "Anda yakin ingin mengaktifkan kembali akun " + currentName + "?",
                        "Konfirmasi Aktifkan", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    statusLabel.setText("Status: Aktif");
                    statusLabel.setForeground(UITheme.SUCCESS);
                    toggleStatusButton.setText("Nonaktifkan");
                    toggleStatusButton.setButtonType(ModernButton.ButtonType.DANGER);
                }
            }
        });
        actionPanel.add(editButton);
        actionPanel.add(toggleStatusButton);
        card.add(infoWrapper, BorderLayout.CENTER);
        card.add(actionPanel, BorderLayout.SOUTH);
        return card;
    }
}