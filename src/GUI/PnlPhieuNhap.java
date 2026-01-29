package GUI;

import BUS.ChiTietPhieuNhapBUS;
import BUS.PhieuNhapBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.PhieuNhapDTO;
import BUS.SachBUS;
import DTO.SachDTO;
import BUS.NhanVienBUS; 
import DTO.NhanVienDTO; 
import BUS.NhaCungCapBUS; 
import DTO.NhaCungCapDTO; 

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PnlPhieuNhap extends JPanel {

    // --- PHẦN 1: PHIẾU NHẬP (UPPER) ---
    private JTextField txtMaPN, txtMaNV, txtMaNCC, txtNgayNhap;
    private JTable tblPhieuNhap;
    private DefaultTableModel modelPhieuNhap;

    // --- PHẦN 2: CHI TIẾT (LOWER) ---
    private JTextField txtMaSach, txtSoLuong, txtGiaNhap;
    private JTable tblChiTiet;
    private DefaultTableModel modelChiTiet;
    
    // --- KHAI BÁO BUS ---
    PhieuNhapBUS pnBUS = new PhieuNhapBUS();
    ChiTietPhieuNhapBUS ctpnBUS = new ChiTietPhieuNhapBUS();
    SachBUS sachBUS = new SachBUS();
    NhanVienBUS nvBUS = new NhanVienBUS(); // Code của bạn kia
    NhaCungCapBUS nccBUS = new NhaCungCapBUS();

    // Biến lưu mã phiếu nhập đang chọn
    private int selectedMaPN = -1;

    // Hàm Main để chạy thử riêng file này
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Quản Lý Phiếu Nhập");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(100, 100, 1000, 750);
                PnlPhieuNhap panel = new PnlPhieuNhap();
                frame.setContentPane(panel);
                frame.setVisible(true);
            } catch (Exception e) { e.printStackTrace(); }
        });
    }

    // CONSTRUCTOR
    public PnlPhieuNhap() {
        setLayout(null);
        setSize(1000, 750);

        // =========================================================================
        // PHẦN 1: QUẢN LÝ PHIẾU NHẬP (GIAO DIỆN)
        // =========================================================================
        JPanel pnlPN = new JPanel();
        pnlPN.setBorder(new TitledBorder(null, "Phiếu Nhập", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlPN.setBounds(10, 10, 400, 300);
        pnlPN.setLayout(null);
        add(pnlPN);

        JLabel lblMaNV = new JLabel("Mã Nhân Viên:");
        lblMaNV.setBounds(20, 30, 100, 25);
        pnlPN.add(lblMaNV);
        txtMaNV = new JTextField();
        txtMaNV.setBounds(120, 30, 150, 25);
        txtMaNV.setEditable(false); // Không cho nhập tay, bắt buộc chọn
        pnlPN.add(txtMaNV);
        JButton btnChonNV = new JButton("...");
        btnChonNV.setBounds(280, 30, 30, 25);
        pnlPN.add(btnChonNV);

        JLabel lblMaNCC = new JLabel("Mã NCC:");
        lblMaNCC.setBounds(20, 70, 100, 25);
        pnlPN.add(lblMaNCC);
        txtMaNCC = new JTextField();
        txtMaNCC.setBounds(120, 70, 150, 25);
        txtMaNCC.setEditable(false); // Không cho nhập tay
        pnlPN.add(txtMaNCC);
        JButton btnChonNCC = new JButton("...");
        btnChonNCC.setBounds(280, 70, 30, 25);
        pnlPN.add(btnChonNCC);

        JLabel lblNgay = new JLabel("Ngày Nhập:");
        lblNgay.setBounds(20, 110, 100, 25);
        pnlPN.add(lblNgay);
        txtNgayNhap = new JTextField();
        txtNgayNhap.setText(LocalDate.now().toString()); // Mặc định ngày hiện tại
        txtNgayNhap.setBounds(120, 110, 150, 25);
        pnlPN.add(txtNgayNhap);
        
        // Nút chức năng Phiếu Nhập
        JButton btnThemPN = new JButton("Thêm");
        btnThemPN.setBounds(20, 250, 80, 30);
        pnlPN.add(btnThemPN);
        
        JButton btnSuaPN = new JButton("Sửa");
        btnSuaPN.setBounds(110, 250, 80, 30);
        pnlPN.add(btnSuaPN);
        
        JButton btnXoaPN = new JButton("Xóa");
        btnXoaPN.setBounds(200, 250, 80, 30);
        pnlPN.add(btnXoaPN);
        
        JButton btnTaiLaiPN = new JButton("Tải Lại");
        btnTaiLaiPN.setBounds(290, 250, 80, 30);
        pnlPN.add(btnTaiLaiPN);

        // BẢNG PHIẾU NHẬP
        JScrollPane scrollPN = new JScrollPane();
        scrollPN.setBounds(420, 15, 550, 295);
        add(scrollPN);
        String[] headerPN = {"Mã PN", "Mã NV", "Mã NCC", "Ngày Nhập"};
        modelPhieuNhap = new DefaultTableModel(headerPN, 0);
        tblPhieuNhap = new JTable(modelPhieuNhap);
        scrollPN.setViewportView(tblPhieuNhap);

        // =========================================================================
        // PHẦN 2: CHI TIẾT PHIẾU NHẬP (GIAO DIỆN)
        // =========================================================================
        JPanel pnlCT = new JPanel();
        pnlCT.setBorder(new TitledBorder(null, "Chi Tiết Phiếu Nhập", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlCT.setBounds(10, 380, 400, 300);
        pnlCT.setLayout(null);
        add(pnlCT);

        JLabel lblMaSach = new JLabel("Mã Sách:");
        lblMaSach.setBounds(20, 30, 100, 25);
        pnlCT.add(lblMaSach);
        txtMaSach = new JTextField();
        txtMaSach.setBounds(120, 30, 150, 25);
        txtMaSach.setEditable(false);
        pnlCT.add(txtMaSach);
        JButton btnChonSach = new JButton("...");
        btnChonSach.setBounds(280, 30, 30, 25);
        pnlCT.add(btnChonSach);

        JLabel lblSoLuong = new JLabel("Số Lượng:");
        lblSoLuong.setBounds(20, 70, 100, 25);
        pnlCT.add(lblSoLuong);
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(120, 70, 150, 25);
        pnlCT.add(txtSoLuong);

        JLabel lblGia = new JLabel("Giá Nhập:");
        lblGia.setBounds(20, 110, 100, 25);
        pnlCT.add(lblGia);
        txtGiaNhap = new JTextField();
        txtGiaNhap.setBounds(120, 110, 150, 25);
        pnlCT.add(txtGiaNhap);

        JButton btnThemCT = new JButton("Thêm");
        btnThemCT.setBounds(20, 250, 80, 30);
        pnlCT.add(btnThemCT);
        JButton btnXoaCT = new JButton("Xóa");
        btnXoaCT.setBounds(200, 250, 80, 30);
        pnlCT.add(btnXoaCT);

        // BẢNG CHI TIẾT
        JScrollPane scrollCT = new JScrollPane();
        scrollCT.setBounds(420, 380, 550, 300);
        add(scrollCT);
        String[] headerCT = {"Mã CTPN", "Mã PN", "Mã Sách", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        modelChiTiet = new DefaultTableModel(headerCT, 0);
        tblChiTiet = new JTable(modelChiTiet);
        scrollCT.setViewportView(tblChiTiet);

        // --- LOAD DỮ LIỆU BAN ĐẦU ---
        loadPhieuNhap();

        // =========================================================================
        // XỬ LÝ SỰ KIỆN (CONTROLLER)
        // =========================================================================

        // ------------------------------------------
        // 1. SỰ KIỆN NÚT CHỌN NHÂN VIÊN (...)
        // ------------------------------------------
        btnChonNV.addActionListener(e -> {
            JPanel pnlChon = new JPanel(new BorderLayout());
            pnlChon.setPreferredSize(new Dimension(600, 400));
            
            // Tìm kiếm
            JPanel pnlTim = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JTextField txtTim = new JTextField(20);
            JButton btnTim = new JButton("Tìm Tên");
            pnlTim.add(new JLabel("Tên NV:")); pnlTim.add(txtTim); pnlTim.add(btnTim);
            pnlChon.add(pnlTim, BorderLayout.NORTH);

            // Bảng
            String[] headers = {"Mã NV", "Họ Tên", "SĐT", "Địa Chỉ"};
            DefaultTableModel modelChon = new DefaultTableModel(headers, 0);
            JTable tblChon = new JTable(modelChon);
            pnlChon.add(new JScrollPane(tblChon), BorderLayout.CENTER);

            // Load data (XỬ LÝ ĐẶC BIỆT CHO CODE BẠN KIA)
            Runnable loadData = () -> {
                modelChon.setRowCount(0);
                String kw = txtTim.getText().trim().toLowerCase();
                
                // Vì BUS bạn kia không có tìm kiếm, ta lấy hết về rồi tự lọc (Client-side filtering)
                ArrayList<NhanVienDTO> listAll = nvBUS.getAll(); // Gọi đúng hàm getAll() của bạn kia
                
                for (NhanVienDTO nv : listAll) {
                    // Kiểm tra điều kiện tìm kiếm và chỉ lấy NV đang hoạt động (TrangThai == 1)
                    if (nv.getTrangThai() == 1 && 
                       (kw.isEmpty() || nv.getTenNV().toLowerCase().contains(kw))) {
                        
                        modelChon.addRow(new Object[]{
                            nv.getMaNV(), // Lấy mã dạng String
                            nv.getTenNV(), 
                            nv.getSdt(), 
                            nv.getDiaChi()
                        });
                    }
                }
            };
            loadData.run();
            btnTim.addActionListener(evt -> loadData.run());
            txtTim.addActionListener(evt -> loadData.run());

            int result = JOptionPane.showConfirmDialog(null, pnlChon, "Chọn Nhân Viên", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                int row = tblChon.getSelectedRow();
                if (row >= 0) txtMaNV.setText(tblChon.getValueAt(row, 0).toString());
            }
        });

        // ------------------------------------------
        // 2. SỰ KIỆN NÚT CHỌN NHÀ CUNG CẤP (...)
        // ------------------------------------------
        btnChonNCC.addActionListener(e -> {
            JPanel pnlChon = new JPanel(new BorderLayout());
            pnlChon.setPreferredSize(new Dimension(500, 300));
            
            JPanel pnlTim = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JTextField txtTim = new JTextField(20);
            JButton btnTim = new JButton("Tìm Tên");
            pnlTim.add(new JLabel("Tên NCC:")); pnlTim.add(txtTim); pnlTim.add(btnTim);
            pnlChon.add(pnlTim, BorderLayout.NORTH);

            String[] headers = {"Mã NCC", "Tên Nhà Cung Cấp", "Địa Chỉ"};
            DefaultTableModel modelChon = new DefaultTableModel(headers, 0);
            JTable tblChon = new JTable(modelChon);
            pnlChon.add(new JScrollPane(tblChon), BorderLayout.CENTER);

            Runnable loadData = () -> {
                modelChon.setRowCount(0);
                String kw = txtTim.getText().trim();
                ArrayList<NhaCungCapDTO> list = kw.isEmpty() ? nccBUS.getDanhSachNCC() : nccBUS.timKiemNCC(kw);
                for (NhaCungCapDTO ncc : list) {
                    modelChon.addRow(new Object[]{ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi()});
                }
            };
            loadData.run();
            btnTim.addActionListener(evt -> loadData.run());
            txtTim.addActionListener(evt -> loadData.run());

            int result = JOptionPane.showConfirmDialog(null, pnlChon, "Chọn Nhà Cung Cấp", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                int row = tblChon.getSelectedRow();
                if (row >= 0) txtMaNCC.setText(tblChon.getValueAt(row, 0).toString());
            }
        });

        // ------------------------------------------
        // 3. SỰ KIỆN NÚT CHỌN SÁCH (...)
        // ------------------------------------------
        btnChonSach.addActionListener(e -> {
            JPanel pnlChon = new JPanel(new BorderLayout());
            pnlChon.setPreferredSize(new Dimension(600, 400));

            JPanel pnlTim = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JTextField txtTimSach = new JTextField(20);
            JButton btnTimSach = new JButton("Tìm Sách");
            pnlTim.add(new JLabel("Tên Sách:")); pnlTim.add(txtTimSach); pnlTim.add(btnTimSach);
            pnlChon.add(pnlTim, BorderLayout.NORTH);

            String[] headers = {"Mã Sách", "Tên Sách", "Tồn Kho"};
            DefaultTableModel modelChon = new DefaultTableModel(headers, 0);
            JTable tblChon = new JTable(modelChon);
            pnlChon.add(new JScrollPane(tblChon), BorderLayout.CENTER);

            Runnable loadData = () -> {
                modelChon.setRowCount(0);
                String kw = txtTimSach.getText().trim();
                ArrayList<SachDTO> list = kw.isEmpty() ? sachBUS.getDanhSachSach() : sachBUS.timKiemSach(kw);
                for (SachDTO s : list) {
                    modelChon.addRow(new Object[]{s.getMaSach(), s.getTenSach(), s.getSoLuong()});
                }
            };
            loadData.run();
            btnTimSach.addActionListener(evt -> loadData.run());
            txtTimSach.addActionListener(evt -> loadData.run());

            int result = JOptionPane.showConfirmDialog(null, pnlChon, "Chọn Sách Nhập Kho", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                int row = tblChon.getSelectedRow();
                if (row >= 0) {
                    txtMaSach.setText(tblChon.getValueAt(row, 0).toString());
                    txtSoLuong.requestFocus();
                }
            }
        });

        // ------------------------------------------
        // 4. SỰ KIỆN CLICK BẢNG PHIẾU NHẬP
        // ------------------------------------------
        tblPhieuNhap.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblPhieuNhap.getSelectedRow();
                if (row >= 0) {
                    selectedMaPN = Integer.parseInt(modelPhieuNhap.getValueAt(row, 0).toString());
                    txtMaNV.setText(modelPhieuNhap.getValueAt(row, 1).toString());
                    txtMaNCC.setText(modelPhieuNhap.getValueAt(row, 2).toString());
                    txtNgayNhap.setText(modelPhieuNhap.getValueAt(row, 3).toString());
                    
                    // Load bảng chi tiết tương ứng
                    loadChiTiet(selectedMaPN);
                }
            }
        });

        // ------------------------------------------
        // 5. CÁC NÚT THAO TÁC PHIẾU NHẬP (THÊM, XÓA, SỬA)
        // ------------------------------------------
        btnThemPN.addActionListener(e -> {
            try {
                if(txtMaNV.getText().isEmpty() || txtMaNCC.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn Nhân Viên và Nhà Cung Cấp!");
                    return;
                }
                PhieuNhapDTO pn = new PhieuNhapDTO();
                // SỬA: Mã Nhân Viên của bạn kia là String, nhưng trong bảng PhieuNhap có thể là int?
                // Nếu bảng PhieuNhap trong DB cột MaNV là INT -> Cần ép kiểu
                // Nếu bảng PhieuNhap trong DB cột MaNV là VARCHAR -> Giữ nguyên String
                // GIẢ SỬ CSDL của bạn cột MaNV là INT (như các bảng cũ), ta cần ép kiểu
                // Nếu MaNV là "NV01" thì sẽ lỗi -> Cần đảm bảo MaNV là số hoặc sửa DTO PhieuNhap thành String
                
                // Ở đây mình ép về int vì PhieuNhapDTO của bạn đang dùng int maNV
                // Nếu mã NV là chữ (VD: NV01) thì bạn cần sửa PhieuNhapDTO cột MaNV thành String luôn.
                pn.setMaNV(Integer.parseInt(txtMaNV.getText())); // Nếu mã NV là số
                
                pn.setMaNCC(Integer.parseInt(txtMaNCC.getText()));
                pn.setNgayNhap(Date.valueOf(txtNgayNhap.getText()));

                if (pnBUS.themPhieuNhap(pn)) {
                    JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thành công!");
                    loadPhieuNhap();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Lỗi: Mã Nhân Viên phải là số! (Hoặc sửa PhieuNhapDTO thành String)");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
            }
        });

        btnSuaPN.addActionListener(e -> {
            if(selectedMaPN == -1) {JOptionPane.showMessageDialog(null, "Chưa chọn phiếu!"); return;}
            try {
                PhieuNhapDTO pn = new PhieuNhapDTO();
                pn.setMaPN(selectedMaPN);
                pn.setMaNV(Integer.parseInt(txtMaNV.getText()));
                pn.setMaNCC(Integer.parseInt(txtMaNCC.getText()));
                pn.setNgayNhap(Date.valueOf(txtNgayNhap.getText()));
                
                if(pnBUS.suaPhieuNhap(pn)) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    loadPhieuNhap();
                }
            } catch(Exception ex) {ex.printStackTrace();}
        });

        btnXoaPN.addActionListener(e -> {
            if(selectedMaPN == -1) { JOptionPane.showMessageDialog(null, "Chọn phiếu để xóa!"); return; }
            if (JOptionPane.showConfirmDialog(null, "Xóa phiếu nhập sẽ xóa cả chi tiết. Tiếp tục?") == JOptionPane.YES_OPTION) {
                if(pnBUS.xoaPhieuNhap(selectedMaPN)) {
                    loadPhieuNhap();
                    modelChiTiet.setRowCount(0);
                    selectedMaPN = -1;
                }
            }
        });
        
        btnTaiLaiPN.addActionListener(e -> {
            txtMaNV.setText(""); txtMaNCC.setText(""); 
            loadPhieuNhap();
            modelChiTiet.setRowCount(0);
            selectedMaPN = -1;
        });

        // ------------------------------------------
        // 6. CÁC NÚT THAO TÁC CHI TIẾT (THÊM, XÓA)
        // ------------------------------------------
        btnThemCT.addActionListener(e -> {
            if(selectedMaPN == -1) { 
                JOptionPane.showMessageDialog(null, "Vui lòng chọn Phiếu Nhập ở bảng trên trước!"); 
                return; 
            }
            if(txtMaSach.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn Sách!");
                return;
            }
            try {
                ChiTietPhieuNhapDTO ct = new ChiTietPhieuNhapDTO();
                ct.setMaPN(selectedMaPN); 
                ct.setMaSach(Integer.parseInt(txtMaSach.getText()));
                ct.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
                ct.setDonGia(Double.parseDouble(txtGiaNhap.getText()));

                if (ctpnBUS.themChiTiet(ct)) {
                    JOptionPane.showMessageDialog(null, "Thêm chi tiết thành công!");
                    loadChiTiet(selectedMaPN);
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm chi tiết thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi nhập liệu: " + ex.getMessage());
            }
        });

        btnXoaCT.addActionListener(e -> {
            int row = tblChiTiet.getSelectedRow();
            if(row == -1) return;
            int maCTPN = Integer.parseInt(modelChiTiet.getValueAt(row, 0).toString());
            if (ctpnBUS.xoaChiTiet(maCTPN)) {
                loadChiTiet(selectedMaPN);
            }
        });
    }

    // --- CÁC HÀM HỖ TRỢ ---
    private void loadPhieuNhap() {
        modelPhieuNhap.setRowCount(0);
        ArrayList<PhieuNhapDTO> list = pnBUS.getAllPhieuNhap();
        for (PhieuNhapDTO pn : list) {
            Vector<Object> vec = new Vector<>();
            vec.add(pn.getMaPN());
            vec.add(pn.getMaNV());
            vec.add(pn.getMaNCC());
            vec.add(pn.getNgayNhap());
            modelPhieuNhap.addRow(vec);
        }
    }

    private void loadChiTiet(int maPN) {
        modelChiTiet.setRowCount(0);
        ArrayList<ChiTietPhieuNhapDTO> list = ctpnBUS.getChiTietByMaPN(maPN);
        for (ChiTietPhieuNhapDTO ct : list) {
            Vector<Object> vec = new Vector<>();
            vec.add(ct.getMaCTPN());
            vec.add(ct.getMaPN());
            vec.add(ct.getMaSach());
            vec.add(ct.getSoLuong());
            vec.add(ct.getDonGia());
            vec.add(ct.getThanhTien());
            modelChiTiet.addRow(vec);
        }
    }
}