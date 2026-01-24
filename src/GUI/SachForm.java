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

public class SachForm extends JFrame { // Đã đổi tên class

    // 1. KHAI BÁO CÁC BIẾN GIAO DIỆN
    private JPanel contentPane;
    private JTextField txtMaSach, txtTenSach, txtNamXB, txtSoLuong, txtTimKiem;
    private JComboBox<String> cboLoai, cboNXB, cboTacGia, cboKeSach;
    private JLabel lblHinhAnh;
    private JTable table;
    private DefaultTableModel tableModel;
    private String strTenAnh = "no-image.png"; // Ảnh mặc định

    // 2. KHAI BÁO KẾT NỐI CSDL
    // === BẠN SỬA LẠI THÔNG TIN Ở ĐÂY CHO ĐÚNG MÁY BẠN ===
    String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLySach;encrypt=true;trustServerCertificate=true;";
    String user = "sa";
    String pass = "123456";
    Connection conn = null;

    // Hàm main để chạy chương trình
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SachForm frame = new SachForm(); // Đã đổi tên khởi tạo
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Constructor: Khởi tạo giao diện
    public SachForm() { // Đã đổi tên Constructor
        setTitle("Quản Lý Sách - Java Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        
        // --- PHẦN GIAO DIỆN (UI) ---
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Tiêu đề
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
        
        // Ẩn Mã Sách (Để quản lý ID khi Sửa/Xóa)
        txtMaSach = new JTextField(); 
        txtMaSach.setVisible(false);
        contentPane.add(txtMaSach);

        // Combobox Loại
        JLabel lblLoai = new JLabel("Mã Loại:");
        lblLoai.setBounds(50, 100, 100, 25);
        contentPane.add(lblLoai);
        cboLoai = new JComboBox<>();
        cboLoai.setBounds(150, 100, 200, 25);
        contentPane.add(cboLoai);

        // Combobox NXB
        JLabel lblNXB = new JLabel("Nhà Xuất Bản:");
        lblNXB.setBounds(50, 140, 100, 25);
        contentPane.add(lblNXB);
        cboNXB = new JComboBox<>();
        cboNXB.setBounds(150, 140, 200, 25);
        contentPane.add(cboNXB);

        // Combobox Tác Giả
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

        // --- GỌI CÁC HÀM XỬ LÝ KHI CHẠY ---
        connectDB();
        loadCombobox();
        loadData();

        // --- XỬ LÝ SỰ KIỆN (EVENTS) ---

        // Nút Chọn Ảnh
        btnChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "gif"));
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                strTenAnh = file.getName();
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(lblHinhAnh.getWidth(), lblHinhAnh.getHeight(), Image.SCALE_SMOOTH);
                lblHinhAnh.setIcon(new ImageIcon(img));
            }
        });

        // Nút Thêm
        btnThem.addActionListener(e -> {
            try {
                String sql = "INSERT INTO Sach(TenSach, MaLoai, MaNXB, MaTacGia, NamXuatBan, SoLuong, MaKe, HinhAnh) VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, txtTenSach.getText());
                ps.setString(2, getID(cboLoai));
                ps.setString(3, getID(cboNXB));
                ps.setString(4, getID(cboTacGia));
                ps.setInt(5, Integer.parseInt(txtNamXB.getText()));
                ps.setInt(6, Integer.parseInt(txtSoLuong.getText()));
                ps.setString(7, getID(cboKeSach));
                ps.setString(8, strTenAnh);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Thêm thành công!");
                loadData();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
            }
        });

        // Nút Xóa
        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1) {
                 JOptionPane.showMessageDialog(null, "Vui lòng chọn sách để xóa");
                 return;
            }
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn