package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class SachForm extends JFrame {

    private JPanel contentPane;
    private JTextField txtMaSach, txtTenSach, txtNamXB, txtSoLuong, txtTimKiem;
    private JComboBox<String> cboLoai, cboNXB, cboTacGia, cboKeSach;
    private JLabel lblHinhAnh;
    private JTable table;
    private DefaultTableModel tableModel;
    private String strTenAnh = "no-image.png"; // Ảnh mặc định

    // === CẤU HÌNH KẾT NỐI MYSQL (Chuẩn theo máy bạn) ===
    String url = "jdbc:mysql://localhost:3306/ql_thuvien?useUnicode=true&characterEncoding=UTF-8";
    String user = "root";
    String pass = ""; 
    Connection conn = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SachForm frame = new SachForm();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SachForm() {
        setTitle("Quản Lý Sách - DB: ql_thuvien");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        
        // --- GIAO DIỆN (UI) ---
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("QUẢN LÝ SÁCH");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0, 10, 980, 30);
        contentPane.add(lblTitle);

        // Các Label và TextField bên trái
        JLabel lblTenSach = new JLabel("Tên Sách:");
        lblTenSach.setBounds(50, 60, 100, 25);
        contentPane.add(lblTenSach);

        txtTenSach = new JTextField();
        txtTenSach.setBounds(150, 60, 200, 25);
        contentPane.add(txtTenSach);
        
        txtMaSach = new JTextField(); 
        txtMaSach.setVisible(false); // Ẩn Mã Sách
        contentPane.add(txtMaSach);

        // Các Combobox
        JLabel lblLoai = new JLabel("Mã Loại:");
        lblLoai.setBounds(50, 100, 100, 25);
        contentPane.add(lblLoai);
        cboLoai = new JComboBox<>();
        cboLoai.setBounds(150, 100, 200, 25);
        contentPane.add(cboLoai);

        JLabel lblNXB = new JLabel("Nhà Xuất Bản:");
        lblNXB.setBounds(50, 140, 100, 25);
        contentPane.add(lblNXB);
        cboNXB = new JComboBox<>();
        cboNXB.setBounds(150, 140, 200, 25);
        contentPane.add(cboNXB);

        JLabel lblTacGia = new JLabel("Tác Giả:");
        lblTacGia.setBounds(50, 180, 100, 25);
        contentPane.add(lblTacGia);
        cboTacGia = new JComboBox<>();
        cboTacGia.setBounds(150, 180, 200, 25);
        contentPane.add(cboTacGia);

        // Các trường bên phải
        JLabel lblNamXB = new JLabel("Năm Xuất Bản:");
        lblNamXB.setBounds(400, 100, 100, 25);
        contentPane.add(lblNamXB);
        txtNamXB = new JTextField();
        txtNamXB.setBounds(500, 100, 150, 25);
        contentPane.add(txtNamXB);

        JLabel lblSoLuong = new JLabel("Số Lượng:");
        lblSoLuong.setBounds(400, 140, 100, 25);
        contentPane.add(lblSoLuong);
        txtSoLuong = new JTextField();
        txtSoLuong.setBounds(500, 140, 150, 25);
        contentPane.add(txtSoLuong);

        JLabel lblKeSach = new JLabel("Mã Kệ Sách:");
        lblKeSach.setBounds(400, 180, 100, 25);
        contentPane.add(lblKeSach);
        cboKeSach = new JComboBox<>();
        cboKeSach.setBounds(500, 180, 150, 25);
        contentPane.add(cboKeSach);

        // KHU VỰC HÌNH ẢNH
        JPanel pnlAnh = new JPanel();
        pnlAnh.setBorder(new TitledBorder(null, "Hình Ảnh", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        pnlAnh.setBounds(700, 50, 200, 220);
        pnlAnh.setLayout(new BorderLayout());
        contentPane.add(pnlAnh);

        lblHinhAnh = new JLabel("");
        lblHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);
        pnlAnh.add(lblHinhAnh, BorderLayout.CENTER);

        JButton btnChonAnh = new JButton("Chọn Ảnh");
        btnChonAnh.setBounds(500, 60, 100, 25);
        contentPane.add(btnChonAnh);

        // CÁC NÚT CHỨC NĂNG
        JButton btnThem = new JButton("Thêm");
        btnThem.setBounds(80, 240, 100, 35);
        contentPane.add(btnThem);

        JButton btnSua = new JButton("Sửa");
        btnSua.setBounds(200, 240, 100, 35);
        contentPane.add(btnSua);

        JButton btnXoa = new JButton("Xoá");
        btnXoa.setBounds(320, 240, 100, 35);
        contentPane.add(btnXoa);

        JButton btnTaiLai = new JButton("Tải Lại");
        btnTaiLai.setBounds(440, 240, 100, 35);
        contentPane.add(btnTaiLai);

        // TÌM KIẾM
        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        lblTimKiem.setBounds(50, 300, 80, 25);
        contentPane.add(lblTimKiem);
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(130, 300, 300, 25);
        contentPane.add(txtTimKiem);
        JButton btnTim = new JButton("Tìm");
        btnTim.setBounds(450, 300, 80, 25);
        contentPane.add(btnTim);

        // BẢNG DỮ LIỆU
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 350, 920, 280);
        contentPane.add(scrollPane);

        String[] headers = {"Mã Sách", "Tên Sách", "Mã TG", "Mã NXB", "Mã Loại", "Năm XB", "Số Lượng", "Mã Kệ", "Ảnh"};
        tableModel = new DefaultTableModel(headers, 0);
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

        // --- GỌI HÀM KHI CHẠY ---
        connectDB();
        loadCombobox(); 
        loadData();      

        // --- SỰ KIỆN (EVENTS) ---
        
        // 1. Nút Chọn Ảnh
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

        // 2. Nút Thêm (INSERT) - Cập nhật đúng tên cột DB
        btnThem.addActionListener(e -> {
            try {
                if (conn == null) { JOptionPane.showMessageDialog(null, "Chưa kết nối DB!"); return; }
                
                // SQL chuẩn theo bảng 'sach' của bạn: NamXB, Make
                String sql = "INSERT INTO sach(TenSach, MaLoai, MaNXB, MaTacGia, NamXB, SoLuong, Make, HinhAnh) VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txtTenSach.getText());
                ps.setInt(2, Integer.parseInt(getID(cboLoai)));   // Chuyển String "3-..." thành int 3
                ps.setInt(3, Integer.parseInt(getID(cboNXB)));
                ps.setInt(4, Integer.parseInt(getID(cboTacGia)));
                ps.setInt(5, Integer.parseInt(txtNamXB.getText())); // Cột NamXB
                ps.setInt(6, Integer.parseInt(txtSoLuong.getText()));
                ps.setInt(7, Integer.parseInt(getID(cboKeSach))); // Cột Make
                ps.setString(8, strTenAnh);
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Thêm thành công!");
                loadData();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi thêm: " + ex.getMessage());
            }
        });

        // 3. Nút Sửa (UPDATE)
        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1) { JOptionPane.showMessageDialog(null, "Chọn sách để sửa"); return; }
            try {
                String maSach = tableModel.getValueAt(row, 0).toString();
                // SQL chuẩn theo tên cột trong ảnh
                String sql = "UPDATE sach SET TenSach=?, MaLoai=?, MaNXB=?, MaTacGia=?, NamXB=?, SoLuong=?, Make=?, HinhAnh=? WHERE MaSach=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txtTenSach.getText());
                ps.setInt(2, Integer.parseInt(getID(cboLoai)));
                ps.setInt(3, Integer.parseInt(getID(cboNXB)));
                ps.setInt(4, Integer.parseInt(getID(cboTacGia)));
                ps.setInt(5, Integer.parseInt(txtNamXB.getText()));
                ps.setInt(6, Integer.parseInt(txtSoLuong.getText()));
                ps.setInt(7, Integer.parseInt(getID(cboKeSach)));
                ps.setString(8, strTenAnh);
                ps.setString(9, maSach);
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                loadData();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi sửa: " + ex.getMessage());
            }
        });

        // 4. Nút Xóa (DELETE)
        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1) return;
            if(JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa?") == JOptionPane.YES_OPTION) {
                try {
                    String maSach = tableModel.getValueAt(row, 0).toString();
                    String sql = "DELETE FROM sach WHERE MaSach=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, maSach);
                    ps.executeUpdate();
                    loadData();
                    JOptionPane.showMessageDialog(null, "Đã xóa!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 5. Nút Tải lại
        btnTaiLai.addActionListener(e -> {
            txtTenSach.setText(""); txtNamXB.setText(""); txtSoLuong.setText("");
            lblHinhAnh.setIcon(null); loadData();
        });

        // 6. Sự kiện Click Bảng
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
                    
                    String anh = tableModel.getValueAt(row, 8).toString();
                    strTenAnh = anh;
                    // Đường dẫn ảnh (Bạn cần đảm bảo file ảnh tồn tại trong folder src/images/)
                    ImageIcon icon = new ImageIcon("src/images/" + anh); 
                    if (icon.getIconWidth() > 0) {
                       Image img = icon.getImage().getScaledInstance(lblHinhAnh.getWidth(), lblHinhAnh.getHeight(), Image.SCALE_SMOOTH);
                       lblHinhAnh.setIcon(new ImageIcon(img));
                    } else {
                        lblHinhAnh.setIcon(null); 
                    }
                }
            }
        });
    }

    // --- CÁC HÀM HỖ TRỢ ---

    private void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Kết nối ql_thuvien thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối: " + e.getMessage());
        }
    }

    private void loadData() {
        if (conn == null) return;
        try {
            tableModel.setRowCount(0);
            // Lấy dữ liệu từ bảng 'sach'
            String sql = "SELECT * FROM sach"; 
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector<Object> vec = new Vector<>();
                vec.add(rs.getInt("MaSach"));
                vec.add(rs.getString("TenSach"));
                vec.add(rs.getInt("MaTacGia")); 
                vec.add(rs.getInt("MaNXB"));
                vec.add(rs.getInt("MaLoai"));
                vec.add(rs.getInt("NamXB"));   // Đã sửa thành NamXB
                vec.add(rs.getInt("SoLuong"));
                vec.add(rs.getInt("Make"));    // Đã sửa thành Make
                vec.add(rs.getString("HinhAnh"));
                tableModel.addRow(vec);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    private void loadCombobox() {
        // Đây là dữ liệu giả lập. Trong thực tế bạn nên SELECT từ bảng Loai, NXB...
        cboLoai.addItem("3-Trinh Thám");
        cboLoai.addItem("8-Truyện Tranh");
        cboNXB.addItem("11-Võ Hoàng Kiệt");
        cboNXB.addItem("13-Kim Đồng");
        cboTacGia.addItem("13-Võ Hoàng Kiệt");
        cboTacGia.addItem("14-Conan Doyle");
        cboKeSach.addItem("2-Kệ 1");
        cboKeSach.addItem("6-Kệ 2");
    }

    private String getID(JComboBox<String> cbo) {
        if(cbo.getSelectedItem() == null) return "0";
        // Tách chuỗi "3-Trinh Thám" lấy phần đầu "3"
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