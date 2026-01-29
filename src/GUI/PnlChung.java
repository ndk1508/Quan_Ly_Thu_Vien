package GUI;

import BUS.KeSachBUS;
import BUS.LoaiBUS;
import BUS.NhaCungCapBUS; // Dùng lại file cũ bạn đã có
import DTO.KeSachDTO;
import DTO.LoaiDTO;
import DTO.NhaCungCapDTO; // Dùng lại file cũ bạn đã có

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PnlChung extends JPanel {

    // --- Components LOẠI ---
    private JTextField txtMaLoai, txtTenLoai;
    private JTable tblLoai;
    private DefaultTableModel modelLoai;
    private LoaiBUS loaiBUS = new LoaiBUS();

    // --- Components NCC ---
    private JTextField txtMaNCC, txtTenNCC;
    private JTable tblNCC;
    private DefaultTableModel modelNCC;
    private NhaCungCapBUS nccBUS = new NhaCungCapBUS();

    // --- Components KỆ SÁCH ---
    private JTextField txtMaKe, txtTenKe;
    private JTable tblKe;
    private DefaultTableModel modelKe;
    private KeSachBUS keBUS = new KeSachBUS();

    // MAIN ĐỂ CHẠY THỬ
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Chung");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(100, 100, 1100, 750);
            frame.setContentPane(new PnlChung());
            frame.setVisible(true);
        });
    }

    public PnlChung() {
        setLayout(null); // Dùng null layout để đặt vị trí chính xác như ảnh
        setSize(1100, 750);

        // 1. PANEL LOẠI (Góc Trái Trên)
        add(createPanelLoai(10, 10));

        // 2. PANEL NHÀ CUNG CẤP (Góc Phải Trên)
        add(createPanelNCC(550, 10));

        // 3. PANEL KỆ SÁCH (Ở Giữa Dưới)
        add(createPanelKe(280, 360));

        // Load dữ liệu
        loadDataLoai();
        loadDataNCC();
        loadDataKe();
    }

    // --- HÀM TẠO PANEL LOẠI ---
    private JPanel createPanelLoai(int x, int y) {
        JPanel pnl = new JPanel(null);
        pnl.setBorder(new TitledBorder("Loại"));
        pnl.setBounds(x, y, 520, 340);

        JLabel lblTen = new JLabel("Tên Loại");
        lblTen.setBounds(20, 30, 80, 25);
        pnl.add(lblTen);
        txtTenLoai = new JTextField();
        txtTenLoai.setBounds(90, 30, 400, 25);
        pnl.add(txtTenLoai);
        
        txtMaLoai = new JTextField(); txtMaLoai.setVisible(false); pnl.add(txtMaLoai);

        JButton btnThem = new JButton("Thêm"); btnThem.setBounds(20, 70, 100, 30);
        JButton btnSua = new JButton("Sửa"); btnSua.setBounds(150, 70, 100, 30);
        JButton btnXoa = new JButton("Xoá"); btnXoa.setBounds(280, 70, 100, 30);
        pnl.add(btnThem); pnl.add(btnSua); pnl.add(btnXoa);

        String[] headers = {"Mã Loại", "Tên Loại"};
        modelLoai = new DefaultTableModel(headers, 0);
        tblLoai = new JTable(modelLoai);
        JScrollPane sc = new JScrollPane(tblLoai);
        sc.setBounds(10, 120, 500, 210);
        pnl.add(sc);

        // Sự kiện
        tblLoai.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblLoai.getSelectedRow();
                if(row >=0) {
                    txtMaLoai.setText(tblLoai.getValueAt(row, 0).toString());
                    txtTenLoai.setText(tblLoai.getValueAt(row, 1).toString());
                }
            }
        });
        btnThem.addActionListener(e -> {
            if(loaiBUS.themLoai(new LoaiDTO(0, txtTenLoai.getText()))) { loadDataLoai(); txtTenLoai.setText(""); }
        });
        btnSua.addActionListener(e -> {
            if(!txtMaLoai.getText().isEmpty()) 
                if(loaiBUS.suaLoai(new LoaiDTO(Integer.parseInt(txtMaLoai.getText()), txtTenLoai.getText()))) loadDataLoai();
        });
        btnXoa.addActionListener(e -> {
            if(!txtMaLoai.getText().isEmpty()) 
                if(loaiBUS.xoaLoai(Integer.parseInt(txtMaLoai.getText()))) loadDataLoai();
        });
        return pnl;
    }

    // --- HÀM TẠO PANEL NCC ---
    private JPanel createPanelNCC(int x, int y) {
        JPanel pnl = new JPanel(null);
        pnl.setBorder(new TitledBorder("Nhà Cung Cấp"));
        pnl.setBounds(x, y, 520, 340);

        JLabel lblTen = new JLabel("Tên NCC");
        lblTen.setBounds(20, 30, 80, 25);
        pnl.add(lblTen);
        txtTenNCC = new JTextField();
        txtTenNCC.setBounds(90, 30, 400, 25);
        pnl.add(txtTenNCC);
        
        txtMaNCC = new JTextField(); txtMaNCC.setVisible(false); pnl.add(txtMaNCC);

        JButton btnThem = new JButton("Thêm"); btnThem.setBounds(20, 70, 100, 30);
        JButton btnSua = new JButton("Sửa"); btnSua.setBounds(150, 70, 100, 30);
        JButton btnXoa = new JButton("Xoá"); btnXoa.setBounds(280, 70, 100, 30);
        pnl.add(btnThem); pnl.add(btnSua); pnl.add(btnXoa);

        String[] headers = {"Mã NCC", "Tên Nhà Cung Cấp"}; // Rút gọn cột hiển thị
        modelNCC = new DefaultTableModel(headers, 0);
        tblNCC = new JTable(modelNCC);
        JScrollPane sc = new JScrollPane(tblNCC);
        sc.setBounds(10, 120, 500, 210);
        pnl.add(sc);

        // Sự kiện
        tblNCC.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblNCC.getSelectedRow();
                if(row >=0) {
                    txtMaNCC.setText(tblNCC.getValueAt(row, 0).toString());
                    txtTenNCC.setText(tblNCC.getValueAt(row, 1).toString());
                }
            }
        });
        btnThem.addActionListener(e -> {
            NhaCungCapDTO ncc = new NhaCungCapDTO(0, txtTenNCC.getText(), "Chưa cập nhật");
            if(nccBUS.themNCC(ncc)) { loadDataNCC(); txtTenNCC.setText(""); }
        });
        btnSua.addActionListener(e -> {
            if(!txtMaNCC.getText().isEmpty()) {
                NhaCungCapDTO ncc = new NhaCungCapDTO(Integer.parseInt(txtMaNCC.getText()), txtTenNCC.getText(), "Chưa cập nhật");
                if(nccBUS.suaNCC(ncc)) loadDataNCC();
            }
        });
        btnXoa.addActionListener(e -> {
            if(!txtMaNCC.getText().isEmpty()) 
                if(nccBUS.xoaNCC(Integer.parseInt(txtMaNCC.getText()))) loadDataNCC();
        });
        return pnl;
    }

    // --- HÀM TẠO PANEL KỆ SÁCH ---
    private JPanel createPanelKe(int x, int y) {
        JPanel pnl = new JPanel(null);
        pnl.setBorder(new TitledBorder("Kệ Sách"));
        pnl.setBounds(x, y, 520, 340);

        JLabel lblTen = new JLabel("Tên Kệ");
        lblTen.setBounds(20, 30, 80, 25);
        pnl.add(lblTen);
        txtTenKe = new JTextField();
        txtTenKe.setBounds(90, 30, 400, 25);
        pnl.add(txtTenKe);
        
        txtMaKe = new JTextField(); txtMaKe.setVisible(false); pnl.add(txtMaKe);

        JButton btnThem = new JButton("Thêm"); btnThem.setBounds(20, 70, 100, 30);
        JButton btnSua = new JButton("Sửa"); btnSua.setBounds(150, 70, 100, 30);
        JButton btnXoa = new JButton("Xoá"); btnXoa.setBounds(280, 70, 100, 30);
        pnl.add(btnThem); pnl.add(btnSua); pnl.add(btnXoa);

        String[] headers = {"Mã Kệ", "Tên Kệ"};
        modelKe = new DefaultTableModel(headers, 0);
        tblKe = new JTable(modelKe);
        JScrollPane sc = new JScrollPane(tblKe);
        sc.setBounds(10, 120, 500, 210);
        pnl.add(sc);

        // Sự kiện
        tblKe.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblKe.getSelectedRow();
                if(row >=0) {
                    txtMaKe.setText(tblKe.getValueAt(row, 0).toString());
                    txtTenKe.setText(tblKe.getValueAt(row, 1).toString());
                }
            }
        });
        btnThem.addActionListener(e -> {
            if(keBUS.themKe(new KeSachDTO(0, txtTenKe.getText()))) { loadDataKe(); txtTenKe.setText(""); }
        });
        btnSua.addActionListener(e -> {
            if(!txtMaKe.getText().isEmpty()) 
                if(keBUS.suaKe(new KeSachDTO(Integer.parseInt(txtMaKe.getText()), txtTenKe.getText()))) loadDataKe();
        });
        btnXoa.addActionListener(e -> {
            if(!txtMaKe.getText().isEmpty()) 
                if(keBUS.xoaKe(Integer.parseInt(txtMaKe.getText()))) loadDataKe();
        });
        return pnl;
    }

    // --- CÁC HÀM LOAD DỮ LIỆU ---
    private void loadDataLoai() {
        modelLoai.setRowCount(0);
        for(LoaiDTO s : loaiBUS.getAllLoai()) 
            modelLoai.addRow(new Object[]{s.getMaLoai(), s.getTenLoai()});
    }
    private void loadDataNCC() {
        modelNCC.setRowCount(0);
        for(NhaCungCapDTO s : nccBUS.getDanhSachNCC()) 
            modelNCC.addRow(new Object[]{s.getMaNCC(), s.getTenNCC()});
    }
    private void loadDataKe() {
        modelKe.setRowCount(0);
        for(KeSachDTO s : keBUS.getAllKe()) 
            modelKe.addRow(new Object[]{s.getMaKe(), s.getTenKe()});
    }
}