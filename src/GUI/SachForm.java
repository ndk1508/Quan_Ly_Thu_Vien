package GUI;

import DAL.DBConnect;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SachForm extends JFrame {

    private JTable tblSach;
    private DefaultTableModel model;

    public SachForm() {
        setTitle("Quản Lý Sách");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        loadData();
    }

    private void initUI() {
        // Panel chính
        JPanel panel = new JPanel(new BorderLayout());

        // Tiêu đề
        JLabel lblTitle = new JLabel("DANH SÁCH SÁCH", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblTitle, BorderLayout.NORTH);

        // Bảng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
                "MaSach", "TenSach", "MaLoai", "MaNXB", "MaTacGia",
                "NamXB", "SoLuong", "Make", "HinhAnh", "TrangThai"
        });

        tblSach = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblSach);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    private void loadData() {
        model.setRowCount(0);

        String sql = """
            SELECT MaSach, TenSach, MaLoai, MaNXB, MaTacGia,
                   NamXB, SoLuong, Make, HinhAnh, TrangThai
            FROM Sach
        """;

        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("MaSach"),
                        rs.getString("TenSach"),
                        rs.getInt("MaLoai"),
                        rs.getInt("MaNXB"),
                        rs.getInt("MaTacGia"),
                        rs.getInt("NamXB"),
                        rs.getInt("SoLuong"),
                        rs.getInt("Make"),
                        rs.getString("HinhAnh"),
                        rs.getObject("TrangThai")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi tải dữ liệu sách!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
