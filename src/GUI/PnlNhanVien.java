package GUI;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PnlNhanVien extends JPanel {

    JTextField txtMaNV, txtTenNV, txtNamSinh, txtDiaChi, txtSdt;
    JComboBox<String> cboGioiTinh, cboTrangThai;
    JButton btnThem, btnSua, btnXoa, btnLamMoi;
    JTable tbl;
    DefaultTableModel model;
    NhanVienBUS bus = new NhanVienBUS();

    public PnlNhanVien() {
        setLayout(new BorderLayout(10, 10));

        // ===== FORM =====
        JPanel pnlForm = new JPanel(new GridLayout(4, 4, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));

        txtMaNV = new JTextField();
        txtTenNV = new JTextField();
        txtNamSinh = new JTextField();
        txtDiaChi = new JTextField();
        txtSdt = new JTextField();

        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Ngừng"});

        pnlForm.add(new JLabel("Mã NV"));
        pnlForm.add(txtMaNV);
        pnlForm.add(new JLabel("Tên NV"));
        pnlForm.add(txtTenNV);

        pnlForm.add(new JLabel("Năm sinh"));
        pnlForm.add(txtNamSinh);
        pnlForm.add(new JLabel("Giới tính"));
        pnlForm.add(cboGioiTinh);

        pnlForm.add(new JLabel("Địa chỉ"));
        pnlForm.add(txtDiaChi);
        pnlForm.add(new JLabel("SĐT"));
        pnlForm.add(txtSdt);

        pnlForm.add(new JLabel("Trạng thái"));
        pnlForm.add(cboTrangThai);

        // ===== BUTTON =====
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");

        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnLamMoi);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(pnlForm, BorderLayout.CENTER);
        pnlTop.add(pnlButton, BorderLayout.SOUTH);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{
                "MaNV", "TenNV", "NamSinh",
                "GioiTinh", "DiaChi", "Sdt", "TrangThai"
        }, 0);

        tbl = new JTable(model);
        tbl.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tbl);

        add(pnlTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ===== EVENTS =====
        loadTable();
        suKien();
    }

    // ===== LOAD TABLE =====
    private void loadTable() {
        model.setRowCount(0);
        for (NhanVienDTO nv : bus.getAll()) {
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

    // ===== SỰ KIỆN =====
    private void suKien() {

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
            NhanVienDTO nv = getForm();
            if (bus.them(nv)) {
                loadTable();
                lamMoi();
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            }
        });

        // SỬA
        btnSua.addActionListener(e -> {
            NhanVienDTO nv = getForm();
            if (bus.sua(nv)) {
                loadTable();
                JOptionPane.showMessageDialog(this, "Sửa thành công");
            }
        });

        // XOÁ
        btnXoa.addActionListener(e -> {
            int row = tbl.getSelectedRow();
            if (row < 0) return;

            String maNV = txtMaNV.getText();
            if (JOptionPane.showConfirmDialog(this,
                    "Xóa nhân viên này?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                if (bus.xoa(maNV)) {
                    loadTable();
                    lamMoi();
                }
            }
        });

        // LÀM MỚI
        btnLamMoi.addActionListener(e -> lamMoi());
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
