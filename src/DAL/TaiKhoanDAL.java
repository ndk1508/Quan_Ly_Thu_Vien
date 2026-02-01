package DAL;

import DTO.TaiKhoanDTO;
import java.sql.*;

public class TaiKhoanDAL {

    public TaiKhoanDTO dangNhap(String username, String password) {
        String sql = "SELECT * FROM taikhoan WHERE username=? AND password=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TaiKhoanDTO(
                    rs.getInt("matk"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("quyen")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getNextMaTK() {
        String sql = "SELECT MAX(matk) FROM taikhoan";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) + 1; // Lấy số lớn nhất cộng thêm 1
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1; // Nếu bảng chưa có dữ liệu, bắt đầu từ 1
    }

    // PHƯƠNG THỨC INSERT ĐÃ SỬA LỖI
    public boolean insert(TaiKhoanDTO tk) {
        // 1. Gọi hàm lấy mã mới
        int nextId = getNextMaTK();
        
        // 2. Thêm matk vào câu lệnh INSERT (4 dấu hỏi)
        String sql = "INSERT INTO taikhoan (matk, username, password, quyen) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nextId); // Nạp mã vừa tìm được vào vị trí số 1
            ps.setString(2, tk.getUsername());
            ps.setString(3, tk.getPassword());
            ps.setInt(4, tk.getQuyen());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}