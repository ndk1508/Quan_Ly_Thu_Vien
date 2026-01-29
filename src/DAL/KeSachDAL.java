package DAL;
import DTO.KeSachDTO;
import java.sql.*;
import java.util.ArrayList;

public class KeSachDAL {
    // 1. Lấy danh sách (Chỉ lấy Kệ đang hoạt động: TrangThai = 1)
    public ArrayList<KeSachDTO> getAllKe() {
        ArrayList<KeSachDTO> list = new ArrayList<>();
        // SỬA: Thêm điều kiện WHERE TrangThai = 1
        String sql = "SELECT * FROM kesach WHERE TrangThai = 1"; 
        try {
            Connection conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                // Lưu ý: Kiểm tra lại tên cột ID trong DB là 'Make' hay 'MaKeSach' nhé
                // Ở đây mình để 'Make' theo code cũ của bạn, nếu lỗi thì đổi thành 'MaKeSach'
                list.add(new KeSachDTO(rs.getInt("Make"), rs.getString("ViTri"))); 
            }
            conn.close();
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm mới (FIX LỖI: Thêm TrangThai = 1)
    public boolean themKe(KeSachDTO ke) {
        // SỬA: Thêm cột TrangThai và giá trị 1
        String sql = "INSERT INTO kesach(ViTri, TrangThai) VALUES(?, 1)";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ke.getTenKe());
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); }
        return false;
    }

    // 3. Sửa
    public boolean suaKe(KeSachDTO ke) {
        String sql = "UPDATE kesach SET ViTri=? WHERE Make=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ke.getTenKe());
            ps.setInt(2, ke.getMaKe());
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); }
        return false;
    }

    // 4. Xóa (SỬA: Xóa mềm - Update TrangThai = 0)
    public boolean xoaKe(int maKe) {
        String sql = "UPDATE kesach SET TrangThai=0 WHERE Make=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maKe);
            return ps.executeUpdate() > 0;
        } catch(Exception e) { e.printStackTrace(); }
        return false;
    }
}