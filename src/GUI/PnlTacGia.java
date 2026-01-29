package GUI;

import BUS.TacGiaBUS;
import DTO.TacGiaDTO;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PnlTacGia extends JPanel {

    private JTextField txtMaTacGia, txtTenTacGia, txtNamSinh, txtQueQuan, txtTimKiem;
    private JTable table;
    private DefaultTableModel tableModel;
    
    // Gọi BUS
    TacGiaBUS tacGiaBUS = new TacGiaBUS();

    // Hàm Main giả lập để chạy thử Panel
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Quản Lý Tác Giả");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(100, 100, 900, 600);
                PnlTacGia panel = new PnlTacGia();
                frame.setContentPane(panel);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PnlTacGia() {
        setLayout(null);
        setSize(900, 600);

        // --- KHUNG NHẬP LIỆU (Bên trái) ---
        JPanel pnlNhap = new JPanel();
        pnlNhap.setLayout(null);
        pnlNhap.setBorder(new TitledBorder(null, "Thông Tin Tác Giả", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlNhap.setBounds(20, 20, 600, 250);
        add(pnlNhap);

        JLabel lblTen = new JLabel("Tên Tác Giả:");
        lblTen.setBounds(30, 40, 100, 25);
        pnlNhap.add(lblTen);
        txtTenTacGia = new JTextField();
        txtTenTacGia.setBounds(130, 40, 400, 25);
        pnlNhap.add(txtTenTacGia);

        JLabel lblNam = new JLabel("Năm Sinh:");
        lblNam.setBounds(30, 90, 100, 25);
        pnlNhap.add(lblNam);
        txtNamSinh = new JTextField();
        txtNamSinh.setBounds(130, 90, 400, 25);
        pnlNhap.add(txtNamSinh);

        JLabel lblQue = new JLabel("Quê Quán:");
        lblQue.setBounds(30, 140, 100, 25);
        pnlNhap.add(lblQue);
        txtQueQuan = new JTextField();
        txtQueQuan.setBounds(130, 140, 400, 25);
        pnlNhap.add(txtQueQuan);

        // Ẩn mã tác giả để xử lý Sửa/Xóa
        txtMaTacGia = new JTextField();
        txtMaTacGia.setVisible(false);
        add(txtMaTacGia);

        // --- CÁC NÚT CHỨC NĂNG (Bên phải - Giống hình) ---
        int btnX = 650;
        int btnW = 120;
        int btnH = 40;

        JButton btnThem = new JButton("Thêm");
        btnThem.setBounds(btnX, 40, btnW, btnH);
        add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.setBounds(btnX, 100, btnW, btnH);
        add(btnSua);

        JButton btnXoa = new JButton("Xoá");
        btnXoa.setBounds(btnX, 160, btnW, btnH);
        add(btnXoa);

        JButton btnTaiLai = new JButton("Tải Lại");
        btnTaiLai.setBounds(btnX, 220, btnW, btnH);
        add(btnTaiLai);

        // --- KHU VỰC TÌM KIẾM ---
        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        lblTimKiem.setBounds(20, 300, 80, 25);
        add(lblTimKiem);
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(100, 300, 350, 25);
        add(txtTimKiem);
        JButton btnTim = new JButton("Tìm");
        btnTim.setBounds(460, 300, 80, 25);
        add(btnTim);

        // --- BẢNG DỮ LIỆU ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 340, 840, 200);
        add(scrollPane);

        String[] headers = {"Mã Tác Giả", "Tên Tác Giả", "Năm Sinh", "Quê Quán"};
        tableModel = new DefaultTableModel(headers, 0);
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        // --- LOAD DỮ LIỆU ---
        loadData(tacGiaBUS.getDanhSachTacGia());

        // --- SỰ KIỆN ---

        // 1. Click Bảng -> Hiện thông tin
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaTacGia.setText(tableModel.getValueAt(row, 0).toString());
                    txtTenTacGia.setText(tableModel.getValueAt(row, 1).toString());
                    txtNamSinh.setText(tableModel.getValueAt(row, 2).toString());
                    txtQueQuan.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        // 2. Nút Thêm
        btnThem.addActionListener(e -> {
            try {
                TacGiaDTO tg = new TacGiaDTO();
                tg.setTenTacGia(txtTenTacGia.getText());
                tg.setNamSinh(Integer.parseInt(txtNamSinh.getText()));
                tg.setQueQuan(txtQueQuan.getText());

                if (tacGiaBUS.themTacGia(tg)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadData(tacGiaBUS.getDanhSachTacGia());
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi nhập liệu (Năm sinh phải là số): " + ex.getMessage());
            }
        });

        // 3. Nút Sửa
        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(null, "Chọn tác giả để sửa!"); return; }
            try {
                TacGiaDTO tg = new TacGiaDTO();
                tg.setMaTacGia(Integer.parseInt(tableModel.getValueAt(row, 0).toString()));
                tg.setTenTacGia(txtTenTacGia.getText());
                tg.setNamSinh(Integer.parseInt(txtNamSinh.getText()));
                tg.setQueQuan(txtQueQuan.getText());

                if (tacGiaBUS.suaTacGia(tg)) {
                    JOptionPane.showMessageDialog(null, "Sửa thành công!");
                    loadData(tacGiaBUS.getDanhSachTacGia());
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
            }
        });

        // 4. Nút Xóa
        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(null, "Chọn tác giả để xóa!"); return; }
            if (JOptionPane.showConfirmDialog(null, "Bạn chắc chắn xóa?") == JOptionPane.YES_OPTION) {
                int ma = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                if (tacGiaBUS.xoaTacGia(ma)) {
                    JOptionPane.showMessageDialog(null, "Đã xóa!");
                    loadData(tacGiaBUS.getDanhSachTacGia());
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại (Có thể tác giả đang có sách)!");
                }
            }
        });

        // 5. Nút Tìm
        btnTim.addActionListener(e -> {
            String keyword = txtTimKiem.getText();
            if (keyword.isEmpty()) {
                loadData(tacGiaBUS.getDanhSachTacGia());
            } else {
                loadData(tacGiaBUS.timKiemTacGia(keyword));
            }
        });

        // 6. Nút Tải lại (Reset)
        btnTaiLai.addActionListener(e -> {
            resetForm();
            loadData(tacGiaBUS.getDanhSachTacGia());
        });
    }

    private void loadData(ArrayList<TacGiaDTO> list) {
        tableModel.setRowCount(0);
        for (TacGiaDTO tg : list) {
            Vector<Object> vec = new Vector<>();
            vec.add(tg.getMaTacGia());
            vec.add(tg.getTenTacGia());
            vec.add(tg.getNamSinh());
            vec.add(tg.getQueQuan());
            tableModel.addRow(vec);
        }
    }

    private void resetForm() {
        txtMaTacGia.setText("");
        txtTenTacGia.setText("");
        txtNamSinh.setText("");
        txtQueQuan.setText("");
        txtTimKiem.setText("");
    }
}