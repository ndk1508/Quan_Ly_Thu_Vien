package GUI;

import BUS.PhieuMuonBUS;
import BUS.NhanVienBUS;
import BUS.DocGiaBUS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PnlPhieuMuon extends JPanel {

    private JTextField txtMaPM, txtNgayMuon;
    private JComboBox<String> cboNhanVien, cboDocGia, cboTinhTrang;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnLapPhieu, btnSua, btnXoa, btnDaTra, btnLamMoi;

    private PhieuMuonBUS pmBUS = new PhieuMuonBUS();
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private DocGiaBUS dgBUS = new DocGiaBUS();

    public PnlPhieuMuon() {
        setLayout(new BorderLayout(10, 10));
        
        initForm();     // Khởi tạo ô nhập liệu
        initTable();    // Khởi tạo bảng
        initButton();   // Khởi tạo các nút bấm
        loadCombo();    // Đổ dữ liệu vào ComboBox
        loadData();     // Đổ dữ liệu vào Bảng
        event();        // Xử lý sự kiện
    }

    private void initForm() {
        JPanel pnl = new JPanel(new GridLayout(3, 4, 10, 10));
        pnl.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu mượn"));

        txtMaPM = new JTextField();
        txtMaPM.setEditable(false); // Không cho sửa mã vì là khóa chính tự tăng
        txtMaPM.setBackground(Color.LIGHT_GRAY);

        txtNgayMuon = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        cboNhanVien = new JComboBox<>();
        cboDocGia = new JComboBox<>();
        cboTinhTrang = new JComboBox<>(new String[]{"Đang mượn", "Đã trả"});

        pnl.add(new JLabel("Mã PM:"));
        pnl.add(txtMaPM);
        pnl.add(new JLabel("Ngày mượn (yyyy-MM-dd):"));
        pnl.add(txtNgayMuon);

        pnl.add(new JLabel("Nhân viên:"));
        pnl.add(cboNhanVien);
        pnl.add(new JLabel("Tình trạng:"));
        pnl.add(cboTinhTrang);

        pnl.add(new JLabel("Độc giả:"));
        pnl.add(cboDocGia);

        add(pnl, BorderLayout.NORTH);
    }

    private void initTable() {
        model = new DefaultTableModel(
            new String[]{"Mã PM", "Nhân viên", "Độc giả", "Ngày mượn", "Tình trạng"}, 0
        );
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void initButton() {
        JPanel pnl = new JPanel();
        btnLapPhieu = new JButton("Lập phiếu");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnDaTra = new JButton("Đánh dấu đã trả");
        btnLamMoi = new JButton("Làm mới");

        pnl.add(btnLapPhieu);
        pnl.add(btnSua);
        pnl.add(btnXoa);
        pnl.add(btnDaTra);
        pnl.add(btnLamMoi);
        add(pnl, BorderLayout.SOUTH);
    }

    private void loadCombo() {
        cboNhanVien.removeAllItems();
        cboDocGia.removeAllItems();
        nvBUS.getAll().forEach(nv -> cboNhanVien.addItem(nv.getMaNV() + " - " + nv.getTenNV()));
        dgBUS.getAll().forEach(dg -> cboDocGia.addItem(dg.getMaDocGia() + " - " + dg.getTenDocGia()));
    }

    private void loadData() {
        model.setRowCount(0);
        for (Object[] row : pmBUS.getAllView()) {
            model.addRow(row);
        }
    }

    private void event() {
        // Sự kiện khi click vào dòng trong bảng để lấy dữ liệu lên form
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) return;

                txtMaPM.setText(model.getValueAt(row, 0).toString());
                
                // Set ComboBox Nhân viên (Tìm kiếm chuỗi chứa tên)
                String tenNV = model.getValueAt(row, 1).toString();
                for (int i = 0; i < cboNhanVien.getItemCount(); i++) {
                    if (cboNhanVien.getItemAt(i).contains(tenNV)) {
                        cboNhanVien.setSelectedIndex(i);
                        break;
                    }
                }

                // Set ComboBox Độc giả
                String tenDG = model.getValueAt(row, 2).toString();
                for (int i = 0; i < cboDocGia.getItemCount(); i++) {
                    if (cboDocGia.getItemAt(i).contains(tenDG)) {
                        cboDocGia.setSelectedIndex(i);
                        break;
                    }
                }

                txtNgayMuon.setText(model.getValueAt(row, 3).toString());
                
                // Set ComboBox Tình trạng
                String tinhTrang = model.getValueAt(row, 4).toString();
                cboTinhTrang.setSelectedItem(tinhTrang);
            }
        });

        // Nút Lập phiếu
        btnLapPhieu.addActionListener(e -> {
            try {
                int maNV = Integer.parseInt(cboNhanVien.getSelectedItem().toString().split(" - ")[0]);
                int maDG = Integer.parseInt(cboDocGia.getSelectedItem().toString().split(" - ")[0]);
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(txtNgayMuon.getText());
                
                if (pmBUS.lapPhieu(maNV, maDG, date)) {
                    JOptionPane.showMessageDialog(this, "Lập phiếu thành công");
                    loadData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: Kiểm tra lại dữ liệu và định dạng ngày (yyyy-MM-dd)");
            }
        });

        // Nút Sửa
        btnSua.addActionListener(e -> {
            if (txtMaPM.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu mượn từ bảng để sửa");
                return;
            }
            try {
                int maPM = Integer.parseInt(txtMaPM.getText());
                int maNV = Integer.parseInt(cboNhanVien.getSelectedItem().toString().split(" - ")[0]);
                int maDG = Integer.parseInt(cboDocGia.getSelectedItem().toString().split(" - ")[0]);
                int trangThai = cboTinhTrang.getSelectedIndex(); // 0: Đang mượn, 1: Đã trả
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(txtNgayMuon.getText());

                if (pmBUS.suaPhieu(maPM, maNV, maDG, date, trangThai)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật phiếu mượn thành công");
                    loadData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu!");
            }
        });

        // Nút Xóa
        btnXoa.addActionListener(e -> {
            if (txtMaPM.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu cần xóa");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phiếu này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int maPM = Integer.parseInt(txtMaPM.getText());
                if (pmBUS.xoaPhieu(maPM)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    btnLamMoi.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại (Có thể phiếu này đang chứa dữ liệu liên quan)");
                }
            }
        });

        // Nút Đánh dấu đã trả
        btnDaTra.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu mượn trên bảng");
                return;
            }
            int maPM = Integer.parseInt(model.getValueAt(row, 0).toString());
            if(pmBUS.danhDauDaTra(maPM)) {
                JOptionPane.showMessageDialog(this, "Đã cập nhật trạng thái đã trả");
                loadData();
            }
        });

        // Nút Làm mới
        btnLamMoi.addActionListener(e -> {
            txtMaPM.setText("");
            txtNgayMuon.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            cboNhanVien.setSelectedIndex(0);
            cboDocGia.setSelectedIndex(0);
            cboTinhTrang.setSelectedIndex(0);
            loadData();
        });
    }
}