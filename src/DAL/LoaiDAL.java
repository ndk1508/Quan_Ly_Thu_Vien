package DAL;
import DTO.LoaiDTO;
import java.sql.*;
import java.util.ArrayList;

public class LoaiDAL {
    
    // 1. Lấy danh sách (Chỉ lấy loại đang dùng)
    public ArrayList<LoaiDTO> getAllLoai() {
        ArrayList<LoaiDTO> list = new ArrayList<>();
        // SỬA: Thêm điều kiện WHERE TrangThai = 1
        String sql = "SELECT * FROM loai WHERE TrangThai = 1"; 
        try {
            Connection conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                list.add(new LoaiDTO(rs.getInt("MaLoai"), rs.getString("TenLoai")));
            }
            conn.close();
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm mới (FIX LỖI: Thêm TrangThai = 1)
    public boolean themLoai(LoaiDTO loai) {
        // SỬA: Thêm cột TrangThai và giá trị 1
        String sql = "INSERT INTO loai(TenLoai, TrangThai) VALUES(?, 1)";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loai.getTenLoai());
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); }
        return false;
    }

    // 3. Sửa
    public boolean suaLoai(LoaiDTO loai) {
        String sql = "UPDATE loai SET TenLoai=? WHERE MaLoai=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loai.getTenLoai());
            ps.setInt(2, loai.getMaLoai());
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); }
        return false;
    }

    // 4. Xóa (Xóa mềm)
    public boolean xoaLoai(int maLoai) {
        String sql = "UPDATE loai SET TrangThai=0 WHERE MaLoai=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maLoai);
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); }
        return false;
    }
}