package GUI;

import BUS.NhaXuatBanBUS;
import DTO.NhaXuatBanDTO;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PnlNhaXuatBan extends JPanel {

    private JTextField txtMaNXB, txtTenNXB, txtDiaChi, txtSdt, txtTimKiem;
    private JTable table;
    private DefaultTableModel tableModel;

    // Gọi BUS
    NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS();

    // Hàm Main để test chạy thử
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Quản Lý Nhà Xuất Bản");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(100, 100, 900, 600);
                PnlNhaXuatBan panel = new PnlNhaXuatBan();
                frame.setContentPane(panel);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PnlNhaXuatBan() {
        setLayout(null);
        setSize(900, 600);

        // --- KHUNG NHẬP LIỆU ---
        JPanel pnlNhap = new JPanel();
        pnlNhap.setLayout(null);
        pnlNhap.setBorder(new TitledBorder(null, "Thông Tin Nhà Xuất Bản", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlNhap.setBounds(20, 20, 600, 250);
        add(pnlNhap);

        JLabel lblTen = new JLabel("Tên Nhà Xuất Bản:");
        lblTen.setBounds(30, 40, 120, 25);
        pnlNhap.add(lblTen);
        txtTenNXB = new JTextField();
        txtTenNXB.setBounds(150, 40, 380, 25);
        pnlNhap.add(txtTenNXB);

        JLabel lblDiaChi = new JLabel("Địa Chỉ:");
        lblDiaChi.setBounds(30, 90, 120, 25);
        pnlNhap.add(lblDiaChi);
        txtDiaChi = new JTextField();
        txtDiaChi.setBounds(150, 90, 380, 25);
        pnlNhap.add(txtDiaChi);

        JLabel lblSdt = new JLabel("Số Điện Thoại:");
        lblSdt.setBounds(30, 140, 120, 25);
        pnlNhap.add(lblSdt);
        txtSdt = new JTextField();
        txtSdt.setBounds(150, 140, 380, 25);
        pnlNhap.add(txtSdt);

        // Ẩn Mã NXB
        txtMaNXB = new JTextField();
        txtMaNXB.setVisible(false);
        add(txtMaNXB);

        // --- CÁC NÚT CHỨC NĂNG ---
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

        // --- TÌM KIẾM ---
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

        String[] headers = {"Mã NXB", "Tên Nhà Xuất Bản", "Địa Chỉ", "Số Điện Thoại"};
        tableModel = new DefaultTableModel(headers, 0);
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        // --- LOAD DỮ LIỆU ---
        loadData(nxbBUS.getDanhSachNXB());

        // --- SỰ KIỆN ---

        // 1. Click Bảng
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaNXB.setText(tableModel.getValueAt(row, 0).toString());
                    txtTenNXB.setText(tableModel.getValueAt(row, 1).toString());
                    txtDiaChi.setText(tableModel.getValueAt(row, 2).toString());
                    txtSdt.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        // 2. Thêm
        btnThem.addActionListener(e -> {
            try {
                NhaXuatBanDTO nxb = new NhaXuatBanDTO();
                nxb.setTenNXB(txtTenNXB.getText());
                nxb.setDiaChi(txtDiaChi.getText());
                nxb.setSdt(txtSdt.getText());

                if (nxbBUS.themNXB(nxb)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadData(nxbBUS.getDanhSachNXB());
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 3. Sửa
        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(null, "Chọn NXB để sửa!"); return; }
            try {
                NhaXuatBanDTO nxb = new NhaXuatBanDTO();
                nxb.setMaNXB(Integer.parseInt(tableModel.getValueAt(row, 0).toString()));
                nxb.setTenNXB(txtTenNXB.getText());
                nxb.setDiaChi(txtDiaChi.getText());
                nxb.setSdt(txtSdt.getText());

                if (nxbBUS.suaNXB(nxb)) {
                    JOptionPane.showMessageDialog(null, "Sửa thành công!");
                    loadData(nxbBUS.getDanhSachNXB());
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 4. Xóa
        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(null, "Chọn NXB để xóa!"); return; }
            if (JOptionPane.showConfirmDialog(null, "Bạn chắc chắn xóa?") == JOptionPane.YES_OPTION) {
                int ma = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                if (nxbBUS.xoaNXB(ma)) {
                    JOptionPane.showMessageDialog(null, "Đã xóa!");
                    loadData(nxbBUS.getDanhSachNXB());
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại (Có thể NXB đang có sách)!");
                }
            }
        });

        // 5. Tìm kiếm
        btnTim.addActionListener(e -> {
            String keyword = txtTimKiem.getText();
            if (keyword.isEmpty()) {
                loadData(nxbBUS.getDanhSachNXB());
            } else {
                loadData(nxbBUS.timKiemNXB(keyword));
            }
        });

        // 6. Tải lại
        btnTaiLai.addActionListener(e -> {
            resetForm();
            loadData(nxbBUS.getDanhSachNXB());
        });
    }

    private void loadData(ArrayList<NhaXuatBanDTO> list) {
        tableModel.setRowCount(0);
        for (NhaXuatBanDTO nxb : list) {
            Vector<Object> vec = new Vector<>();
            vec.add(nxb.getMaNXB());
            vec.add(nxb.getTenNXB());
            vec.add(nxb.getDiaChi());
            vec.add(nxb.getSdt());
            tableModel.addRow(vec);
        }
    }

    private void resetForm() {
        txtMaNXB.setText("");
        txtTenNXB.setText("");
        txtDiaChi.setText("");
        txtSdt.setText("");
        txtTimKiem.setText("");
    }
}