package GUI;

import BUS.SachBUS;
import DTO.SachDTO;
import BUS.NhaXuatBanBUS;
import DTO.NhaXuatBanDTO;
import BUS.TacGiaBUS;
import DTO.TacGiaDTO;
import BUS.LoaiBUS;
import DTO.LoaiDTO;
import BUS.KeSachBUS;
import DTO.KeSachDTO;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class PnlSach extends JPanel {

    private JTextField txtMaSach, txtTenSach, txtNamXB, txtSoLuong, txtTimKiem;
    private JComboBox<String> cboLoai, cboNXB, cboTacGia, cboKeSach;
    private JLabel lblHinhAnh;
    private JTable table;
    private DefaultTableModel tableModel;
    private String strTenAnh = "no-image.png"; 

    // GỌI BUS
    SachBUS sachBUS = new SachBUS();
    NhaXuatBanBUS nxbBUS = new NhaXuatBanBUS();
    TacGiaBUS tgBUS = new TacGiaBUS();
    LoaiBUS loaiBUS = new LoaiBUS();
    KeSachBUS keBUS = new KeSachBUS();

    // HÀM MAIN ĐỂ CHẠY THỬ PANEL NÀY
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Test Panel Sách");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(100, 100, 1020, 750);
                
                // Tạo panel và nhúng vào frame
                PnlSach panel = new PnlSach();
                frame.setContentPane(panel);
                
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // CONSTRUCTOR
    public PnlSach() {
        setLayout(null);
        setSize(1000, 700); 
        
        // --- GIAO DIỆN ---
        JLabel lblTitle = new JLabel("QUẢN LÝ SÁCH");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0, 10, 980, 30);
        add(lblTitle);

        JLabel lblTenSach = new JLabel("Tên Sách:");
        lblTenSach.setBounds(50, 60, 100, 25);
        add(lblTenSach);
        txtTenSach = new JTextField();
        txtTenSach.setBounds(150, 60, 200, 25);
        add(txtTenSach);
        
        txtMaSach = new JTextField(); 
        txtMaSach.setVisible(false);
        add(txtMaSach);

        JLabel lblLoai = new JLabel("Mã Loại:");
        lblLoai.setBounds(50, 100, 100, 25);
        add(lblLoai);
        cboLoai = new JComboBox<>();
        cboLoai.setBounds(150, 100, 200, 25);
        add(cboLoai);

        JLabel lblNXB = new JLabel("Nhà Xuất Bản:");
        lblNXB.setBounds(50, 140, 100, 25);
        add(lblNXB);
        cboNXB = new JComboBox<>();
        cboNXB.setBounds(150, 140, 200, 25);
        add(cboNXB);

        JLabel lblTacGia = new JLabel("Tác Giả:");
        lblTacGia.setBounds(50, 180, 100, 25);
        add(lblTacGia);
        cboTacGia = new JComboBox<>();
        cboTacGia.setBounds(150, 180, 200, 25);
        add(cboTacGia);

        JLabel lblNamXB = new JLabel("Năm Xuất Bản:");
        lblNamXB.setBounds(400, 100, 100, 25);
        add(lblNamXB);
        txtNamXB = new JTextField();
        txtNamXB.setBounds(500, 100, 150, 25);
        add(txtNamXB);

        JLabel lblSoLuong = new JLabel("Số Lượng:");
        lblSoLuong.setBounds(400, 140, 100, 25);
        add(lblSoLuong);
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(500, 140, 150, 25);
        add(txtSoLuong);

        JLabel lblKeSach = new JLabel("Mã Kệ Sách:");
        lblKeSach.setBounds(400, 180, 100, 25);
        add(lblKeSach);
        cboKeSach = new JComboBox<>();
        cboKeSach.setBounds(500, 180, 150, 25);
        add(cboKeSach);

        JPanel pnlAnh = new JPanel();
        pnlAnh.setBorder(new TitledBorder(null, "Hình Ảnh", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlAnh.setBounds(700, 50, 200, 220);
        pnlAnh.setLayout(new BorderLayout());
        add(pnlAnh);
        lblHinhAnh = new JLabel("");
        lblHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);
        pnlAnh.add(lblHinhAnh, BorderLayout.CENTER);

        JButton btnChonAnh = new JButton("Chọn Ảnh");
        btnChonAnh.setBounds(500, 60, 100, 25);
        add(btnChonAnh);

        JButton btnThem = new JButton("Thêm");
        btnThem.setBounds(80, 240, 100, 35);
        add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.setBounds(200, 240, 100, 35);
        add(btnSua);

        JButton btnXoa = new JButton("Xoá");
        btnXoa.setBounds(320, 240, 100, 35);
        add(btnXoa);

        JButton btnTaiLai = new JButton("Tải Lại");
        btnTaiLai.setBounds(440, 240, 100, 35);
        add(btnTaiLai);

        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        lblTimKiem.setBounds(50, 300, 80, 25);
        add(lblTimKiem);
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(130, 300, 300, 25);
        add(txtTimKiem);
        JButton btnTim = new JButton("Tìm");
        btnTim.setBounds(450, 300, 80, 25);
        add(btnTim);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 350, 920, 280);
        add(scrollPane);

        String[] headers = {"Mã Sách", "Tên Sách", "Mã TG", "Mã NXB", "Mã Loại", "Năm XB", "Số Lượng", "Mã Kệ", "Ảnh"};
        tableModel = new DefaultTableModel(headers, 0);
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        // --- LOAD DỮ LIỆU BAN ĐẦU ---
        loadCombobox(); 
        loadDataToTable(sachBUS.getDanhSachSach()); 

        // --- SỰ KIỆN ---
        
        btnChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png"));
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                strTenAnh = file.getName();
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(lblHinhAnh.getWidth(), lblHinhAnh.getHeight(), Image.SCALE_SMOOTH);
                lblHinhAnh.setIcon(new ImageIcon(img));
            }
        });

        btnThem.addActionListener(e -> {
            try {
                SachDTO s = new SachDTO();
                s.setTenSach(txtTenSach.getText());
                s.setMaLoai(Integer.parseInt(getID(cboLoai)));
                s.setMaNXB(Integer.parseInt(getID(cboNXB)));
                s.setMaTacGia(Integer.parseInt(getID(cboTacGia)));
                s.setNamXB(Integer.parseInt(txtNamXB.getText()));
                s.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
                s.setMake(Integer.parseInt(getID(cboKeSach)));
                s.setHinhAnh(strTenAnh);

                if (sachBUS.themSach(s)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadDataToTable(sachBUS.getDanhSachSach());
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi nhập liệu: " + ex.getMessage());
            }
        });

        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1) { JOptionPane.showMessageDialog(null, "Chọn sách để sửa"); return; }
            try {
                SachDTO s = new SachDTO();
                s.setMaSach(Integer.parseInt(tableModel.getValueAt(row, 0).toString()));
                s.setTenSach(txtTenSach.getText());
                s.setMaLoai(Integer.parseInt(getID(cboLoai)));
                s.setMaNXB(Integer.parseInt(getID(cboNXB)));
                s.setMaTacGia(Integer.parseInt(getID(cboTacGia)));
                s.setNamXB(Integer.parseInt(txtNamXB.getText()));
                s.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
                s.setMake(Integer.parseInt(getID(cboKeSach)));
                s.setHinhAnh(strTenAnh);

                if (sachBUS.suaSach(s)) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    loadDataToTable(sachBUS.getDanhSachSach());
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1) return;
            if(JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa?") == JOptionPane.YES_OPTION) {
                int maSach = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                if (sachBUS.xoaSach(maSach)) {
                    loadDataToTable(sachBUS.getDanhSachSach());
                    JOptionPane.showMessageDialog(null, "Đã xóa!");
                }
            }
        });

        btnTim.addActionListener(e -> {
            String tuKhoa = txtTimKiem.getText();
            if(tuKhoa.trim().isEmpty()) {
                loadDataToTable(sachBUS.getDanhSachSach());
            } else {
                ArrayList<SachDTO> list = sachBUS.timKiemSach(tuKhoa);
                if(list.size() > 0) {
                    loadDataToTable(list);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sách nào!");
                    loadDataToTable(sachBUS.getDanhSachSach());
                }
            }
        });

        btnTaiLai.addActionListener(e -> {
            txtTenSach.setText(""); txtNamXB.setText(""); txtSoLuong.setText(""); txtTimKiem.setText("");
            lblHinhAnh.setIcon(null); 
            loadDataToTable(sachBUS.getDanhSachSach());
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaSach.setText(tableModel.getValueAt(row, 0).toString());
                    txtTenSach.setText(tableModel.getValueAt(row, 1).toString());
                    
                    setSelectedCombo(cboTacGia, tableModel.getValueAt(row, 2).toString());
                    setSelectedCombo(cboNXB, tableModel.getValueAt(row, 3).toString());
                    setSelectedCombo(cboLoai, tableModel.getValueAt(row, 4).toString());
                    
                    txtNamXB.setText(tableModel.getValueAt(row, 5).toString());
                    txtSoLuong.setText(tableModel.getValueAt(row, 6).toString());
                    setSelectedCombo(cboKeSach, tableModel.getValueAt(row, 7).toString());
                    
                    strTenAnh = tableModel.getValueAt(row, 8).toString();
                    ImageIcon icon = new ImageIcon("src/images/" + strTenAnh); 
                    if (icon.getIconWidth() > 0) {
                       Image img = icon.getImage().getScaledInstance(lblHinhAnh.getWidth(), lblHinhAnh.getHeight(), Image.SCALE_SMOOTH);
                       lblHinhAnh.setIcon(new ImageIcon(img));
                    } else lblHinhAnh.setIcon(null);
                }
            }
        });
    }

    // --- HÀM HỖ TRỢ GUI ---
    private void loadDataToTable(ArrayList<SachDTO> list) {
        tableModel.setRowCount(0);
        for (SachDTO s : list) {
            Vector<Object> vec = new Vector<>();
            vec.add(s.getMaSach());
            vec.add(s.getTenSach());
            vec.add(s.getMaTacGia()); 
            vec.add(s.getMaNXB());
            vec.add(s.getMaLoai());
            vec.add(s.getNamXB());   
            vec.add(s.getSoLuong());
            vec.add(s.getMake());    
            vec.add(s.getHinhAnh());
            tableModel.addRow(vec);
        }
    }

    private void loadCombobox() {
        // 1. Load LOẠI SÁCH (Dữ liệu thật)
        cboLoai.removeAllItems();
        ArrayList<LoaiDTO> listLoai = loaiBUS.getAllLoai();
        for (LoaiDTO loai : listLoai) {
            cboLoai.addItem(loai.getMaLoai() + "-" + loai.getTenLoai());
        }

        // 2. Load NHÀ XUẤT BẢN (Dữ liệu thật)
        cboNXB.removeAllItems(); 
        ArrayList<NhaXuatBanDTO> listNXB = nxbBUS.getDanhSachNXB();
        for (NhaXuatBanDTO nxb : listNXB) {
            cboNXB.addItem(nxb.getMaNXB() + "-" + nxb.getTenNXB());
        }

        // 3. Load TÁC GIẢ (Dữ liệu thật)
        cboTacGia.removeAllItems();
        ArrayList<TacGiaDTO> listTG = tgBUS.getDanhSachTacGia();
        for (TacGiaDTO tg : listTG) {
            cboTacGia.addItem(tg.getMaTacGia() + "-" + tg.getTenTacGia());
        }

        // 4. Load KỆ SÁCH (Dữ liệu thật)
        cboKeSach.removeAllItems();
        ArrayList<KeSachDTO> listKe = keBUS.getAllKe();
        for (KeSachDTO ke : listKe) {
            cboKeSach.addItem(ke.getMaKe() + "-" + ke.getTenKe());
        }
    }

    private String getID(JComboBox<String> cbo) {
        if(cbo.getSelectedItem() == null) return "0";
        return cbo.getSelectedItem().toString().split("-")[0];
    }
    
    private void setSelectedCombo(JComboBox<String> cbo, String id) {
        if(id == null) return;
        for (int i = 0; i < cbo.getItemCount(); i++) {
            if (cbo.getItemAt(i).startsWith(id + "-")) {
                cbo.setSelectedIndex(i);
                break;
            }
        }
    }
}