package GUI;

import BUS.ChiTietPhieuNhapBUS;
import BUS.PhieuNhapBUS;
import DTO.ChiTietPhieuNhapDTO;
import DTO.PhieuNhapDTO;
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
    
    // BUS
    PhieuNhapBUS pnBUS = new PhieuNhapBUS();
    ChiTietPhieuNhapBUS ctpnBUS = new ChiTietPhieuNhapBUS();

    // Biến lưu mã phiếu nhập đang chọn
    private int selectedMaPN = -1;

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

    public PnlPhieuNhap() {
        setLayout(null);
        setSize(1000, 750);

        // ==========================
        // PHẦN 1: QUẢN LÝ PHIẾU NHẬP
        // ==========================
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
        pnlPN.add(txtMaNV);
        JButton btnChonNV = new JButton("...");
        btnChonNV.setBounds(280, 30, 30, 25);
        pnlPN.add(btnChonNV);

        JLabel lblMaNCC = new JLabel("Mã NCC:");
        lblMaNCC.setBounds(20, 70, 100, 25);
        pnlPN.add(lblMaNCC);
        txtMaNCC = new JTextField();
        txtMaNCC.setBounds(120, 70, 150, 25);
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

        // ==================================
        // PHẦN 2: CHI TIẾT PHIẾU NHẬP
        // ==================================
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

        // ===================
        // SỰ KIỆN (EVENTS)
        // ===================

        // 1. Click Bảng Phiếu Nhập -> Load Chi Tiết xuống dưới
        tblPhieuNhap.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblPhieuNhap.getSelectedRow();
                if (row >= 0) {
                    selectedMaPN = Integer.parseInt(modelPhieuNhap.getValueAt(row, 0).toString());
                    txtMaNV.setText(modelPhieuNhap.getValueAt(row, 1).toString());
                    txtMaNCC.setText(modelPhieuNhap.getValueAt(row, 2).toString());
                    txtNgayNhap.setText(modelPhieuNhap.getValueAt(row, 3).toString());
                    
                    // Load bảng chi tiết theo mã phiếu
                    loadChiTiet(selectedMaPN);
                }
            }
        });

        // 2. Thêm Phiếu Nhập
        btnThemPN.addActionListener(e -> {
            try {
                PhieuNhapDTO pn = new PhieuNhapDTO();
                pn.setMaNV(Integer.parseInt(txtMaNV.getText()));
                pn.setMaNCC(Integer.parseInt(txtMaNCC.getText()));
                pn.setNgayNhap(Date.valueOf(txtNgayNhap.getText())); // Định dạng YYYY-MM-DD

                if (pnBUS.themPhieuNhap(pn)) {
                    JOptionPane.showMessageDialog(null, "Thêm phiếu nhập thành công!");
                    loadPhieuNhap();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
            }
        });

        // 3. Xóa Phiếu Nhập
        btnXoaPN.addActionListener(e -> {
            if(selectedMaPN == -1) { JOptionPane.showMessageDialog(null, "Chọn phiếu để xóa!"); return; }
            if (JOptionPane.showConfirmDialog(null, "Xóa phiếu nhập sẽ xóa cả chi tiết. Tiếp tục?") == JOptionPane.YES_OPTION) {
                // Lưu ý: Trong thực tế nên xóa chi tiết trước nếu DB không có ON DELETE CASCADE
                if(pnBUS.xoaPhieuNhap(selectedMaPN)) {
                    loadPhieuNhap();
                    modelChiTiet.setRowCount(0); // Xóa trắng bảng chi tiết
                    selectedMaPN = -1;
                }
            }
        });
        
        // 4. Tải lại Phiếu Nhập
        btnTaiLaiPN.addActionListener(e -> {
            txtMaNV.setText(""); txtMaNCC.setText(""); 
            loadPhieuNhap();
            modelChiTiet.setRowCount(0);
        });

        // 5. Thêm Chi Tiết (Sách vào phiếu)
        btnThemCT.addActionListener(e -> {
            if(selectedMaPN == -1) { JOptionPane.showMessageDialog(null, "Vui lòng chọn Phiếu Nhập ở bảng trên trước!"); return; }
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

        // 6. Xóa Chi Tiết
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