package DAL;

import DTO.NhaXuatBanDTO;
import java.sql.*;
import java.util.ArrayList;

public class NhaXuatBanDAL {

    // 1. Lấy danh sách (Chỉ lấy cái nào TrangThai = 1)
    public ArrayList<NhaXuatBanDTO> getAllNhaXuatBan() {
        ArrayList<NhaXuatBanDTO> list = new ArrayList<>();
        // SỬA: Thêm điều kiện WHERE TrangThai = 1
        String sql = "SELECT * FROM nhaxuatban WHERE TrangThai = 1";
        try {
            Connection conn = DBConnect.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                NhaXuatBanDTO nxb = new NhaXuatBanDTO();
                nxb.setMaNXB(rs.getInt("MaNXB"));
                nxb.setTenNXB(rs.getString("TenNXB"));
                nxb.setDiaChi(rs.getString("DiaChi"));
                nxb.setSdt(rs.getString("Sdt"));
                list.add(nxb);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Thêm mới (FIX LỖI: Thêm cột TrangThai giá trị 1)
    public boolean themNhaXuatBan(NhaXuatBanDTO nxb) {
        // SỬA: Thêm ", TrangThai" và giá trị 1 vào câu lệnh
        String sql = "INSERT INTO nhaxuatban(TenNXB, DiaChi, Sdt, TrangThai) VALUES(?, ?, ?, 1)";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiaChi());
            ps.setString(3, nxb.getSdt());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Sửa thông tin
    public boolean suaNhaXuatBan(NhaXuatBanDTO nxb) {
        String sql = "UPDATE nhaxuatban SET TenNXB=?, DiaChi=?, Sdt=? WHERE MaNXB=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nxb.getTenNXB());
            ps.setString(2, nxb.getDiaChi());
            ps.setString(3, nxb.getSdt());
            ps.setInt(4, nxb.getMaNXB());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Xóa (SỬA: Chuyển thành Xóa mềm - Update TrangThai = 0)
    public boolean xoaNhaXuatBan(int maNXB) {
        // Thay vì DELETE FROM..., ta dùng UPDATE để ẩn nó đi
        String sql = "UPDATE nhaxuatban SET TrangThai=0 WHERE MaNXB=?";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maNXB);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Tìm kiếm (Chỉ tìm trong những cái đang hoạt động)
    public ArrayList<NhaXuatBanDTO> timKiemNhaXuatBan(String tuKhoa) {
        ArrayList<NhaXuatBanDTO> list = new ArrayList<>();
        // SỬA: Thêm điều kiện AND TrangThai = 1
        String sql = "SELECT * FROM nhaxuatban WHERE TenNXB LIKE ? AND TrangThai = 1";
        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + tuKhoa + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaXuatBanDTO nxb = new NhaXuatBanDTO();
                nxb.setMaNXB(rs.getInt("MaNXB"));
                nxb.setTenNXB(rs.getString("TenNXB"));
                nxb.setDiaChi(rs.getString("DiaChi"));
                nxb.setSdt(rs.getString("Sdt"));
                list.add(nxb);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}