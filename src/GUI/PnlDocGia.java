package GUI;

import BUS.DocGiaBUS;
import DTO.DocGiaDTO;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PnlDocGia extends JPanel {

    private JTextField txtMa, txtTen, txtDiaChi, txtSdt;
    private JComboBox<String> cboGioiTinh, cboTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private JTable table;
    private DefaultTableModel model;

    private DocGiaBUS bus = new DocGiaBUS();

    public PnlDocGia() {
        setLayout(new BorderLayout(10, 10));

        initForm();
        initTable();
        initButton();

        loadData();
        event();
    }

    // ================= FORM =================
    private void initForm() {
        JPanel form = new JPanel(new GridLayout(3, 4, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin độc giả"));

        txtMa = new JTextField();
        txtTen = new JTextField();
        txtDiaChi = new JTextField();
        txtSdt = new JTextField();

        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Ngừng"});

        form.add(new JLabel("Mã độc giả"));
        form.add(txtMa);
        form.add(new JLabel("Tên độc giả"));
        form.add(txtTen);

        form.add(new JLabel("Giới tính"));
        form.add(cboGioiTinh);
        form.add(new JLabel("Địa chỉ"));
        form.add(txtDiaChi);

        form.add(new JLabel("SĐT"));
        form.add(txtSdt);
        form.add(new JLabel("Trạng thái"));
        form.add(cboTrangThai);

        add(form, BorderLayout.NORTH);
    }

    // ================= TABLE =================
    private void initTable() {
        model = new DefaultTableModel(
                new String[]{"Mã DG", "Tên DG", "Giới tính", "Địa chỉ", "SĐT", "Trạng thái"}, 0
        );
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // ================= BUTTON =================
    private void initButton() {
        JPanel btnPanel = new JPanel();

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");

        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);

        add(btnPanel, BorderLayout.SOUTH);

        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
    }

    // ================= LOAD DATA =================
    private void loadData() {
        model.setRowCount(0);
        for (DocGiaDTO dg : bus.getAll()) {
            model.addRow(new Object[]{
                    dg.getMaDocGia(),
                    dg.getTenDocGia(),
                    dg.getGioiTinh(),
                    dg.getDiaChi(),
                    dg.getSdt(),
                    dg.getTrangThai() == 1 ? "Hoạt động" : "Ngừng"
            });
        }
    }

    // ================= EVENT =================
    private void event() {

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMa.setText(model.getValueAt(row, 0).toString());
                txtTen.setText(model.getValueAt(row, 1).toString());
                cboGioiTinh.setSelectedItem(model.getValueAt(row, 2).toString());
                txtDiaChi.setText(model.getValueAt(row, 3).toString());
                txtSdt.setText(model.getValueAt(row, 4).toString());

                String trangThai = model.getValueAt(row, 5).toString();
                cboTrangThai.setSelectedIndex(trangThai.equals("Hoạt động") ? 0 : 1);

                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);
                txtMa.setEnabled(false);
            }
        });

        btnThem.addActionListener(e -> {
            DocGiaDTO dg = getForm();
            if (bus.them(dg)) {
                loadData();
                clearForm();
            }
        });

        btnSua.addActionListener(e -> {
            DocGiaDTO dg = getForm();
            if (bus.sua(dg)) {
                loadData();
                clearForm();
            }
        });

        btnXoa.addActionListener(e -> {
            if (txtMa.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả");
                return;
            }

            int ma = Integer.parseInt(txtMa.getText());

            String result = bus.kiemTraXoa(ma);

            if (!result.equals("OK")) {
                JOptionPane.showMessageDialog(
                        this,
                        result,
                        "Không thể xoá",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Độc giả hiện không được phép xoá trong hệ thống.\n(Vui lòng chuyển sang trạng thái Ngừng)",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });



        btnLamMoi.addActionListener(e -> clearForm());
    }

    // ================= HỖ TRỢ =================
    private DocGiaDTO getForm() {
        return new DocGiaDTO(
                Integer.parseInt(txtMa.getText()),
                txtTen.getText(),
                cboGioiTinh.getSelectedItem().toString(),
                txtDiaChi.getText(),
                txtSdt.getText(),
                cboTrangThai.getSelectedIndex() == 0 ? 1 : 0
        );
    }

    private void clearForm() {
        txtMa.setText("");
        txtTen.setText("");
        txtDiaChi.setText("");
        txtSdt.setText("");
        cboGioiTinh.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);

        txtMa.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        table.clearSelection();
    }
}
