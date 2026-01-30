package GUI;

import BUS.DocGiaBUS;
import DTO.DocGiaDTO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PnlDocGia extends JPanel {

    private JTextField txtMa, txtTen, txtDiaChi, txtSdt, txtTimKiem; // Thêm txtTimKiem
    private JComboBox<String> cboGioiTinh, cboTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem; // Thêm btnTimKiem
    private JTable table;
    private DefaultTableModel model;

    private DocGiaBUS bus = new DocGiaBUS();

    public PnlDocGia() {
        setLayout(new BorderLayout(10, 10));

        // Gom nhóm Form và Tìm kiếm vào vùng NORTH
        JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
        pnlNorth.add(initForm(), BorderLayout.CENTER);
        pnlNorth.add(initSearchPanel(), BorderLayout.SOUTH);

        add(pnlNorth, BorderLayout.NORTH);
        initTable();
        initButton();

        loadData(bus.getAll()); // Load toàn bộ lúc đầu
        event();
    }

    // ================= FORM =================
    private JPanel initForm() {
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
        
        return form;
    }

    // ================= THANH TÌM KIẾM =================
    private JPanel initSearchPanel() {
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        
        txtTimKiem = new JTextField(25);
        btnTimKiem = new JButton("Tìm kiếm");
        
        pnlSearch.add(new JLabel("Nhập tên hoặc mã: "));
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);
        
        return pnlSearch;
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

    // ================= LOAD DATA (Sửa lại để nhận danh sách) =================
    private void loadData(ArrayList<DocGiaDTO> list) {
        model.setRowCount(0);
        for (DocGiaDTO dg : list) {
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
        // Sự kiện Click bảng
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

        // Xử lý Tìm kiếm
        btnTimKiem.addActionListener(e -> {
            String keyword = txtTimKiem.getText().trim();
            if (keyword.isEmpty()) {
                loadData(bus.getAll()); // Nếu rỗng thì hiện tất cả
            } else {
                ArrayList<DocGiaDTO> result = bus.timKiem(keyword);
                loadData(result);
            }
        });

        // Tìm kiếm nhanh khi đang gõ (Tùy chọn)
        txtTimKiem.addActionListener(e -> btnTimKiem.doClick());

        btnThem.addActionListener(e -> {
            DocGiaDTO dg = getForm();
            if (bus.them(dg)) {
                loadData(bus.getAll());
                clearForm();
            }
        });

        btnSua.addActionListener(e -> {
            DocGiaDTO dg = getForm();
            if (bus.sua(dg)) {
                loadData(bus.getAll());
                clearForm();
            }
        });

        btnXoa.addActionListener(e -> {
            if (txtMa.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả");
                return;
            }
            int ma = Integer.parseInt(txtMa.getText());
            // ... logic xóa giữ nguyên của bạn ...
        });

        btnLamMoi.addActionListener(e -> {
            clearForm();
            txtTimKiem.setText("");
            loadData(bus.getAll());
        });
    }

    // ================= HỖ TRỢ =================
    private DocGiaDTO getForm() {
        int ma = txtMa.getText().isEmpty() ? 0 : Integer.parseInt(txtMa.getText());
        return new DocGiaDTO(
                ma,
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