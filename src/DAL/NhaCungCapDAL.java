package DAL;

import DTO.NhaCungCapDTO;
import java.sql.*;
import java.util.ArrayList;

public class NhaCungCapDAL {

    // 1. Lấy danh sách (BỎ lấy địa chỉ)
    public ArrayList<NhaCungCapDTO> getAllNCC() {
        ArrayList<NhaCungCapDTO> list = new ArrayList<>();
        // Lấy tất cả các cột, nhưng chỉ map những cột có thật
        String sql = "SELECT * FROM nhacungcap WHERE TrangThai = 1";
        try {
            Connection conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                NhaCungCapDTO ncc = new NhaCungCapDTO();
                ncc.setMaNCC(rs.getInt("MaNCC"));
                ncc.setTenNCC(rs.getString("TenNCC"));
                // SỬA: Không lấy DiaChi từ DB nữa vì không có cột này
                ncc.setDiaChi(""); 
                list.add(ncc);
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm mới (BỎ insert địa chỉ)
    public boolean themNCC(NhaCungCapDTO ncc) {
        // SỬA: Xóa cột DiaChi khỏi câu lệnh INSERT
        String sql = "INSERT INTO nhacungcap(TenNCC, TrangThai) VALUES(?, 1)";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ncc.getTenNCC());
            // ps.setString(2, ncc.getDiaChi()); // Bỏ dòng này
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 3. Sửa thông tin (BỎ update địa chỉ)
    public boolean suaNCC(NhaCungCapDTO ncc) {
        // SỬA: Xóa DiaChi khỏi câu lệnh UPDATE
        String sql = "UPDATE nhacungcap SET TenNCC=? WHERE MaNCC=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ncc.getTenNCC());
            // ps.setString(2, ncc.getDiaChi()); // Bỏ dòng này
            ps.setInt(2, ncc.getMaNCC()); // Đổi chỉ số index từ 3 thành 2
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 4. Xóa mềm (Giữ nguyên)
    public boolean xoaNCC(int maNCC) {
        String sql = "UPDATE nhacungcap SET TrangThai=0 WHERE MaNCC=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maNCC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 5. Tìm kiếm (BỎ lấy địa chỉ)
    public ArrayList<NhaCungCapDTO> timKiemNCC(String tuKhoa) {
        ArrayList<NhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM nhacungcap WHERE TenNCC LIKE ? AND TrangThai = 1";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + tuKhoa + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaCungCapDTO ncc = new NhaCungCapDTO();
                ncc.setMaNCC(rs.getInt("MaNCC"));
                ncc.setTenNCC(rs.getString("TenNCC"));
                ncc.setDiaChi(""); // Để trống
                list.add(ncc);
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}