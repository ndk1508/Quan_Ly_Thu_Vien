package GUI;

import BUS.PhieuMuonBUS;
import BUS.NhanVienBUS;
import BUS.DocGiaBUS;
import BUS.SachBUS; // Giả định bạn đã có lớp này

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
    public String toString() { return name; }
}

public class PnlPhieuMuon extends JPanel {
	private JTextField txtMaPM, txtNgayMuon, txtHanTra, txtTimKiem; 
    // Components cho Thông tin chung (Bên trái)
    private JComboBox<ComboItem> cboNhanVien, cboDocGia;
    private JComboBox<String> cboTinhTrang;

    // Components cho Chọn sách (Bên phải)
    private JComboBox<ComboItem> cboSach;
    private JTable tableCT; 
    private DefaultTableModel modelCT;
    private JButton btnThemSach, btnXoaSach;

    // Bảng danh sách phiếu mượn (Phía dưới)
    private JTable table;
    private DefaultTableModel model;

    // Nút chức năng
    private JButton btnLapPhieu, btnSua, btnXoa, btnDaTra, btnLamMoi, btnTimKiem;

    // Business Logic Layers
    private PhieuMuonBUS pmBUS = new PhieuMuonBUS();
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private DocGiaBUS dgBUS = new DocGiaBUS();
    private SachBUS sBUS = new SachBUS(); // Cần khởi tạo SachBUS

    public PnlPhieuMuon() {
        setLayout(new BorderLayout(10, 10));

        // 1. Vùng Nhập Liệu & Chọn Sách (Phần TRÊN)
        JPanel pnlInputWrap = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlInputWrap.add(initInfoPanel());
        pnlInputWrap.add(initSachPanel());

        // 2. Vùng Tìm Kiếm & Bảng Phiếu Mượn (Phần DƯỚI)
        JPanel pnlTableWrap = new JPanel(new BorderLayout(5, 5));
        pnlTableWrap.add(initSearchPanel(), BorderLayout.NORTH);
        
        model = new DefaultTableModel(new String[]{"Mã PM", "Nhân viên", "Độc giả", "Ngày mượn", "Tình trạng"}, 0);
        table = new JTable(model);
        pnlTableWrap.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- SỬ DỤNG JSPLITPANE ĐỂ CHIA TỈ LỆ ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnlInputWrap, pnlTableWrap);
        splitPane.setDividerLocation(300); // Chiều cao phần trên là 300px
        splitPane.setResizeWeight(0.0);    // Khi phóng to cửa sổ, phần dưới (danh sách) sẽ tự động dãn ra thêm
        splitPane.setContinuousLayout(true);
        splitPane.setOneTouchExpandable(true); // Thêm nút thu nhanh

        // Thêm vào Center của BorderLayout chính
        add(splitPane, BorderLayout.CENTER);
        
        // Nút bấm ở dưới cùng
        add(initButtonPanel(), BorderLayout.SOUTH);

