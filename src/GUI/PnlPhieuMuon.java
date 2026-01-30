package GUI;

import BUS.PhieuMuonBUS;
import BUS.NhanVienBUS;
import BUS.DocGiaBUS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Lớp hỗ trợ để lưu trữ ID và Tên trong JComboBox
 */
class ComboItem {
    private Object id; 
    private String name;

    public ComboItem(Object id, String name) {
        this.id = id;
        this.name = name;
    }

    public Object getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name; 
    }
}

public class PnlPhieuMuon extends JPanel {

    private JTextField txtMaPM, txtNgayMuon, txtTimKiem;
    private JComboBox<ComboItem> cboNhanVien, cboDocGia; 
    private JComboBox<String> cboTinhTrang;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnLapPhieu, btnSua, btnXoa, btnDaTra, btnLamMoi, btnTimKiem;

    private PhieuMuonBUS pmBUS = new PhieuMuonBUS();
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private DocGiaBUS dgBUS = new DocGiaBUS();

    public PnlPhieuMuon() {
        setLayout(new BorderLayout(10, 10));
        
        // --- Vùng NORTH (Form + Tìm kiếm) ---
        JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
        
        initFormPanel(); // Phương thức này giờ sẽ add vào pnlNorth thay vì add trực tiếp vào PnlPhieuMuon
        pnlNorth.add(initFormPanel(), BorderLayout.CENTER);
        pnlNorth.add(initSearchPanel(), BorderLayout.SOUTH);
        
        add(pnlNorth, BorderLayout.NORTH);
        
        initTable();    
        initButton();   
        loadCombo();    
        loadData(pmBUS.getAllView()); // Ban đầu load tất cả
        event();        
    }

    private JPanel initFormPanel() {
        JPanel pnl = new JPanel(new GridLayout(3, 4, 10, 10));
        pnl.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu mượn"));

        txtMaPM = new JTextField();
        txtMaPM.setEditable(false); 
        txtMaPM.setBackground(new Color(230, 230, 230));

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

        return pnl;
    }

    private JPanel initSearchPanel() {
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnl.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        
        txtTimKiem = new JTextField(25);
        btnTimKiem = new JButton("Tìm kiếm");
        
        pnl.add(new JLabel("Nhập mã PM, tên NV hoặc tên độc giả: "));
        pnl.add(txtTimKiem);
        pnl.add(btnTimKiem);
        
        return pnl;
    }

    private void initTable() {
        model = new DefaultTableModel(
            new String[]{"Mã PM", "Nhân viên", "Độc giả", "Ngày mượn", "Tình trạng"}, 0
        );
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        nvBUS.getAll().forEach(nv -> cboNhanVien.addItem(new ComboItem(nv.getMaNV(), nv.getTenNV())));
        dgBUS.getAll().forEach(dg -> cboDocGia.addItem(new ComboItem(dg.getMaDocGia(), dg.getTenDocGia())));
    }

    private void loadData(ArrayList<Object[]> list) {
        model.setRowCount(0);
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    private void event() {
        // TÌM KIẾM
        btnTimKiem.addActionListener(e -> {
            String keyword = txtTimKiem.getText().trim();
            if (keyword.isEmpty()) {
                loadData(pmBUS.getAllView());
            } else {
                loadData(pmBUS.timKiem(keyword));
            }
        });

        txtTimKiem.addActionListener(e -> btnTimKiem.doClick());

        // CLICK BẢNG
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) return;
                txtMaPM.setText(model.getValueAt(row, 0).toString());
                
                String tenNV = model.getValueAt(row, 1).toString();
                for (int i = 0; i < cboNhanVien.getItemCount(); i++) {
                    if (cboNhanVien.getItemAt(i).getName().equals(tenNV)) {
                        cboNhanVien.setSelectedIndex(i);
                        break;
                    }
                }

                String tenDG = model.getValueAt(row, 2).toString();
                for (int i = 0; i < cboDocGia.getItemCount(); i++) {
                    if (cboDocGia.getItemAt(i).getName().equals(tenDG)) {
                        cboDocGia.setSelectedIndex(i);
                        break;
                    }
                }
                txtNgayMuon.setText(model.getValueAt(row, 3).toString());
                cboTinhTrang.setSelectedItem(model.getValueAt(row, 4).toString());
            }
        });

        // LẬP PHIẾU
        btnLapPhieu.addActionListener(e -> {
            try {
                int maNV = Integer.parseInt(((ComboItem) cboNhanVien.getSelectedItem()).getId().toString());
                int maDG = Integer.parseInt(((ComboItem) cboDocGia.getSelectedItem()).getId().toString());
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(txtNgayMuon.getText());
                if (pmBUS.lapPhieu(maNV, maDG, date)) {
                    loadData(pmBUS.getAllView());
                    JOptionPane.showMessageDialog(this, "Thành công");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày!");
            }
        });

        // SỬA
        btnSua.addActionListener(e -> {
            if (txtMaPM.getText().isEmpty()) return;
            try {
                int maPM = Integer.parseInt(txtMaPM.getText());
                int maNV = Integer.parseInt(((ComboItem) cboNhanVien.getSelectedItem()).getId().toString());
                int maDG = Integer.parseInt(((ComboItem) cboDocGia.getSelectedItem()).getId().toString());
                int trangThai = cboTinhTrang.getSelectedIndex(); 
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(txtNgayMuon.getText());

                if (pmBUS.suaPhieu(maPM, maNV, maDG, date, trangThai)) {
                    loadData(pmBUS.getAllView());
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu!");
            }
        });

        // XÓA
        btnXoa.addActionListener(e -> {
            if (txtMaPM.getText().isEmpty()) return;
            if (JOptionPane.showConfirmDialog(this, "Xóa phiếu này?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (pmBUS.xoaPhieu(Integer.parseInt(txtMaPM.getText()))) {
                    loadData(pmBUS.getAllView());
                    btnLamMoi.doClick();
                }
            }
        });

        // ĐÁNH DẤU ĐÃ TRẢ
        btnDaTra.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;
            int maPM = Integer.parseInt(model.getValueAt(row, 0).toString());
            if(pmBUS.danhDauDaTra(maPM)) loadData(pmBUS.getAllView());
        });

        // LÀM MỚI
        btnLamMoi.addActionListener(e -> {
            txtMaPM.setText("");
            txtTimKiem.setText("");
            txtNgayMuon.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if (cboNhanVien.getItemCount() > 0) cboNhanVien.setSelectedIndex(0);
            if (cboDocGia.getItemCount() > 0) cboDocGia.setSelectedIndex(0);
            cboTinhTrang.setSelectedIndex(0);
            loadData(pmBUS.getAllView());
        });
    }
}