package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PnlSidebar extends JPanel {

    public PnlSidebar(PnlContent pnlContent) {

        setLayout(new GridLayout(8, 1, 0, 10));
        setPreferredSize(new Dimension(200, 0));
        setBackground(new Color(45, 45, 45));

        JButton btnSach = new JButton("Sách");
        JButton btnDocGia = new JButton("Độc giả");
        JButton btnNhanVien = new JButton("Nhân viên");
        JButton btnTacGia = new JButton("Tác giả");
        JButton btnNXB = new JButton("Nhà xuất bản");
        JButton btnPM = new JButton("Phiếu mượn");
        JButton btnPN = new JButton("Phiếu nhập");
        JButton btnThongKe = new JButton("Thống kê");

        // Action
        btnSach.addActionListener(e -> pnlContent.showPanel("SACH"));
        btnDocGia.addActionListener(e -> pnlContent.showPanel("DOCGIA"));
        btnNhanVien.addActionListener(e -> pnlContent.showPanel("NHANVIEN"));
        btnTacGia.addActionListener(e -> pnlContent.showPanel("TACGIA"));
        btnNXB.addActionListener(e -> pnlContent.showPanel("NXB"));
        btnPM.addActionListener(e -> pnlContent.showPanel("PHIEUMUON"));
        btnPN.addActionListener(e -> pnlContent.showPanel("PHIEUNHAP"));
        btnThongKe.addActionListener(e -> pnlContent.showPanel("THONGKE"));

        add(btnSach);
        add(btnDocGia);
        add(btnNhanVien);
        add(btnTacGia);
        add(btnNXB);
        add(btnPM);
        add(btnPN);
        add(btnThongKe);
    }
}