        // Khởi tạo dữ liệu
        loadCombo();
        loadData(pmBUS.getAllView());
        event();
    }

    private JPanel initInfoPanel() {
        // --- 1. KHỞI TẠO CÁC COMPONENT TRƯỚC (QUAN TRỌNG) ---
        if (txtMaPM == null) txtMaPM = new JTextField();
        txtMaPM.setEditable(false);
        txtMaPM.setBackground(new Color(230, 230, 230));

        if (txtNgayMuon == null) txtNgayMuon = new JTextField();
        txtNgayMuon.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        if (txtHanTra == null) txtHanTra = new JTextField();
        txtHanTra.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        if (cboNhanVien == null) cboNhanVien = new JComboBox<>();
        if (cboDocGia == null) cboDocGia = new JComboBox<>();
        if (cboTinhTrang == null) cboTinhTrang = new JComboBox<>(new String[]{"Đang mượn", "Đã trả"});

        // --- 2. THIẾT LẬP LAYOUT ---
        JPanel pnl = new JPanel(new GridBagLayout());
        pnl.setBorder(BorderFactory.createTitledBorder("1. Thông tin phiếu mượn"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.NORTH;

        // Hàng 0: Mã phiếu
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        pnl.add(new JLabel("Mã phiếu:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pnl.add(txtMaPM, gbc);

        // Hàng 1: Nhân viên
        gbc.gridx = 0; gbc.gridy = 1;
        pnl.add(new JLabel("Nhân viên:"), gbc);
        gbc.gridx = 1;
        pnl.add(cboNhanVien, gbc);

        // Hàng 2: Độc giả
        gbc.gridx = 0; gbc.gridy = 2;
        pnl.add(new JLabel("Độc giả:"), gbc);
        gbc.gridx = 1;
        pnl.add(cboDocGia, gbc);

        // Hàng 3: Ngày mượn
        gbc.gridx = 0; gbc.gridy = 3;
        pnl.add(new JLabel("Ngày mượn:"), gbc);
        gbc.gridx = 1;
        pnl.add(txtNgayMuon, gbc);

        // Hàng 4: Hạn trả
        gbc.gridx = 0; gbc.gridy = 4;
        pnl.add(new JLabel("Hạn trả:"), gbc);
        gbc.gridx = 1;
        pnl.add(txtHanTra, gbc);

        // Hàng 5: Tình trạng
        gbc.gridx = 0; gbc.gridy = 5;
        pnl.add(new JLabel("Tình trạng:"), gbc);
        gbc.gridx = 1;
        pnl.add(cboTinhTrang, gbc);

        // Thành phần giả đẩy lên trên
        gbc.gridx = 0; gbc.gridy = 6; gbc.weighty = 1.0;
        pnl.add(new JLabel(""), gbc);

        return pnl;
    }
    private JPanel initSachPanel() {
        JPanel pnl = new JPanel(new BorderLayout(5, 5));
        pnl.setBorder(BorderFactory.createTitledBorder("2. Chọn sách mượn"));

        JPanel pnlPick = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cboSach = new JComboBox<>();
        btnThemSach = new JButton("Thêm sách");
        btnXoaSach = new JButton("Xóa dòng");
        pnlPick.add(new JLabel("Sách:"));
        pnlPick.add(cboSach);
        pnlPick.add(btnThemSach);
        pnlPick.add(btnXoaSach);

        modelCT = new DefaultTableModel(new String[]{"Mã Sách", "Tên Sách", "Ngày trả dự kiến", "Ghi chú"}, 0);
        tableCT = new JTable(modelCT);

        pnl.add(pnlPick, BorderLayout.NORTH);
        pnl.add(new JScrollPane(tableCT), BorderLayout.CENTER);
        return pnl;
    }

    private JPanel initSearchPanel() {
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField(25);
        btnTimKiem = new JButton("Tìm kiếm");
        pnl.add(new JLabel("Tìm kiếm (Mã, Tên NV, Tên ĐG): "));
        pnl.add(txtTimKiem);
        pnl.add(btnTimKiem);
        return pnl;
    }

    private JPanel initButtonPanel() {
        JPanel pnl = new JPanel();
        btnLapPhieu = new JButton("Lập phiếu mượn");
        btnLapPhieu.setBackground(new Color(0, 153, 76));
        btnLapPhieu.setForeground(Color.WHITE);
        btnSua = new JButton("Sửa phiếu");
        btnXoa = new JButton("Xóa phiếu");
        btnDaTra = new JButton("Đánh dấu đã trả");
        btnLamMoi = new JButton("Làm mới");

        pnl.add(btnLapPhieu);
        pnl.add(btnSua);
        pnl.add(btnXoa);
        pnl.add(btnDaTra);
        pnl.add(btnLamMoi);
        return pnl;
    }

    private void loadCombo() {
        cboNhanVien.removeAllItems();
        cboDocGia.removeAllItems();
        cboSach.removeAllItems();
        
        nvBUS.getAll().forEach(nv -> cboNhanVien.addItem(new ComboItem(nv.getMaNV(), nv.getTenNV())));
        dgBUS.getAll().forEach(dg -> cboDocGia.addItem(new ComboItem(dg.getMaDocGia(), dg.getTenDocGia())));
        // Giả sử SachBUS có hàm getAll() trả về list SachDTO
        sBUS.getDanhSachSach().forEach(s -> cboSach.addItem(new ComboItem(s.getMaSach(), s.getTenSach())));
    }

    private void loadData(ArrayList<Object[]> list) {
        model.setRowCount(0);
        for (Object[] row : list) model.addRow(row);
    }

    private void event() {
        // --- SỰ KIỆN CHỌN SÁCH ---
        btnThemSach.addActionListener(e -> {
            ComboItem item = (ComboItem) cboSach.getSelectedItem();
            if (item == null) return;
            
            // Kiểm tra trùng sách trong bảng tạm
            for(int i=0; i<modelCT.getRowCount(); i++) {
                if(modelCT.getValueAt(i, 0).equals(item.getId())) {
                    JOptionPane.showMessageDialog(this, "Sách này đã có trong danh sách chọn!");
                    return;
                }
            }
            modelCT.addRow(new Object[]{item.getId(), item.getName(), "", ""});
        });

        btnXoaSach.addActionListener(e -> {
            int row = tableCT.getSelectedRow();
            if(row >= 0) modelCT.removeRow(row);
        });

        // --- CLICK BẢNG CHÍNH ĐỂ XEM CHI TIẾT ---
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) return;

                // 1. Hiển thị thông tin chung
                txtMaPM.setText(model.getValueAt(row, 0).toString());
                txtNgayMuon.setText(model.getValueAt(row, 3).toString());
                cboTinhTrang.setSelectedItem(model.getValueAt(row, 4).toString());

                String tenNV = model.getValueAt(row, 1).toString();
                for (int i = 0; i < cboNhanVien.getItemCount(); i++) 
                    if (cboNhanVien.getItemAt(i).getName().equals(tenNV)) { cboNhanVien.setSelectedIndex(i); break; }

                String tenDG = model.getValueAt(row, 2).toString();
                for (int i = 0; i < cboDocGia.getItemCount(); i++)
                    if (cboDocGia.getItemAt(i).getName().equals(tenDG)) { cboDocGia.setSelectedIndex(i); break; }

                // 2. Load danh sách sách của phiếu này vào bảng bên phải (tableCT)
                int maPM = Integer.parseInt(txtMaPM.getText());
                ArrayList<Object[]> dsChiTiet = pmBUS.getChiTiet(maPM); // Cần viết hàm này trong BUS/DAL
                modelCT.setRowCount(0);
                for(Object[] r : dsChiTiet) modelCT.addRow(r);
            }
        });

        // --- LẬP PHIẾU ---
        btnLapPhieu.addActionListener(e -> {
            try {
                int maNV = Integer.parseInt(((ComboItem) cboNhanVien.getSelectedItem()).getId().toString());
                int maDG = Integer.parseInt(((ComboItem) cboDocGia.getSelectedItem()).getId().toString());
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date ngayMuon = sdf.parse(txtNgayMuon.getText());
                Date ngayTra = sdf.parse(txtHanTra.getText()); // Lấy ngày trả từ ô mới thêm

                ArrayList<Object[]> dsSach = new ArrayList<>();
                for(int i=0; i<modelCT.getRowCount(); i++) {
                    dsSach.add(new Object[]{ modelCT.getValueAt(i, 0) });
                }

                // Truyền thêm ngayTra vào BUS
                if (pmBUS.lapPhieuFull(maNV, maDG, ngayMuon, ngayTra, dsSach)) {
                    loadData(pmBUS.getAllView());
                    JOptionPane.showMessageDialog(this, "Lập phiếu thành công!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày (yyyy-MM-dd)!");
            }
        });

        // --- CÁC SỰ KIỆN KHÁC GIỮ NGUYÊN ---
        btnTimKiem.addActionListener(e -> loadData(pmBUS.timKiem(txtTimKiem.getText().trim())));
        
        btnLamMoi.addActionListener(e -> {
            txtMaPM.setText("");
            txtTimKiem.setText("");
            modelCT.setRowCount(0);
            txtNgayMuon.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            loadData(pmBUS.getAllView());
        });

        btnXoa.addActionListener(e -> {
            if (txtMaPM.getText().isEmpty()) return;
            if (JOptionPane.showConfirmDialog(this, "Xóa phiếu này sẽ xóa cả chi tiết mượn sách. Bạn chắc chứ?", "Cảnh báo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (pmBUS.xoaPhieu(Integer.parseInt(txtMaPM.getText()))) {
                    loadData(pmBUS.getAllView());
                    btnLamMoi.doClick();
                }
            }
        });
    }
}