package GUI;

import DTO.TaiKhoanDTO;
import java.awt.*;
import javax.swing.*;

public class FrmMain extends JFrame {

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

    private void initUI() {
        setTitle("Quản Lý Thư Viện");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(60, 60, 60));
        pnlHeader.setPreferredSize(new Dimension(0, 60));

        lblTitle = new JLabel("QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnlHeader.add(lblTitle);

        // ===== MENU =====
        pnlMenu = new JPanel(new GridLayout(11, 1, 0, 5));
        pnlMenu.setBackground(new Color(50, 50, 50));
        pnlMenu.setPreferredSize(new Dimension(220, 0));

        btnTrangChu = taoButton("Trang Chủ");
        btnSach = taoButton("Sách");
        btnDocGia = taoButton("Độc Giả");
        btnTacGia = taoButton("Tác Giả");
        btnNXB = taoButton("Nhà Xuất Bản");
        btnNhanVien = taoButton("Nhân Viên");
        btnPhieuMuon = taoButton("Phiếu Mượn");
        btnPhieuNhap = taoButton("Phiếu Nhập");
        btnThongKe = taoButton("Thống Kê");
        btnDangXuat = taoButton("Đăng Xuất");

        pnlMenu.add(btnTrangChu);
        pnlMenu.add(btnSach);
        pnlMenu.add(btnDocGia);
        pnlMenu.add(btnTacGia);
        pnlMenu.add(btnNXB);
        pnlMenu.add(btnNhanVien);
        pnlMenu.add(btnPhieuMuon);
        pnlMenu.add(btnPhieuNhap);
        pnlMenu.add(btnThongKe);
        pnlMenu.add(new JLabel());
        pnlMenu.add(btnDangXuat);

        // ===== CONTENT =====
        pnlContent = new JPanel(new BorderLayout());

        lblWelcome = new JLabel(
                "CHÀO MỪNG BẠN ĐẾN VỚI THƯ VIỆN ĐẠI HỌC NAM CẦN THƠ",
                JLabel.CENTER
        );
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblWelcome.setForeground(Color.PINK);

        pnlContent.add(lblWelcome, BorderLayout.CENTER);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlMenu, BorderLayout.WEST);
        add(pnlContent, BorderLayout.CENTER);

        btnDangXuat.addActionListener(e -> dangXuat());
    }

    // ===== LOAD PANEL (ĐÚNG) =====
    private void loadPanel(JPanel panel) {
        pnlContent.removeAll();
        pnlContent.add(panel, BorderLayout.CENTER);
        pnlContent.revalidate();
        pnlContent.repaint();
    }

    // ===== SỰ KIỆN MENU =====
    private void suKienMenu() {

        btnSach.addActionListener(e ->
                loadPanel(new PnlSach())
        );

        btnDocGia.addActionListener(e ->
                loadPanel(new PnlDocGia())
        );

        btnNhanVien.addActionListener(e ->
                loadPanel(new PnlNhanVien())
        );

        btnTacGia.addActionListener(e ->
                loadPanel(new PnlTacGia())
        );

        btnNXB.addActionListener(e ->
                loadPanel(new PnlNhaXuatBan())
        );

        btnPhieuMuon.addActionListener(e ->
                loadPanel(new PnlPhieuMuon())
        );

        btnPhieuNhap.addActionListener(e ->
                loadPanel(new PnlPhieuNhap())
        );

        btnThongKe.addActionListener(e ->
                loadPanel(new PnlThongKe())
        );

        btnTrangChu.addActionListener(e -> {
            pnlContent.removeAll();
            pnlContent.add(lblWelcome, BorderLayout.CENTER);
            pnlContent.revalidate();
            pnlContent.repaint();
        });
    }

    private JButton taoButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
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
}
