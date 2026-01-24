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
                int matk = rs.getInt("matk");
                int q = rs.getInt("quyen");

                String quyenStr = (q == 0) ? "admin" : "nhanvien";

                return new TaiKhoanDTO(
                        matk,
                        rs.getString("username"),
                        rs.getString("password"),
                        quyenStr
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
