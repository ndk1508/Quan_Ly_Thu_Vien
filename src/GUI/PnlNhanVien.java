package GUI;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PnlNhanVien extends JPanel {

    private JTextField txtMaNV, txtTenNV, txtNamSinh, txtDiaChi, txtSdt, txtTimKiem; // Thêm txtTimKiem
    private JComboBox<String> cboGioiTinh, cboTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem; // Thêm btnTimKiem
    private JTable tbl;
    private DefaultTableModel model;
    private NhanVienBUS bus = new NhanVienBUS();

    public PnlNhanVien() {
        setLayout(new BorderLayout(10, 10));

        // ===== VÙNG PHÍA BẮC (FORM + TÌM KIẾM) =====
        JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));

        // 1. FORM NHẬP LIỆU
        JPanel pnlForm = new JPanel(new GridLayout(4, 4, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));

        txtMaNV = new JTextField();
        txtTenNV = new JTextField();
        txtNamSinh = new JTextField();
        txtDiaChi = new JTextField();
        txtSdt = new JTextField();
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Ngừng"});

        pnlForm.add(new JLabel("Mã NV")); pnlForm.add(txtMaNV);
        pnlForm.add(new JLabel("Tên NV")); pnlForm.add(txtTenNV);
        pnlForm.add(new JLabel("Năm sinh")); pnlForm.add(txtNamSinh);
        pnlForm.add(new JLabel("Giới tính")); pnlForm.add(cboGioiTinh);
        pnlForm.add(new JLabel("Địa chỉ")); pnlForm.add(txtDiaChi);
        pnlForm.add(new JLabel("SĐT")); pnlForm.add(txtSdt);
        pnlForm.add(new JLabel("Trạng thái")); pnlForm.add(cboTrangThai);

        // 2. THANH TÌM KIẾM
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txtTimKiem = new JTextField(20);
        btnTimKiem = new JButton("Tìm kiếm");
        pnlSearch.add(new JLabel("Nhập mã hoặc tên: "));
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);

        pnlNorth.add(pnlForm, BorderLayout.CENTER);
        pnlNorth.add(pnlSearch, BorderLayout.SOUTH);

        // ===== BUTTON CHỨC NĂNG (DƯỚI CÙNG) =====
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");
        pnlButton.add(btnThem); pnlButton.add(btnSua); pnlButton.add(btnXoa); pnlButton.add(btnLamMoi);

        // ===== BẢNG DỮ LIỆU =====
        model = new DefaultTableModel(new String[]{
                "MaNV", "TenNV", "NamSinh", "GioiTinh", "DiaChi", "Sdt", "TrangThai"
        }, 0);
        tbl = new JTable(model);
        tbl.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tbl);

        // Gom nhóm layout chính
        add(pnlNorth, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);

        // ===== KHỞI CHẠY =====
        loadTable(bus.getAll());
        suKien();
    }

    // Cập nhật loadTable để nhận danh sách (phục vụ tìm kiếm)
    private void loadTable(ArrayList<NhanVienDTO> list) {
        model.setRowCount(0);
        for (NhanVienDTO nv : list) {
            model.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getNamSinh(),
                    nv.getGioiTinh(),
                    nv.getDiaChi(),
                    nv.getSdt(),
                    nv.getTrangThai() == 1 ? "Hoạt động" : "Ngừng"
            });
        }
    }

    private void suKien() {
        // TÌM KIẾM
        btnTimKiem.addActionListener(e -> {
            String keyword = txtTimKiem.getText().trim();
            if (keyword.isEmpty()) {
                loadTable(bus.getAll());
            } else {
                ArrayList<NhanVienDTO> ketQua = bus.timKiem(keyword);
                loadTable(ketQua);
            }
        });

        // Tìm nhanh khi nhấn Enter trong ô tìm kiếm
        txtTimKiem.addActionListener(e -> btnTimKiem.doClick());

        // CLICK TABLE -> ĐỔ FORM
        tbl.getSelectionModel().addListSelectionListener(e -> {
            int row = tbl.getSelectedRow();
            if (row >= 0) {
                txtMaNV.setText(model.getValueAt(row, 0).toString());
                txtTenNV.setText(model.getValueAt(row, 1).toString());
                txtNamSinh.setText(model.getValueAt(row, 2).toString());
                cboGioiTinh.setSelectedItem(model.getValueAt(row, 3));
                txtDiaChi.setText(model.getValueAt(row, 4).toString());
                txtSdt.setText(model.getValueAt(row, 5).toString());
                cboTrangThai.setSelectedItem(model.getValueAt(row, 6));
                txtMaNV.setEnabled(false);
            }
        });

        // THÊM
        btnThem.addActionListener(e -> {
            try {
                NhanVienDTO nv = getForm();
                if (bus.them(nv)) {
                    loadTable(bus.getAll());
                    lamMoi();
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu: " + ex.getMessage());
            }
        });

        // SỬA
        btnSua.addActionListener(e -> {
            try {
                NhanVienDTO nv = getForm();
                if (bus.sua(nv)) {
                    loadTable(bus.getAll());
                    JOptionPane.showMessageDialog(this, "Sửa thành công");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu!");
            }
        });

        // XOÁ
        btnXoa.addActionListener(e -> {
            if (txtMaNV.getText().isEmpty()) return;
            if (JOptionPane.showConfirmDialog(this, "Xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (bus.xoa(txtMaNV.getText())) {
                    loadTable(bus.getAll());
                    lamMoi();
                }
            }
        });

        // LÀM MỚI
        btnLamMoi.addActionListener(e -> {
            lamMoi();
            txtTimKiem.setText("");
            loadTable(bus.getAll());
        });
    }

    private NhanVienDTO getForm() {
        return new NhanVienDTO(
                txtMaNV.getText(),
                txtTenNV.getText(),
                Integer.parseInt(txtNamSinh.getText()),
                cboGioiTinh.getSelectedItem().toString(),
                txtDiaChi.getText(),
                txtSdt.getText(),
                cboTrangThai.getSelectedIndex() == 0 ? 1 : 0
        );
    }

    private void lamMoi() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtNamSinh.setText("");
        txtDiaChi.setText("");
        txtSdt.setText("");
        cboGioiTinh.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        txtMaNV.setEnabled(true);
        tbl.clearSelection();
    }
}