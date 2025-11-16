import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D; 
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 
import java.io.IOException;
import java.net.URL; 

public class ProfilePanel extends JPanel {
    private MainAppFrame parentFrame;
    private JLabel nameLabel;
    private JLabel nimLabel;
    private JLabel prodiLabel;
    private JLabel semesterLabel;
    private JLabel teleponLabel;
    private JLabel emailLabel;
    private JLabel angkatanLabel;
    private JLabel paLabel;
    private JLabel statusLabel;
    public ProfilePanel(MainAppFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        setBackground(UITheme.BACKGROUND);
        add(SidebarUtil.createStudentSidebar(parentFrame, MainAppFrame.PROFILE), BorderLayout.WEST);
        add(createContent(), BorderLayout.CENTER);
        // Muat data saat panel ditampilkan
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadUserData();
            }
        });
    }
    private void loadUserData() {
        User user = MainAppFrame.loggedInUser;
        if (user == null) {
            // Keamanan jika user logout
            nameLabel.setText("Nama Mahasiswa");
            nimLabel.setText("NIM");
            prodiLabel.setText("-");
            semesterLabel.setText("-");
            teleponLabel.setText("-");
            emailLabel.setText("-");
            angkatanLabel.setText("-");
            paLabel.setText("-");
            statusLabel.setText("-");
            return;
        }
        nameLabel.setText(user.getNama());
        nimLabel.setText(user.getId());
        prodiLabel.setText(user.getProdi());
        semesterLabel.setText(user.getSemester());
        teleponLabel.setText(user.getTelepon());
        emailLabel.setText(user.getEmail());
        angkatanLabel.setText(user.getAngkatan());
        paLabel.setText(user.getPembimbingAkademik());
        statusLabel.setText(user.getStatus());
        statusLabel.setForeground(user.getStatus().equals("Aktif") ? UITheme.SUCCESS : UITheme.DANGER);
    }
    private JScrollPane createContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        content.setBackground(UITheme.BACKGROUND);
        JLabel header = new JLabel("Profile Mahasiswa");
        header.setFont(UITheme.FONT_TITLE);
        header.setForeground(UITheme.TEXT_PRIMARY);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(header);
        content.add(Box.createVerticalStrut(20));
        // Profile Card
        RoundedCardPanel profileCard = new RoundedCardPanel();
        profileCard.setLayout(new BorderLayout());
        profileCard.setBackground(new Color(176, 196, 222)); 
        profileCard.setBorderColor(new Color(176, 196, 222));
        profileCard.setPreferredSize(new Dimension(900, 500)); 
        // Wrapper untuk menengahkan kartu
        JPanel cardWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardWrapper.setOpaque(false); 
        cardWrapper.add(profileCard);
        // Bagian Atas: Nama dan Avatar
        JPanel headerInfo = new JPanel();
        headerInfo.setLayout(new BoxLayout(headerInfo, BoxLayout.Y_AXIS));
        headerInfo.setOpaque(false);
        headerInfo.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, 0, UITheme.PADDING_LARGE, 0));
        // AvatarPlaceholder sekarang hanya menampilkan foto bulat
        AvatarPlaceholder avatar = new AvatarPlaceholder();
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT); 
        nameLabel = new JLabel("Nama Mahasiswa", SwingConstants.CENTER);
        nameLabel.setFont(UITheme.FONT_HEADING_1);
        nameLabel.setForeground(UITheme.TEXT_PRIMARY); 
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerInfo.add(avatar);
        headerInfo.add(Box.createVerticalStrut(UITheme.SPACING_MEDIUM));
        headerInfo.add(nameLabel);
        // Kotak detail putih di dalam kartu utama
        JPanel detailInfo = new JPanel(new GridBagLayout());
        detailInfo.setOpaque(true); 
        detailInfo.setBackground(UITheme.SURFACE); 
        detailInfo.setBorder(new EmptyBorder(UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE, UITheme.PADDING_LARGE));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, UITheme.PADDING_MEDIUM, UITheme.PADDING_LARGE);
        gbc.weightx = 1.0;
        // Inisialisasi semua label
        prodiLabel = createValueLabel("-");
        semesterLabel = createValueLabel("-");
        nimLabel = createValueLabel("-");
        teleponLabel = createValueLabel("-");
        emailLabel = createValueLabel("-");
        angkatanLabel = createValueLabel("-");
        paLabel = createValueLabel("-");
        statusLabel = createValueLabel("-");
        statusLabel.setForeground(UITheme.SUCCESS); 
        // Kolom 1
        gbc.gridx = 0; gbc.gridy = 0; detailInfo.add(createTitleLabel("Program Studi"), gbc);
        gbc.gridy = 1; detailInfo.add(prodiLabel, gbc);
        gbc.gridy = 2; detailInfo.add(createTitleLabel("NIM"), gbc);
        gbc.gridy = 3; detailInfo.add(nimLabel, gbc);
        gbc.gridy = 4; detailInfo.add(createTitleLabel("Email"), gbc);
        gbc.gridy = 5; detailInfo.add(emailLabel, gbc);
        gbc.gridy = 6; detailInfo.add(createTitleLabel("Pembimbing Akademik"), gbc);
        gbc.gridy = 7; detailInfo.add(paLabel, gbc);
        // Kolom 2
        gbc.gridx = 1; gbc.gridy = 0; detailInfo.add(createTitleLabel("Semester"), gbc);
        gbc.gridy = 1; detailInfo.add(semesterLabel, gbc);
        gbc.gridy = 2; detailInfo.add(createTitleLabel("Telepon"), gbc);
        gbc.gridy = 3; detailInfo.add(teleponLabel, gbc);
        gbc.gridy = 4; detailInfo.add(createTitleLabel("Angkatan"), gbc);
        gbc.gridy = 5; detailInfo.add(angkatanLabel, gbc);
        gbc.gridy = 6; detailInfo.add(createTitleLabel("Status"), gbc);
        gbc.gridy = 7; detailInfo.add(statusLabel, gbc);
        profileCard.add(headerInfo, BorderLayout.NORTH);
        profileCard.add(detailInfo, BorderLayout.CENTER);
        // Tombol Edit
        ModernButton editButton = new ModernButton("Edit Profile");
        editButton.setButtonType(ModernButton.ButtonType.PRIMARY);
        // Action Listener Tombol Edit
        editButton.addActionListener(e -> {
            boolean success = showEditProfileForm();
            if (success) {
                // Jika berhasil disimpan, muat ulang data di panel
                loadUserData();
                JOptionPane.showMessageDialog(this, "Profile berhasil diperbarui.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonWrapper.setOpaque(false);
        buttonWrapper.setBorder(new EmptyBorder(0, 0, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        buttonWrapper.add(editButton);
        profileCard.add(buttonWrapper, BorderLayout.SOUTH);
        content.add(cardWrapper); 
        content.add(Box.createVerticalGlue());
        // Muat data pengguna saat pertama kali panel dibuat
        loadUserData();
        return new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }
    // Form pop-up untuk Edit Profile (Semua field)
    private boolean showEditProfileForm() {
        User user = MainAppFrame.loggedInUser;
        if (user == null) return false;
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM, UITheme.PADDING_MEDIUM));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        // Buat field untuk SEMUA data
        JTextField nameField = new JTextField(user.getNama(), 25);
        JTextField nimField = new JTextField(user.getId(), 25);
        JTextField prodiField = new JTextField(user.getProdi(), 25);
        JTextField semesterField = new JTextField(user.getSemester(), 10);
        JTextField telpField = new JTextField(user.getTelepon(), 25);
        JTextField emailField = new JTextField(user.getEmail(), 25);
        JTextField angkatanField = new JTextField(user.getAngkatan(), 10);
        JTextField paField = new JTextField(user.getPembimbingAkademik(), 25);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Aktif", "Nonaktif"});
        statusCombo.setSelectedItem(user.getStatus());
        int y = 0;
        // Baris Nama
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1; formPanel.add(nameField, gbc);
        // Baris NIM
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("NIM:"), gbc);
        gbc.gridx = 1; formPanel.add(nimField, gbc);
        // Baris Prodi
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Prodi:"), gbc);
        gbc.gridx = 1; formPanel.add(prodiField, gbc);
        // Baris Semester
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Semester:"), gbc);
        gbc.gridx = 1; formPanel.add(semesterField, gbc);
        // Baris Telepon
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Telepon:"), gbc);
        gbc.gridx = 1; formPanel.add(telpField, gbc);
        // Baris Email
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; formPanel.add(emailField, gbc);
        // Baris Angkatan
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Angkatan:"), gbc);
        gbc.gridx = 1; formPanel.add(angkatanField, gbc);
        // Baris PA
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Pembimbing Akd:"), gbc);
        gbc.gridx = 1; formPanel.add(paField, gbc);
        // Baris Status
        gbc.gridx = 0; gbc.gridy = ++y; formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; formPanel.add(statusCombo, gbc);
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        int result = JOptionPane.showConfirmDialog(this, scrollPane, "Edit Profile", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Simpan data baru ke objek User
            user.setNama(nameField.getText());
            user.setId(nimField.getText());
            user.setProdi(prodiField.getText());
            user.setSemester(semesterField.getText());
            user.setTelepon(telpField.getText());
            user.setEmail(emailField.getText());
            user.setAngkatan(angkatanField.getText());
            user.setPembimbingAkademik(paField.getText());
            user.setStatus((String) statusCombo.getSelectedItem());
            return true;
        }
        return false;
    }
    // Helper untuk membuat label judul (abu-abu kecil)
    private JLabel createTitleLabel(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UITheme.FONT_SMALL);
        titleLabel.setForeground(UITheme.TEXT_SECONDARY);
        return titleLabel;
    }
    // Helper untuk membuat label data (hitam besar)
    private JLabel createValueLabel(String value) {
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(UITheme.FONT_HEADING_3);
        valueLabel.setForeground(UITheme.TEXT_PRIMARY);
        return valueLabel;
    }
    class AvatarPlaceholder extends JComponent {
        private BufferedImage scaledImage;
        private Icon fallbackIcon = UIManager.getIcon("OptionPane.informationIcon");
        private int AVATAR_SIZE = 100;
        public AvatarPlaceholder() {
            setPreferredSize(new Dimension(AVATAR_SIZE, AVATAR_SIZE));
            setOpaque(false); 
            try {
                // Muat gambar dari /assets/profile.png
                URL imageUrl = getClass().getResource("/assets/profile.png");
                if (imageUrl == null) {
                    throw new IOException("Resource not found: /assets/profile.png");
                }
                BufferedImage originalImage = ImageIO.read(imageUrl);
                scaledImage = new BufferedImage(AVATAR_SIZE, AVATAR_SIZE, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = scaledImage.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(originalImage, 0, 0, AVATAR_SIZE, AVATAR_SIZE, null);
                g2.dispose();
            } catch (IOException | IllegalArgumentException e) {
                 System.err.println("Gagal memuat /assets/profile.png. Menggunakan fallback icon. Error: " + e.getMessage());
                 scaledImage = null;
            }
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(AVATAR_SIZE, AVATAR_SIZE);
        }
        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }
        @Override
        public Dimension getMaximumSize() {
            return getPreferredSize();
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int size = Math.min(getWidth(), getHeight());
            Ellipse2D.Float clipShape = new Ellipse2D.Float(0, 0, size, size);
            if (scaledImage != null) {
                g2.setClip(clipShape);
                g2.drawImage(scaledImage, 0, 0, size, size, this);
            } else {
                g2.setColor(Color.LIGHT_GRAY); 
                g2.fill(clipShape); 
                int x = (size - fallbackIcon.getIconWidth()) / 2;
                int y = (size - fallbackIcon.getIconHeight()) / 2;
                fallbackIcon.paintIcon(this, g2, x, y);
            }
            g2.dispose();
        }
    }
}