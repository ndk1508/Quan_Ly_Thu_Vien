package GUI;

import BUS.NhanVienBUS;
import DTO.NhanVienDTO;
import DTO.TaiKhoanDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PnlNhanVien extends JPanel {
    // Thành phần nhập liệu
    private JTextField txtMaNV, txtTenNV, txtNamSinh, txtDiaChi, txtSdt;
    private JTextField txtUsername, txtTimKiem;
    private JPasswordField txtPassword;
    private JComboBox<String> cboGioiTinh, cboTrangThai;

    // Thành phần điều khiển
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;
    private JTable tbl;
    private DefaultTableModel model;
    
    // Tầng nghiệp vụ
    private NhanVienBUS bus = new NhanVienBUS();

    public PnlNhanVien() {
        initComponents();
        loadTable(bus.getAll());
        suKien();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // 1. KHỞI TẠO CÁC ĐỐI TƯỢNG NHẬP LIỆU (Phải làm trước khi add vào Panel)
        txtMaNV = new JTextField();
        txtTenNV = new JTextField();
        txtNamSinh = new JTextField();
        txtDiaChi = new JTextField();
        txtSdt = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtTimKiem = new JTextField(20);

        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Ngừng"});

        // 2. PHẦN PHÍA BẮC: FORM NHẬP LIỆU & TÌM KIẾM
        JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));

        // --- Layout Form nhập liệu (GridLayout 4 cột) ---
        JPanel pnlForm = new JPanel(new GridLayout(0, 4, 15, 10));
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Thông tin chi tiết nhân viên"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Hàng 1: Mã và Tên
        pnlForm.add(new JLabel("Mã Nhân Viên:")); pnlForm.add(txtMaNV);
        pnlForm.add(new JLabel("Họ và Tên:")); pnlForm.add(txtTenNV);

        // Hàng 2: Giới tính và Năm sinh
        pnlForm.add(new JLabel("Giới Tính:")); pnlForm.add(cboGioiTinh);
        pnlForm.add(new JLabel("Năm Sinh:")); pnlForm.add(txtNamSinh);

        // Hàng 3: SĐT và Địa chỉ
        pnlForm.add(new JLabel("Số Điện Thoại:")); pnlForm.add(txtSdt);
        pnlForm.add(new JLabel("Địa Chỉ:")); pnlForm.add(txtDiaChi);

        // Hàng 4: Tài khoản (Nhóm thông tin quan trọng)
        pnlForm.add(new JLabel("Tên Đăng Nhập:")); pnlForm.add(txtUsername);
        pnlForm.add(new JLabel("Mật Khẩu:")); pnlForm.add(txtPassword);

        // Hàng 5: Trạng thái
        pnlForm.add(new JLabel("Trạng Thái:")); pnlForm.add(cboTrangThai);
        pnlForm.add(new JLabel("")); pnlForm.add(new JLabel("")); // Ô trống giữ hàng

        // --- Layout Thanh Tìm Kiếm ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Bộ lọc tìm kiếm"));
        btnTimKiem = new JButton("Tìm kiếm");
        pnlSearch.add(new JLabel("Nhập mã hoặc tên cần tìm: "));
        pnlSearch.add(txtTimKiem);
        pnlSearch.add(btnTimKiem);

        pnlNorth.add(pnlForm, BorderLayout.CENTER);
        pnlNorth.add(pnlSearch, BorderLayout.SOUTH);

        // 3. PHẦN GIỮA: BẢNG DỮ LIỆU
        model = new DefaultTableModel(new String[]{
                "Mã NV", "Họ Tên", "Năm Sinh", "Giới Tính", "Địa Chỉ", "SĐT", "Trạng Thái"
        }, 0);
        tbl = new JTable(model);
        tbl.setRowHeight(25);
        tbl.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        JScrollPane scroll = new JScrollPane(tbl);

        // 4. PHẦN PHÍA NAM: CÁC NÚT CHỨC NĂNG
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnThem = new JButton("Thêm mới");
        btnSua = new JButton("Cập nhật");
        btnXoa = new JButton("Xóa bỏ");
        btnLamMoi = new JButton("Làm mới");
        
        // Trang trí nút
        btnThem.setBackground(new Color(40, 167, 69)); btnThem.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(0, 123, 255)); btnSua.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(220, 53, 69)); btnXoa.setForeground(Color.WHITE);

        pnlButton.add(btnThem); 
        pnlButton.add(btnSua); 
        pnlButton.add(btnXoa); 
        pnlButton.add(btnLamMoi);

        // Gom tất cả vào Panel chính
        add(pnlNorth, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);
    }

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
        // Xử lý Tìm kiếm
        btnTimKiem.addActionListener(e -> {
            String keyword = txtTimKiem.getText().trim();
            loadTable(bus.timKiem(keyword));
        });

        // Xử lý click Table đổ ngược vào Form
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
                
                txtMaNV.setEnabled(false); // Không cho sửa mã
                txtUsername.setEnabled(false); // Thường không cho sửa user trực tiếp ở đây
                txtPassword.setEnabled(false);
            }
        });

        // Xử lý Thêm mới (Bao gồm đăng ký tài khoản)
        btnThem.addActionListener(e -> {
            try {
                if(txtUsername.getText().isEmpty() || new String(txtPassword.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản/mật khẩu!");
                    return;
                }
                
                NhanVienDTO nv = getForm();
                TaiKhoanDTO tk = new TaiKhoanDTO(0, txtUsername.getText(), new String(txtPassword.getPassword()), 1);

                if (bus.themNhanVienVaTaiKhoan(nv, tk)) {
                    loadTable(bus.getAll());
                    lamMoi();
                    JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });

        // Xử lý Sửa
        btnSua.addActionListener(e -> {
            try {
                if (bus.sua(getForm())) {
                    loadTable(bus.getAll());
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu!");
            }
        });

        // Xử lý Xóa
        btnXoa.addActionListener(e -> {
            String ma = txtMaNV.getText();
            if (ma.isEmpty()) return;
            if (JOptionPane.showConfirmDialog(this, "Xóa nhân viên " + ma + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (bus.xoa(ma)) {
                    loadTable(bus.getAll());
                    lamMoi();
                }
            }
        });

        // Xử lý Làm mới
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
        txtUsername.setText("");
        txtPassword.setText("");
        txtTimKiem.setText("");
        cboGioiTinh.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        txtMaNV.setEnabled(true);
        txtUsername.setEnabled(true);
        txtPassword.setEnabled(true);
        tbl.clearSelection();
        loadTable(bus.getAll());
    }
}