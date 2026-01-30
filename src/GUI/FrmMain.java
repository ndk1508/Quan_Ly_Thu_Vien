package GUI;

import DTO.TaiKhoanDTO;
import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class FrmMain extends JFrame {
	private JButton btnDangChon = null;

    JPanel pnlHeader, pnlMenu, pnlContent;
    JButton btnTrangChu, btnSach, btnDocGia, btnTacGia,
            btnNXB, btnNhanVien, btnPhieuMuon,
            btnPhieuNhap, btnThongKe, btnDangXuat;

    JLabel lblTitle, lblWelcome;
    TaiKhoanDTO taiKhoan;

    public FrmMain(TaiKhoanDTO tk) {
        this.taiKhoan = tk;
        initUI();
        phanQuyen();
        suKienMenu();
    }

    // --- HÀM LẤY ICON (KHÔNG RESIZE) ---
    private ImageIcon getAppIcon(String iconName) {
        URL url = getClass().getResource("/icons/" + iconName);
        if (url != null) {
            return new ImageIcon(url);
        }
        return null;
    }

    private void initUI() {
        setTitle("Quản Lý Thư Viện");
        setSize(1250, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(40, 40, 40));
        pnlHeader.setPreferredSize(new Dimension(0, 60));

        lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnlHeader.add(lblTitle);

        // ===== MENU (Khớp tên file từ danh sách icons của bạn) =====
        pnlMenu = new JPanel(new GridLayout(11, 1, 0, 0)); 
        pnlMenu.setBackground(new Color(51, 51, 51));
        pnlMenu.setPreferredSize(new Dimension(240, 0));

        // Tên file phải khớp tuyệt đối kể cả hoa/thường và khoảng trắng
        btnTrangChu = taoButton("Trang Chủ", "home.png"); 
        btnSach = taoButton("Sách", "bookicon.png");
        btnDocGia = taoButton("Độc Giả", "docgia.png");
        btnTacGia = taoButton("Tác Giả", "Tacgia.png");
        btnNXB = taoButton("Nhà Xuất Bản", "nhaxuatban.png");
        btnNhanVien = taoButton("Nhân Viên", "Staff.png");
        btnPhieuMuon = taoButton("Phiếu Mượn", "phieumuon.png");
        btnPhieuNhap = taoButton("Phiếu Nhập", "phieunhap.png");
        btnThongKe = taoButton("Thống Kê", "Combo Chart.png");
        btnDangXuat = taoButton("Đăng Xuất", "Exit.png");

        pnlMenu.add(btnTrangChu);
        pnlMenu.add(btnSach);
        pnlMenu.add(btnDocGia);
        pnlMenu.add(btnTacGia);
        pnlMenu.add(btnNXB);
        pnlMenu.add(btnNhanVien);
        pnlMenu.add(btnPhieuMuon);
        pnlMenu.add(btnPhieuNhap);
        pnlMenu.add(btnThongKe);
        pnlMenu.add(new JLabel()); // Placeholder
        pnlMenu.add(btnDangXuat);

        // ===== CONTENT =====
        pnlContent = new JPanel(new BorderLayout());
        pnlContent.setBackground(Color.WHITE);

        lblWelcome = new JLabel(
                "CHÀO MỪNG BẠN ĐẾN VỚI THƯ VIỆN ĐẠI HỌC NAM CẦN THƠ",
                JLabel.CENTER
        );
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblWelcome.setForeground(new Color(255, 105, 180));

        pnlContent.add(lblWelcome, BorderLayout.CENTER);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlMenu, BorderLayout.WEST);
        add(pnlContent, BorderLayout.CENTER);

        btnDangXuat.addActionListener(e -> dangXuat());
        setActiveButton(btnTrangChu);

    }

    private void loadPanel(JPanel panel) {
        pnlContent.removeAll();
        pnlContent.add(panel, BorderLayout.CENTER);
        pnlContent.revalidate();
        pnlContent.repaint();
    }

    private void suKienMenu() {
        btnSach.addActionListener(e -> loadPanel(new PnlSach()));
        btnDocGia.addActionListener(e -> loadPanel(new PnlDocGia()));
        btnNhanVien.addActionListener(e -> loadPanel(new PnlNhanVien()));
        btnTacGia.addActionListener(e -> loadPanel(new PnlTacGia()));
        btnNXB.addActionListener(e -> loadPanel(new PnlNhaXuatBan()));
        btnPhieuMuon.addActionListener(e -> loadPanel(new PnlPhieuMuon()));
        btnPhieuNhap.addActionListener(e -> loadPanel(new PnlPhieuNhap()));
        btnThongKe.addActionListener(e -> loadPanel(new PnlThongKe()));
        btnTrangChu.addActionListener(e -> {
            pnlContent.removeAll();
            pnlContent.add(lblWelcome, BorderLayout.CENTER);
            pnlContent.revalidate();
            pnlContent.repaint();
        });
    }

    // --- HÀM TẠO BUTTON CÓ ICON (ĐÃ BỎ RESIZE) ---
    private JButton taoButton(String text, String iconName) {

        JButton btn = new JButton(text);

        // Icon
        ImageIcon icon = getAppIcon(iconName);
        if (icon != null) {
            btn.setIcon(icon);
        }

        // Style cơ bản
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(51, 51, 51));
        btn.setForeground(Color.WHITE);

        // Viền trên + dưới
        btn.setBorder(BorderFactory.createMatteBorder(
                1, 0, 1, 0, new Color(80, 80, 80)
        ));

        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(20);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover + Click effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

                if (btn != btnDangChon) {
                    btn.setBackground(new Color(70, 70, 70));

                    btn.setBorder(BorderFactory.createMatteBorder(
                            1, 0, 1, 0, new Color(255, 140, 0)
                    ));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

                if (btn != btnDangChon) {
                    btn.setBackground(new Color(51, 51, 51));

                    btn.setBorder(BorderFactory.createMatteBorder(
                            1, 0, 1, 0, new Color(80, 80, 80)
                    ));
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                setActiveButton(btn);
            }
        });

        return btn;
    }



    private void phanQuyen() {
        if (taiKhoan.getQuyen() == 1) {
            btnNhanVien.setVisible(false);
            btnThongKe.setVisible(false);
        }
    }

    private void dangXuat() {
        if (JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn đăng xuất?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dispose();
            new FrmDangNhap().setVisible(true);
        }
    }
    private void setActiveButton(JButton btn) {

        if (btnDangChon != null) {
            // Trả nút cũ về màu mặc định
            btnDangChon.setBackground(new Color(51, 51, 51));
            btnDangChon.setBorder(BorderFactory.createMatteBorder(
                    1, 0, 1, 0, new Color(80, 80, 80)
            ));
        }

        // Set nút đang chọn
        btn.setBackground(new Color(90, 90, 90));
        btn.setBorder(BorderFactory.createMatteBorder(
                1, 0, 1, 0, new Color(255, 140, 0)
        ));

        btnDangChon = btn;
    }

}