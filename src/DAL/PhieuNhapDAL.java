package DAL;

import DTO.PhieuNhapDTO;
import java.sql.*;
import java.util.ArrayList;

public class PhieuNhapDAL {
    
    // 1. Lấy danh sách (Chỉ lấy phiếu đang hoạt động: TrangThai = 1)
    public ArrayList<PhieuNhapDTO> getAllPhieuNhap() {
        ArrayList<PhieuNhapDTO> list = new ArrayList<>();
        // SỬA: Thêm điều kiện WHERE TrangThai = 1
        String sql = "SELECT * FROM phieunhap WHERE TrangThai = 1";
        try {
            Connection conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO();
                pn.setMaPN(rs.getInt("MaPhieuNhap"));
                pn.setMaNCC(rs.getInt("MaNCC"));
                pn.setMaNV(rs.getInt("MaNV"));
                pn.setNgayNhap(rs.getDate("NgayNhap"));
                list.add(pn);
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm mới (FIX LỖI QUAN TRỌNG: Thêm cột TrangThai giá trị 1)
    public boolean themPhieuNhap(PhieuNhapDTO pn) {
        // SỬA: Thêm ", TrangThai" vào câu lệnh insert
        String sql = "INSERT INTO phieunhap(MaNCC, MaNV, NgayNhap, TrangThai) VALUES(?, ?, ?, 1)";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pn.getMaNCC());
            ps.setInt(2, pn.getMaNV());
            ps.setDate(3, pn.getNgayNhap());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { 
            e.printStackTrace(); 
            // In lỗi ra console để bạn dễ kiểm tra nếu có lỗi khác (ví dụ sai Mã NV)
            System.out.println("Lỗi thêm phiếu nhập: " + e.getMessage());
        }
        return false;
    }

    // 3. Xóa phiếu nhập (SỬA: Chuyển thành Xóa Mềm - Update TrangThai = 0)
    public boolean xoaPhieuNhap(int maPN) {
        // Thay vì DELETE, ta ẩn nó đi bằng cách set TrangThai = 0
        String sql = "UPDATE phieunhap SET TrangThai=0 WHERE MaPhieuNhap=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maPN);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    
    // 4. Sửa phiếu nhập
    public boolean suaPhieuNhap(PhieuNhapDTO pn) {
        String sql = "UPDATE phieunhap SET MaNCC=?, MaNV=?, NgayNhap=? WHERE MaPhieuNhap=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pn.getMaNCC());
            ps.setInt(2, pn.getMaNV());
            ps.setDate(3, pn.getNgayNhap());
            ps.setInt(4, pn.getMaPN());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}