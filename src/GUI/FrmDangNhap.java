package GUI;

import BUS.TaiKhoanBUS;
import DTO.TaiKhoanDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;

public class FrmDangNhap extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private TaiKhoanBUS tkBUS = new TaiKhoanBUS();

    // Màu sắc
    private Color colorRed = new Color(200, 0, 0);
    private Color colorBeige = new Color(210, 190, 160); 

    public FrmDangNhap() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Hệ thống Quản lý Thư viện - DNC");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainContainer = new JPanel(new GridLayout(1, 2));
        mainContainer.setBackground(colorBeige);
        mainContainer.setBorder(new EmptyBorder(30, 30, 30, 0)); 

        // ==========================================
        // PHẦN BÊN TRÁI: PANEL TRANG BÌA (Bo tròn)
        // ==========================================
        JPanel pnlLeft = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2d.dispose();
            }
        };
        pnlLeft.setOpaque(false);
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
        pnlLeft.setBorder(new EmptyBorder(40, 30, 40, 30));

        JLabel lblLogo = new JLabel();
        try {
            URL imgURL = getClass().getResource("/icons/dnc.png"); 
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {}
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle1 = new JLabel("QUẢN LÝ THƯ VIỆN");
        lblTitle1.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitle1.setForeground(colorRed);
        lblTitle1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle2 = new JLabel("TRƯỜNG ĐẠI HỌC NAM CẦN THƠ");
        lblTitle2.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitle2.setForeground(colorRed);
        lblTitle2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAuthor1 = new JLabel("NGUYỄN ĐĂNG KHOA");
        lblAuthor1.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAuthor1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAuthor2 = new JLabel("LÊ MINH HUY");
        lblAuthor2.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAuthor2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel yearPanel = new JPanel(new FlowLayout());
        yearPanel.setOpaque(false);
        JSeparator sep1 = new JSeparator(); sep1.setPreferredSize(new Dimension(70, 1));
        JSeparator sep2 = new JSeparator(); sep2.setPreferredSize(new Dimension(70, 1));
        JLabel lblYear = new JLabel("  2026  ");
        lblYear.setFont(new Font("Arial", Font.BOLD, 16));
        yearPanel.add(sep1); yearPanel.add(lblYear); yearPanel.add(sep2);

        pnlLeft.add(lblLogo);
        pnlLeft.add(Box.createVerticalStrut(30));
        pnlLeft.add(lblTitle1);
        pnlLeft.add(Box.createVerticalStrut(10));
        pnlLeft.add(lblTitle2);
        pnlLeft.add(Box.createVerticalGlue());
        pnlLeft.add(lblAuthor1);
        pnlLeft.add(lblAuthor2);
        pnlLeft.add(Box.createVerticalStrut(20));
        pnlLeft.add(yearPanel);

        // ==========================================
        // PHẦN BÊN PHẢI: FORM ĐĂNG NHẬP
        // ==========================================
        JPanel pnlRight = new JPanel(new GridBagLayout());
        pnlRight.setBackground(colorBeige);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel lblLoginTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        lblLoginTitle.setFont(new Font("Arial", Font.BOLD, 26));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 50, 0);
        pnlRight.add(lblLoginTitle, gbc);

        // Reset gridwidth và font cho label
        gbc.gridwidth = 1;
        Font labelFont = new Font("Arial", Font.BOLD, 16);

        // Dòng Tài khoản
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel lbUser = new JLabel("Tài khoản:");
        lbUser.setFont(labelFont);
        pnlRight.add(lbUser, gbc);

        txtUser = new JTextField();
        txtUser.setFont(new Font("Arial", Font.PLAIN, 16));
        txtUser.setPreferredSize(new Dimension(280, 45)); // Kích thước chuẩn
        txtUser.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 1), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 1; pnlRight.add(txtUser, gbc);

        // Dòng Mật khẩu
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lbPass = new JLabel("Mật khẩu:");
        lbPass.setFont(labelFont);
        pnlRight.add(lbPass, gbc);

        txtPass = new JPasswordField();
        txtPass.setFont(new Font("Arial", Font.PLAIN, 16));
        txtPass.setPreferredSize(new Dimension(280, 45)); // Kích thước chuẩn
        txtPass.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.GRAY, 1), 
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 1; pnlRight.add(txtPass, gbc);

        // NÚT ĐĂNG NHẬP (Cùng kích cỡ với ô nhập liệu)
        btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setBackground(Color.WHITE);
        btnLogin.setForeground(Color.BLACK); // Đổi sang trắng để rõ hơn trên nền đỏ
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Thiết lập kích thước y hệt txtUser và txtPass
        btnLogin.setPreferredSize(new Dimension(280, 45)); 
        btnLogin.setBorder(new LineBorder(colorRed.darker(), 1));

        // Đặt nút vào cột 1 (dưới ô mật khẩu) thay vì chiếm cả 2 cột để thẳng hàng
        gbc.gridx = 1; 
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 10, 10, 10); // Tạo khoảng cách phía trên nút
        pnlRight.add(btnLogin, gbc);

        mainContainer.add(pnlLeft);
        mainContainer.add(pnlRight);
        add(mainContainer);
        
        btnLogin.addActionListener(e -> dangNhap());
    }

    private void dangNhap() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());
        
        if(user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        
        TaiKhoanDTO tk = tkBUS.dangNhap(user, pass);
        
        if (tk == null) {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            
            // --- THÊM DÒNG NÀY ĐỂ MỞ FORM CHÍNH ---
            // Giả sử FrmMain của bạn nhận vào một TaiKhoanDTO để phân quyền
            new FrmMain(tk).setVisible(true); 
            
            // Nếu FrmMain của bạn không nhận tham số, hãy dùng: 
            // new FrmMain().setVisible(true);
            
            this.dispose(); // Đóng form đăng nhập sau khi đã mở form chính
        }
    }

    public static void main(String args[]) {
        // try {
        //    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new FrmDangNhap().setVisible(true));
    }
}