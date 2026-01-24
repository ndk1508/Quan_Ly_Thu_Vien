package GUI;

import DTO.TaiKhoanDTO;
import java.awt.*;
import javax.swing.*;

public class FrmMain extends JFrame {

    // ====== Components ======
    JPanel pnlHeader, pnlMenu, pnlContent;

    JButton btnTrangChu, btnSach, btnDocGia, btnTacGia,
            btnNXB, btnNhanVien, btnPhieuMuon,
            btnPhieuNhap, btnThongKe, btnDangXuat;

    JLabel lblTitle, lblWelcome;

    TaiKhoanDTO taiKhoan;

    // ====== Constructor ======
    public FrmMain(TaiKhoanDTO tk) {
        this.taiKhoan = tk;
        initUI();
        phanQuyen();
    }

    // ====== Giao diện chính ======
    private void initUI() {
        setTitle("Quản Lý Thư Viện");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(60, 60, 60));
        pnlHeader.setPreferredSize(new Dimension(0, 60));

        lblTitle = new JLabel("QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));

        pnlHeader.setLayout(new BorderLayout());
        pnlHeader.add(lblTitle, BorderLayout.CENTER);

        // ===== MENU LEFT =====
        pnlMenu = new JPanel();
        pnlMenu.setBackground(new Color(50, 50, 50));
        pnlMenu.setPreferredSize(new Dimension(220, 0));
        pnlMenu.setLayout(new GridLayout(11, 1, 0, 5));

        btnTrangChu   = taoButton("Trang Chủ");
        btnSach       = taoButton("Sách");
        btnDocGia     = taoButton("Độc Giả");
        btnTacGia     = taoButton("Tác Giả");
        btnNXB        = taoButton("Nhà Xuất Bản");
        btnNhanVien   = taoButton("Nhân Viên");
        btnPhieuMuon  = taoButton("Phiếu Mượn");
        btnPhieuNhap  = taoButton("Phiếu Nhập");
        btnThongKe    = taoButton("Thống Kê");
        btnDangXuat   = taoButton("Đăng Xuất");

        pnlMenu.add(btnTrangChu);
        pnlMenu.add(btnSach);
        pnlMenu.add(btnDocGia);
        pnlMenu.add(btnTacGia);
        pnlMenu.add(btnNXB);
        pnlMenu.add(btnNhanVien);
        pnlMenu.add(btnPhieuMuon);
        pnlMenu.add(btnPhieuNhap);
        pnlMenu.add(btnThongKe);
        pnlMenu.add(new JLabel()); // khoảng trống
        pnlMenu.add(btnDangXuat);

        // ===== CONTENT =====
        pnlContent = new JPanel();
        pnlContent.setBackground(new Color(245, 245, 245));
        pnlContent.setLayout(new BorderLayout());

        lblWelcome = new JLabel(
            "CHÀO MỪNG BẠN ĐẾN VỚI THƯ VIỆN TRƯỜNG ĐẠI HỌC NAM CẦN THƠ",
            JLabel.CENTER
        );
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblWelcome.setForeground(Color.PINK);

        pnlContent.add(lblWelcome, BorderLayout.CENTER);

        // ===== Add vào Frame =====
        add(pnlHeader, BorderLayout.NORTH);
        add(pnlMenu, BorderLayout.WEST);
        add(pnlContent, BorderLayout.CENTER);

        // ===== Sự kiện =====
        btnDangXuat.addActionListener(e -> dangXuat());
    }

    // ===== Button style =====
    private JButton taoButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(255, 140, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70, 70, 70));
            }
        });

        return btn;
    }

    // ===== Phân quyền =====
    private void phanQuyen() {
        // quyen = 1 → Nhân viên
        if (taiKhoan.getQuyen() == 1) {
            btnNhanVien.setVisible(false);
            btnThongKe.setVisible(false);
        }
    }

    // ===== Đăng xuất =====
    private void dangXuat() {
        int chon = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn đăng xuất?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (chon == JOptionPane.YES_OPTION) {
            this.dispose();
            new FrmDangNhap().setVisible(true);
        }
    }
}
