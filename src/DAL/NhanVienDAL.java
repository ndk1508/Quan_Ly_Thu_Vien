package DAL;

import DTO.NhanVienDTO;
import java.sql.*;
import java.util.ArrayList;

public class NhanVienDAL {
	public ArrayList<NhanVienDTO> search(String keyword) {
	    ArrayList<NhanVienDTO> list = new ArrayList<>();
	    String sql = "SELECT * FROM nhanvien WHERE MaNV LIKE ? OR TenNV LIKE ?";
	    try (Connection conn = DBConnect.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, "%" + keyword + "%" );
	        ps.setString(2, "%" + keyword + "%" );
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            list.add(new NhanVienDTO(
	                rs.getString("MaNV"),
	                rs.getString("TenNV"),
	                rs.getInt("NamSinh"),
	                rs.getString("GioiTinh"),
	                rs.getString("DiaChi"),
	                rs.getString("Sdt"),
	                rs.getInt("TrangThai")
	            ));
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return list;
	}
    public ArrayList<NhanVienDTO> getAll() {
        ArrayList<NhanVienDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVienDTO nv = new NhanVienDTO(
                        rs.getString("MaNV"),
                        rs.getString("TenNV"),
                        rs.getInt("NamSinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("Sdt"),
                        rs.getInt("TrangThai")
                );
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(NhanVienDTO nv) {
        String sql = "INSERT INTO NhanVien VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getTenNV());
            ps.setInt(3, nv.getNamSinh());
            ps.setString(4, nv.getGioiTinh());
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getSdt());
            ps.setInt(7, nv.getTrangThai());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(NhanVienDTO nv) {
        String sql = "UPDATE NhanVien SET TenNV=?, NamSinh=?, GioiTinh=?, DiaChi=?, Sdt=?, TrangThai=? WHERE MaNV=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getTenNV());
            ps.setInt(2, nv.getNamSinh());
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getDiaChi());
            ps.setString(5, nv.getSdt());
            ps.setInt(6, nv.getTrangThai());
            ps.setString(7, nv.getMaNV());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
